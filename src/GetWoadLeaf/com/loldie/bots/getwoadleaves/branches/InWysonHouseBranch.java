package com.loldie.bots.getwoadleaves.branches;
import com.loldie.bots.common.tasks.TalkToNPCTask;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.common.tasks.SelectDialogOptionsTask;
import com.loldie.bots.common.branches.IsChatDialogOpenBranch;
public class InWysonHouseBranch extends LogicAreaBranch
{
	private static final String[] OPTION_SELECTORS = new String[] {
		"Yes please, I need woad leaves.",
		"How about 20 coins?"
	};
	private static final SelectDialogOptionsTask SELECT_OPTIONS_TASK = new SelectDialogOptionsTask("Buying woad leaves...", OPTION_SELECTORS);
	private static final Coordinate HOUSE_POS = new Coordinate(3028, 3379, 0);
	private static final Area HOUSE_AREA = new Area.Circular(HOUSE_POS, 10);
	public InWysonHouseBranch(ITimedTaskDoneHandler timedTaskDoneHandler)
	{
		super(HOUSE_AREA, new IsChatDialogOpenBranch(SELECT_OPTIONS_TASK, new TalkToNPCTask(false, "Wyson the gardener"), timedTaskDoneHandler), "Wyson's House (The woad leaf seller)");
	}
}
