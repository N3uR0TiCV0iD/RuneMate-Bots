package com.loldie.bots.dyemaker.branches;
import com.loldie.bots.common.tasks.TalkToNPCTask;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.loldie.bots.common.branches.IsDoorOpenBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.common.tasks.SelectDialogOptionsTask;
import com.loldie.bots.common.branches.IsChatDialogOpenBranch;
public class InAggieHouseBranch extends LogicAreaBranch
{
	private static final Coordinate DOOR_POS = new Coordinate(3089, 3258, 0);
	private static final Coordinate HOUSE_POS = new Coordinate(3086, 3258, 0);
	private static final Area HOUSE_AREA = new Area.Circular(HOUSE_POS, 10);
	public InAggieHouseBranch(boolean blueDye)
	{
		super(HOUSE_AREA, createLogicBranch(blueDye), "Aggie's House (The dye maker)");
	}
	private static IsDoorOpenBranch createLogicBranch(boolean blueDye)
	{
		String dyeColor = blueDye ? "blue" : "yellow";
		String[] optionSelectors = new String[] {
			"Can you make dyes for me please?", 
			"What do you need to make " + dyeColor + " dye?",
			"Okay, make me some " + dyeColor + " dye please."
		};
		IsChatDialogOpenBranch requestDyesBranch = new IsChatDialogOpenBranch(new SelectDialogOptionsTask("Requesting dye...", optionSelectors), new TalkToNPCTask(false, "Aggie")); //Moved to a variable for readability :P
		return new IsDoorOpenBranch(DOOR_POS, requestDyesBranch);
	}
}
