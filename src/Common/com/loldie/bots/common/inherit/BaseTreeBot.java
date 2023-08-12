package com.loldie.bots.common.inherit;
import com.loldie.bots.common.logic.ILogicNode;
import java.util.ArrayList;
import java.util.HashMap;
import com.loldie.bots.common.NoAvailableWorldsException;
import com.loldie.bots.common.MembershipPreference;
import com.loldie.bots.common.PlayerWatch;
import com.loldie.bots.common.Utils;
import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.AccountInfo;
import com.runemate.game.api.hybrid.local.WorldOverview;
import com.runemate.game.api.hybrid.local.Worlds;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.WorldHop;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.queries.WorldQueryBuilder;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.osrs.local.hud.interfaces.OptionsTab;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.LoopingBot;
import com.runemate.game.api.script.framework.listeners.ChatboxListener;
import com.runemate.game.api.script.framework.listeners.events.MessageEvent;
public abstract class BaseTreeBot extends LoopingBot implements ChatboxListener
{
	public static final int INVENTORY_MENU_BUTTON_ID = 10747958;
	private static final int NOTSEEN_REMOVE_LIMIT = 7200000; //If we haven't seen a player for 2 hours he is probably not around :)
	private static final int LAST_SEEN_LIMIT = 5400000; //If we've seen a player around for 1 hour and 30 minutes he might be getting suspicious
	private static final int FIRST_SEEN_LIMIT = 9000000; //If 2 hours and 30 minutes have passed since we first saw this player, he might be getting suspicious
	private static final int FLAG_COOLDOWN = 10800000; //Flag a world for 3 hours
	private static HashMap<AbstractBot, Integer> botIDs = new HashMap<AbstractBot, Integer>();
	private static HashMap<Integer, Integer> botWorlds = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Long> flaggedWorlds = new HashMap<Integer, Long>(); //TODO: Flag them based on the TYPE of bot, ie: BOT_TYPE => WorldID => Cooldown
	private static int nextBotID = 0;
	public static int getBotID(AbstractBot bot)
	{
		return botIDs.getOrDefault(bot, -1);
	}
	public static ArrayList<Integer> getBotsInWorld(int worldID)
	{
		ArrayList<Integer> botIDs = new ArrayList<Integer>();
		for (int currBotID : botWorlds.keySet())
		{
			if (botWorlds.get(currBotID) == worldID)
			{
				botIDs.add(currBotID);
			}
		}
		return botIDs;
	}
	public static String stopBot(AbstractBot botInstance, String reason)
	{
		String stopMessage = "Stopping Bot (Reason: " + reason + ")";
		ClientUI.sendTrayNotification(Environment.getAccountAlias(), stopMessage);
		botInstance.stop(reason);
		return stopMessage;
	}
	protected ILogicNode logicTree;
	private HashMap<String, PlayerWatch> playerWatches;
	private WorldQueryBuilder worldsQuery;
	private int minRunEnergyToggle;
	private boolean joinedNewWorld;
	private boolean canShareWorld;
	public BaseTreeBot()
	{
		this(MembershipPreference.PREFERED, true, false);
	}
	public BaseTreeBot(MembershipPreference membershipPreference, boolean playerAware, boolean canShareWorld)
	{
		this.worldsQuery = Worlds.newQuery().pvp(false).deadman(false).tournament(false).filter(currWorld -> !currWorld.getActivity().endsWith("skill total"));
		switch (membershipPreference)
		{
			case REQUIRED:
				this.worldsQuery = this.worldsQuery.member();
			break;
			case PREFERED:
				if (AccountInfo.isMember())
				{
					this.worldsQuery = this.worldsQuery.member();
				}
				else
				{
					this.worldsQuery = this.worldsQuery.free();
				}
			break;
			case NONE:
				//Use both worlds (if possible)
				if (AccountInfo.isMember())
				{
					this.worldsQuery = this.worldsQuery.member();
				}
				this.worldsQuery = this.worldsQuery.free();
			break;
		}
		if (playerAware)
		{
			this.playerWatches = new HashMap<String, PlayerWatch>();
			this.getEventDispatcher().addListener(this);
		}
		this.canShareWorld = canShareWorld;
		this.minRunEnergyToggle = 1;
		botIDs.put(this, nextBotID);
		nextBotID++;
	}
	public int getBotID()
	{
		return botIDs.get(this);
	}
	public boolean isPlayerAware()
	{
		return playerWatches != null;
	}
	public boolean canShareWorld()
	{
		return canShareWorld;
	}
	@Override
	public void onLoop()
	{
		try
		{
			Player localPlayer = Players.getLocal();
			if (localPlayer != null && Worlds.getCurrent() != -1) //Wait until we are loaded in...
			{
				String localPlayerName = localPlayer.getName();
				if (Utils.isValidPlayerName(localPlayerName) && needsWorldSwitch(localPlayerName))
				{
					joinNewWorld();
				}
				else if (Utils.isPlayerIdle(localPlayer))
				{
					if (!Traversal.isRunEnabled() && Traversal.getRunEnergy() >= minRunEnergyToggle)
					{
						minRunEnergyToggle = Utils.GENERATOR.nextInt(10) == 0 ? (2 + Utils.GENERATOR.nextInt(2)) : 1; //10% chance of not toggling run @ 1% next time
						Traversal.toggleRun();
					}
					if (joinedNewWorld)
					{
						joinedNewWorld = false;
						afterWorldHop();
					}
					logicTree.runNode();
				}
			}
		}
		catch (NoAvailableWorldsException ex)
		{
			ex.printStackTrace();
			this.stop(ex.getMessage());
		}
	}
	private boolean needsWorldSwitch(String localPlayerName)
	{
		int botID = getBotID();
		if (botWorlds.containsKey(botID)) //Is it the first time we load in?
		{
			//No it isn't, let's monitor the players around us IF we chose to do so...
			if (isPlayerAware() && playerAwarenessCheck(localPlayerName))
			{
				flaggedWorlds.put(Worlds.getCurrent(), System.currentTimeMillis() + FLAG_COOLDOWN);
				return true;
			}
			return false;
		}
		firstLoad();
		if (!isPlayerAware()) //Should we find a less populated world right away?
		{
			//No, that won't be necessary...
			int worldID = Worlds.getCurrent();
			if (!isWorldValid(worldID))
			{
				return true;
			}
			notifyNewWorldID(worldID);
			return false;
		}
		return true; //Yes we should!
	}
	private boolean playerAwarenessCheck(String localPlayerName)
	{
		boolean switchWorlds = false;
		ArrayList<String> deletingWatches = new ArrayList<String>();
		for (Player currPlayer : Players.getLoaded())
		{
			String currPlayerName = currPlayer.getName();
			if (!currPlayerName.equals(localPlayerName))
			{
				PlayerWatch currPlayerWatch = playerWatches.get(currPlayerName);
				if (currPlayerWatch == null)
				{
					playerWatches.put(currPlayerName, new PlayerWatch());
				}
				else
				{
					currPlayerWatch.updateLastSeen();
				}
			}
		}
		for (String currSeenPlayerName : playerWatches.keySet())
		{
			PlayerWatch currPlayerWatch = playerWatches.get(currSeenPlayerName);
			long timeSinceLastSeen = currPlayerWatch.getTimeSinceLastSeen();
			if (timeSinceLastSeen >= NOTSEEN_REMOVE_LIMIT)
			{
				deletingWatches.add(currSeenPlayerName);
			}
			else if (timeSinceLastSeen >= LAST_SEEN_LIMIT || currPlayerWatch.getTimeSinceFirstSeen() >= FIRST_SEEN_LIMIT)
			{
				switchWorlds = true;
			}
		}
		for (String currDeletingPlayerName : deletingWatches)
		{
			playerWatches.remove(currDeletingPlayerName);
		}
		return switchWorlds;
	}
	private void firstLoad()
	{
		//Apply some settings that can help the bot
		if (!OptionsTab.areRoofsAlwaysHidden())
		{
			OptionsTab.toggleAlwaysHideRoofs();
		}
	}
	protected boolean isWorldValid(int worldID)
	{
		Object cooldownEndTime = flaggedWorlds.get(worldID);
		if (cooldownEndTime != null && System.currentTimeMillis() < (long)cooldownEndTime) //Was this world flagged? If so, has the cooldown ended?
		{
			return false; //It was flagged and the cooldown hasn't ended yet :(
		}
		return canShareWorld || !botWorlds.containsValue(worldID);
	}
	private void notifyNewWorldID(int worldID)
	{
		Utils.debugLog("[BOT-NAME=" + Players.getLocal().getName() + "]: Joined world " + worldID);
		botWorlds.put(getBotID(), worldID);
		joinedNewWorld = true;
	}
	public void joinNewWorld() throws NoAvailableWorldsException
	{
		boolean foundWorld = false;
		//List<WorldOverview> worlds = worldsQuery.results().asList();
		if (isPlayerAware())
		{
			//TODO: Sort from least populated to most populated [Population getter not yet implemented in the runemate API]
			//http://oldschool.runescape.com/slu?order=PWMLA
			/*
			worlds.sort((left, right) -> left.);
			*/
		}
		for (WorldOverview currWorld : worldsQuery.results())
		{
			int currWorldID = currWorld.getId();
			if (isWorldValid(currWorldID))
			{
				beforeWorldHop();
				if (WorldHop.hopTo(currWorldID))
				{
					notifyNewWorldID(currWorldID);
					playerWatches.clear();
				}
				foundWorld = true;
				break;
			}
		}
		if (!foundWorld)
		{
			throw new NoAvailableWorldsException();
		}
	}
	protected void afterWorldHop()
	{
		if (Environment.isOSRS())
		{
			InterfaceComponent inventoryMenuButton = Utils.getInterfaceComponent(INVENTORY_MENU_BUTTON_ID);
			if (inventoryMenuButton != null)
			{
				inventoryMenuButton.click();
			}
		}
	}
	//[IChatboxListener]
	@Override
	public void onMessageReceived(MessageEvent e)
	{
		//TODO: Analyze the message and if it is comming from a player AND your name get's mentioned send a notification
		Utils.debugLog("/!\\ CHATMESSAGE /!\\ | " + e.getSpeaker() + ": " + e.getMessage());
	}
	protected void beforeWorldHop() { } //Virtual method
}
