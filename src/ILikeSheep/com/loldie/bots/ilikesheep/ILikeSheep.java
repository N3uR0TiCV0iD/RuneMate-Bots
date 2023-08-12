package com.loldie.bots.ilikesheep;
import com.runemate.game.api.hybrid.Environment;
import com.loldie.bots.ilikesheep.tasks.GetWoolTask;
import com.loldie.bots.common.tasks.BankEverythingTask;
import com.loldie.bots.common.tasks.JumpOverStileTask;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.branches.InventoryFullBranch;
import com.loldie.bots.common.inherit.StandardTreeBot;
import com.loldie.bots.common.inherit.StandardUIController;
import com.loldie.bots.common.items.ItemName;
import com.loldie.bots.common.items.ItemPricePoller;
import com.loldie.bots.common.locations.Lumbridge;
import com.loldie.bots.ilikesheep.branches.IsNearPenBranch;
import com.loldie.bots.ilikesheep.branches.IsInSheepPenBranch;
public class ILikeSheep extends StandardTreeBot
{
	private static final int WOOL_ID = 1737;
	private static final ItemName WOOL_NAME = new ItemName("Wool");
	private static final ItemPricePoller PRICE_POLLER = new ItemPricePoller(WOOL_ID);
	public ILikeSheep()
	{
		//TODO: Refactor & Add full OSRS support (Check for shears & farm in Edgeville)
		super(new StandardUIController(WOOL_NAME, PRICE_POLLER, Environment.isRS3() ? 28 : 27));
		super.logicTree = new InventoryFullBranch(new IsInSheepPenBranch( new JumpOverStileTask(), Lumbridge.getNorthBankAccessBranch(new BankEverythingTask((ITimedTaskDoneHandler)this.uiController)) ),
												  new IsInSheepPenBranch(new GetWoolTask(), new IsNearPenBranch())
					    					     );
	}
	@Override
	public void onStart(String... args)
	{
		this.setLoopDelay(500, 750);
		System.out.println("Loaded ILikeSheep v1.0");
	}
}
