package com.loldie.bots.dotutorial.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog.Continue;
public class ClickContinueTask extends LogicTask
{
	@Override
	public void runNode()
	{
		Continue continueOption = ChatDialog.getContinue();
		notifyNewTask("Clicking continue...");
		if (continueOption != null)
		{
			continueOption.select(true);
		}
	}
}
