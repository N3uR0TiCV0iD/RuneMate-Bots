package com.loldie.bots.givemebeer;
import com.loldie.bots.common.locations.Falador;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.MembershipPreference;
import com.loldie.bots.common.branches.HasGoldBranch;
import com.loldie.bots.common.inherit.StandardTreeBot;
import com.loldie.bots.common.tasks.BankEverythingTask;
import com.loldie.bots.common.tasks.WithdrawBankMoneyTask;
import com.loldie.bots.common.branches.InventoryFullBranch;
import com.loldie.bots.common.inherit.StandardUIController;
import com.loldie.bots.common.items.ItemName;
import com.loldie.bots.common.items.ItemPricePoller;
import com.loldie.bots.givemebeer.branches.InFaladorBarBranch;
import com.runemate.game.api.hybrid.Environment;
public class GiveMeBeer extends StandardTreeBot
{
	private static final int BEER_ID = 1913;
	private static final int BEER_PRICE = 3;
	private static final int ITEMS_PER_BANK = 28;
	private static final int WITHDRAW_AMOUNT = BEER_PRICE * ITEMS_PER_BANK;
	private static final ItemName BEER_NAME = new ItemName("Beer", "Beers");
	private static final ItemPricePoller PRICE_POLLER = new ItemPricePoller(BEER_ID);
	public GiveMeBeer()
	{
		super(new StandardUIController(BEER_NAME, PRICE_POLLER, ITEMS_PER_BANK), MembershipPreference.PREFERED, true, true);
		ILogicNode inventoryFullPath;
		ILogicNode faladorBarBranch = new InFaladorBarBranch();
		ILogicNode bankEverythingTask = new BankEverythingTask((ITimedTaskDoneHandler)this.uiController);
		ILogicNode getMoneyBranch = Falador.getWestBankAccessBranch(new WithdrawBankMoneyTask(WITHDRAW_AMOUNT));
		ILogicNode bankEverythingBranch = Falador.getWestBankAccessBranch(bankEverythingTask);
		if (Environment.isOSRS())
		{
			//This "HasGoldBranch" will check if we have enough for ONE more beer while the inventory is full
			inventoryFullPath = new HasGoldBranch(BEER_PRICE, faladorBarBranch, bankEverythingBranch);
		}
		else
		{
			inventoryFullPath = bankEverythingBranch;
		}
		this.logicTree = new InventoryFullBranch(inventoryFullPath, new HasGoldBranch(BEER_PRICE, faladorBarBranch, getMoneyBranch));
	}
	@Override
	public void onStart(String... args)
	{
		this.setLoopDelay(450, 650);
		System.out.println("Loaded GiveMeBeer v1.0");
	}
}
