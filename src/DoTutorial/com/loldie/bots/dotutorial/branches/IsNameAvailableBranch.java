package com.loldie.bots.dotutorial.branches;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.ClickOnInterfaceTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class IsNameAvailableBranch extends LogicBranch
{
	private static final int NAME_AVAILABLE_LABEL_ID = 36569100;
	private static final int SET_NAME_LABEL_ID = 36569106;
	public IsNameAvailableBranch()
	{
		super(new ClickOnInterfaceTask("Setting display name...", SET_NAME_LABEL_ID), new IsPickNameDialogOpenBranch());
	}
	@Override
	protected boolean condition()
	{
		InterfaceComponent nameAvailableLabel = Utils.getInterfaceComponent(NAME_AVAILABLE_LABEL_ID);
		return nameAvailableLabel != null && nameAvailableLabel.getText().startsWith("Great!");
	}
}
