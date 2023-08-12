package com.loldie.bots.ilikecabbage;
import com.loldie.bots.common.tasks.BankEverythingTask;
import com.loldie.bots.common.tasks.InteractWithObjectTask;
import com.loldie.bots.ilikecabbage.branches.IsInCabbageFarmBranch;
import com.loldie.bots.ilikecabbage.branches.IsNearFarmBranch;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.branches.InventoryFullBranch;
import com.loldie.bots.common.inherit.StandardTreeBot;
import com.loldie.bots.common.inherit.StandardUIController;
import com.loldie.bots.common.items.ItemName;
import com.loldie.bots.common.items.ItemPricePoller;
import com.loldie.bots.common.locations.Draynor;
public class ILikeCabbage extends StandardTreeBot
{
	private static final int CABBAGE_ID = 1965;
	private static final ItemName CABBAGE_NAME = new ItemName("Cabbage", "Cabbages");
	private static final ItemPricePoller PRICE_POLLER = new ItemPricePoller(CABBAGE_ID);
	public ILikeCabbage()
	{
		super(new StandardUIController(CABBAGE_NAME, PRICE_POLLER, 28));
		IsNearFarmBranch isNearFarmBranch = new IsNearFarmBranch();
		super.logicTree = new InventoryFullBranch( new IsInCabbageFarmBranch( isNearFarmBranch, Draynor.getBankAccessBranch(new BankEverythingTask((ITimedTaskDoneHandler)this.uiController)) ),
												   new IsInCabbageFarmBranch(new InteractWithObjectTask("Getting cabbage...", "Pick", "Cabbage"), isNearFarmBranch) );
	}
	@Override
	public void onStart(String... args)
	{
		this.setLoopDelay(500, 750);
		System.out.println("Loaded ILikeCabbage v1.0");
	}
}
