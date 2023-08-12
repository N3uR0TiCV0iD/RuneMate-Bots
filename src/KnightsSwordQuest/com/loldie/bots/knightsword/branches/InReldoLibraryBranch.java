package com.loldie.bots.knightsword.branches;
import com.loldie.bots.common.tasks.TalkToNPCTask;
import com.runemate.game.api.hybrid.location.Area;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.common.branches.IsDoorOpenBranch;
import com.loldie.bots.common.tasks.SelectDialogOptionsTask;
import com.loldie.bots.common.branches.IsChatDialogOpenBranch;
public class InReldoLibraryBranch extends LogicAreaBranch
{
	private static final Coordinate DOOR_POS = new Coordinate(3210, 3489, 0);
	private static final Coordinate LIBRARY_POS = new Coordinate(3210, 3493, 0);
	private static final Area LIBRARY_AREA = new Area.Circular(LIBRARY_POS, 10);
	private static InReldoLibraryBranch instance;
	private InReldoLibraryBranch()
	{
		super(LIBRARY_AREA, new IsDoorOpenBranch( DOOR_POS, new IsChatDialogOpenBranch(new SelectDialogOptionsTask("Asking about Imcando Dwarves...", "What do you know about the Imcando dwarves?"),
																					   new TalkToNPCTask("Reldo")) ), "Reldo's library");
	}
	public static InReldoLibraryBranch getInstance()
	{
		if (instance == null)
		{
			instance = new InReldoLibraryBranch();
		}
		return instance;
	}
}
