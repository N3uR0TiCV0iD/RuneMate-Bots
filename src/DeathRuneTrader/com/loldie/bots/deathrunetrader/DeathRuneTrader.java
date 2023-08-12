package com.loldie.bots.deathrunetrader;
import com.runemate.game.api.hybrid.local.Worlds;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import java.util.HashMap;
import com.loldie.bots.common.MembershipPreference;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.branches.HasGoldBranch;
import com.loldie.bots.common.branches.HasItemsBranch;
import com.loldie.bots.common.branches.IsShopOpenBranch;
import com.loldie.bots.common.branches.ShopHasItemsBranch;
import com.loldie.bots.common.inherit.StandardTreeBot;
import com.loldie.bots.common.inherit.StandardUIController;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.items.ItemName;
import com.loldie.bots.common.items.ItemPricePoller;
import com.loldie.bots.common.locations.Varrock;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.loldie.bots.common.tasks.TradeNPCTask;
import com.loldie.bots.common.tasks.BuyInBulkTask;
import com.loldie.bots.common.tasks.HopWorldTask;
import com.loldie.bots.common.tasks.SellToGrandExchangeTask;
import com.loldie.bots.common.tasks.StopBotTask;
public class DeathRuneTrader extends StandardTreeBot
{
	private static final int DEATH_RUNE_ID = 560;
	private static final int MAX_RUNE_STOCK = 250;
	private static final int RUNE_REFRESH_TIME = 8; //It takes 8 seconds for the rune count to go up by 1
	private static final int DEATH_RUNE_PRICE = 203; //The "maximum" buy price in the shop. Needs to be updated if we change the "MIN_RUNE_BUY_STOCK"
	private static final int MIN_RUNE_BUY_STOCK = 126; //88
	private static final int RUNE_BUY_PER_WORLD = MAX_RUNE_STOCK - MIN_RUNE_BUY_STOCK;
	private static final long WORLD_COOLDOWN = RUNE_BUY_PER_WORLD * RUNE_REFRESH_TIME;
	private static final ItemName DEATH_RUNE_NAME = new ItemName("Death rune", "Death runes");
	private static final ItemPricePoller PRICE_POLLER = new ItemPricePoller(DEATH_RUNE_ID);
	private static final Area RUNE_SHOP_AREA = new Area.Circular(new Coordinate(3253, 3402, 0), 5);
	private static HashMap<Integer, Long> flaggedWorlds = new HashMap<Integer, Long>();
	public DeathRuneTrader()
	{
		super(new StandardUIController(DEATH_RUNE_NAME, PRICE_POLLER, RUNE_BUY_PER_WORLD), MembershipPreference.PREFERED, false, false);
		ILogicNode tradeBranch = new ShopHasItemsBranch(new ItemAmount(DEATH_RUNE_ID, MIN_RUNE_BUY_STOCK + 1),
														new BuyInBulkTask(new ItemAmount(DEATH_RUNE_ID, MIN_RUNE_BUY_STOCK), true),
														new HopWorldTask(this));
		ILogicNode auburyTradeBranch = new IsShopOpenBranch(tradeBranch, new TradeNPCTask("Aubury"));
		ILogicNode runeShopBranch = new LogicAreaBranch(RUNE_SHOP_AREA, auburyTradeBranch, "Rune shop");
		ILogicNode sellToGEBranch = Varrock.getGrandExchangeAccessBranch(new SellToGrandExchangeTask("Death rune", PRICE_POLLER.getItemPrice()));
		ILogicNode grandExchangeBranch = new HasItemsBranch(new ItemAmount(DEATH_RUNE_ID, 1), true, sellToGEBranch, new StopBotTask("You need to have at least 50k gold ready!", this));
		super.logicTree = new HasGoldBranch(DEATH_RUNE_PRICE, runeShopBranch, grandExchangeBranch);
	}
	@Override
	public void onStart(String... args)
	{
		this.setLoopDelay(500, 750);
		System.out.println("DeathRuneTrader v1.0");
	}
	@Override
	protected boolean isWorldValid(int worldID)
	{
		return super.isWorldValid(worldID) && System.currentTimeMillis() >= flaggedWorlds.getOrDefault(worldID, 0L);
	}
	@Override
	protected void beforeWorldHop()
	{
		int worldID = Worlds.getCurrent();
		Utils.debugLog("World " + worldID + " has been flagged for " + WORLD_COOLDOWN + " seconds!");
		flaggedWorlds.put(worldID, System.currentTimeMillis() + WORLD_COOLDOWN);
	}
}
