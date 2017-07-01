package com.loldie.bots.givemebeer.branches;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.loldie.bots.givemebeer.tasks.OrderBeerTask;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.givemebeer.tasks.StartBeerOrderTask;
import com.loldie.bots.common.branches.IsChatDialogOpenBranch;
public class InFaladorBarBranch extends LogicAreaBranch
{
	private static final Coordinate BAR_POS = new Coordinate(2955, 3375, 0);
	private static final Area BAR_AREA = new Area.Circular(BAR_POS, 10);
	public InFaladorBarBranch()
	{
		super(BAR_AREA, new IsChatDialogOpenBranch(new OrderBeerTask(), new StartBeerOrderTask()), "Falador Bar");
	}
}
