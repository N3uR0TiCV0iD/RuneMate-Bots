package com.loldie.bots.common.logic;
import java.util.HashMap;
import java.util.ArrayList;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.PlayerWatch;
import com.runemate.game.api.hybrid.local.Worlds;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.entities.Player;
import com.loldie.bots.common.AllWorldsOccupiedException;
import com.runemate.game.api.hybrid.local.hud.interfaces.WorldHop;
public class LogicTree
{
	private static final int[] FREE_WORLDS = new int[] {120, 57, 136, 81, 41, 80, 20, 61, 141, 38, 19, 29, 17, 8, 33, 43, 108, 11, 34};
	private static final int[] MEMBER_WORLDS = new int[] {97, 18, 137, 115, 73, 49, 50, 96, 15, 62, 74, 104, 40, 72, 53, 59, 72, 59};
	private static final int NOTSEEN_REMOVE_LIMIT = 7200000; //If we haven't seen a player for 2 hours he is probably not around :)
	private static final int LAST_SEEN_LIMIT = 5400000; //If we've seen a player around for 1 hour and 30 minutes he might be getting suspicious
	private static final int FIRST_SEEN_LIMIT = 9000000; //If 2 hours and 30 minutes have passed since we first saw this player, he might be getting suspicious
	private static final int FLAG_COOLDOWN = 10800000; //Flag a world for 3 hours
	private static HashMap<Integer, Long> flaggedWorlds;
	private static HashMap<Long, Integer> botWorlds;
	static
	{
		flaggedWorlds = new HashMap<Integer, Long>();
		botWorlds = new HashMap<Long, Integer>();
	}
	public static boolean isWorldValid(int worldID)
	{
		Object cooldownEndTime = flaggedWorlds.get(worldID);
		if (cooldownEndTime != null && System.currentTimeMillis() < (long)cooldownEndTime) //Was this world flagged? If so, has the cooldown ended?
		{
			return false; //No it hasn't :(
		}
		for (int currWorldID : botWorlds.values()) //Is this world occupied by any other bot?
		{
			if (currWorldID == worldID)
			{
				return false; //Yes it is :(
			}
		}
		return true;
	}
	public static void notifyNewWorldID(int worldID)
	{
		System.out.println("[BOT-NAME=" + Players.getLocal().getName() + "]: Joined world " + worldID);
		botWorlds.put(Utils.getBotID(), worldID);
	}
	private LogicBranch root;
	private int[] usingWorlds;
	private HashMap<String, PlayerWatch> playerWatches;
	public LogicTree(LogicBranch root, boolean memberWorlds)
	{
		this.root = root;
		this.playerWatches = new HashMap<String, PlayerWatch>();
		this.usingWorlds = memberWorlds ? MEMBER_WORLDS : FREE_WORLDS;
	}
	public void runTree() throws AllWorldsOccupiedException
	{
		if (Worlds.getCurrent() != -1) //Wait until we are loaded in...
		{
			Player localPlayer = Players.getLocal();
			if ( needsWorldSwitch(localPlayer.getName()) )
			{
				joinNewWorld();
			}
			else if (localPlayer.getAnimationId() == -1 && !localPlayer.isMoving())
			{
				root.runNode();
			}
		}
	}
	private boolean needsWorldSwitch(String localPlayerName)
	{
		long botID = Utils.getBotID();
		if (botWorlds.containsKey(botID)) //Is it the first time we load in?
		{
			//No it isn't, let's monitor the players around us...
			String currPlayerName;
			long timeSinceLastSeen;
			PlayerWatch currPlayerWatch;
			boolean switchWorlds = false;
			ArrayList<String> deletingWatches = new ArrayList<String>();
			for (Player currPlayer : Players.getLoaded())
			{
				currPlayerName = currPlayer.getName();
				if (!currPlayerName.equals(localPlayerName))
				{
					currPlayerWatch = playerWatches.get(currPlayerName);
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
				currPlayerWatch = playerWatches.get(currSeenPlayerName);
				timeSinceLastSeen = currPlayerWatch.getTimeSinceLastSeen();
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
			if (switchWorlds)
			{
				flaggedWorlds.put(Worlds.getCurrent(), System.currentTimeMillis() + FLAG_COOLDOWN);
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	public void joinNewWorld() throws AllWorldsOccupiedException
	{
		boolean joinedWorld = false;
		for (int currWorldID : usingWorlds)
		{
			if (isWorldValid(currWorldID))
			{
				if (WorldHop.hopTo(currWorldID))
				{
					notifyNewWorldID(currWorldID);
					playerWatches.clear();
				}
				joinedWorld = true;
				break;
			}
		}
		if (!joinedWorld)
		{
			throw new AllWorldsOccupiedException();
		}
	}
}
