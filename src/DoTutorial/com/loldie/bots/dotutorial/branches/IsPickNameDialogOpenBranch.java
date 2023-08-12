package com.loldie.bots.dotutorial.branches;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.dotutorial.tasks.PickNameTask;
import com.loldie.bots.dotutorial.tasks.LookUpNameTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class IsPickNameDialogOpenBranch extends LogicBranch
{
	private static final int PLEASE_PICK_NAME_LABEL_ID = 10616876;
	public IsPickNameDialogOpenBranch()
	{
		super(new PickNameTask(), new LookUpNameTask());
	}
	@Override
	protected boolean condition()
	{
		InterfaceComponent pleasePickNameLabel = Utils.getInterfaceComponent(PLEASE_PICK_NAME_LABEL_ID);
		return pleasePickNameLabel != null && pleasePickNameLabel.isVisible() && pleasePickNameLabel.getText().startsWith("Please pick a unique display name");
	}
}
