package com.loldie.bots.getwoadleaves;
import com.loldie.bots.dyemaker.DyeMaker;
import com.loldie.bots.common.items.ItemName;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.StopBotTask;
import com.loldie.bots.common.locations.Falador;
import com.loldie.bots.common.items.ItemPricePoller;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.MembershipPreference;
import com.loldie.bots.common.branches.HasGoldBranch;
import com.loldie.bots.common.branches.HasItemsBranch;
import com.loldie.bots.common.inherit.StandardTreeBot;
import com.loldie.bots.common.tasks.WithdrawBankMoneyTask;
import com.loldie.bots.common.inherit.StandardUIController;
import com.loldie.bots.getwoadleaves.branches.InWysonHouseBranch;
public class GetWoadLeaves extends StandardTreeBot
{
	private static final int WOADLEAF_PRICE = 10;
	private static final int WOADLEAF_AMOUNT = 500;
	private static final int WITHDRAW_AMOUNT = WOADLEAF_PRICE * WOADLEAF_AMOUNT;
	private static final ItemName LEAVES_NAME = new ItemName("WoadLeaf", "WoadLeaves");
	private static final ItemPricePoller PRICE_POLLER = new ItemPricePoller(DyeMaker.WOADLEAF_ID);
	public GetWoadLeaves()
	{
		super(new StandardUIController(LEAVES_NAME, PRICE_POLLER, 2), MembershipPreference.PREFERED, true, true);
		LogicBranch getMoneyBranch = Falador.getEastBankAccessBranch(new WithdrawBankMoneyTask(WITHDRAW_AMOUNT));
		StopBotTask stopBotTask = new StopBotTask("Successfully collected " + WOADLEAF_AMOUNT + " woad leaves", this);
		LogicBranch getLeavesBranch = new HasGoldBranch(WOADLEAF_PRICE, new InWysonHouseBranch((ITimedTaskDoneHandler)super.uiController), getMoneyBranch);
		super.logicTree = new HasItemsBranch(new ItemAmount(DyeMaker.WOADLEAF_ID, WOADLEAF_AMOUNT), true, stopBotTask, getLeavesBranch);
	}
	@Override
	public void onStart(String... args)
	{
		super.onStart(args);
		this.setLoopDelay(450, 650);
		System.out.println("Loaded GetWoadLeaves v1.0");
	}
}
