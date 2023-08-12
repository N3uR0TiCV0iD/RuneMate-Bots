package com.loldie.bots.common.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.region.Players;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog.Continue;
public class IsChatDialogOpenBranch extends LogicBranch
{
	private boolean wasOpen;
	private ITimedTaskDoneHandler timedTaskDoneHandler;
	public IsChatDialogOpenBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
	}
	public IsChatDialogOpenBranch(ILogicNode truePath, ILogicNode falsePath, ITimedTaskDoneHandler timedTaskDoneHandler)
	{
		super(truePath, falsePath);
		this.timedTaskDoneHandler = timedTaskDoneHandler;
	}
	@Override
	protected boolean condition()
	{
		boolean isOpen;
		if (Environment.isRS3())
		{
			isOpen = ChatDialog.getText() != null || ChatDialog.getTitle() != null;
		}
		else
		{
			String text = ChatDialog.getText();
			Continue continueOption = ChatDialog.getContinue();
			isOpen = (continueOption != null && continueOption.isValid()) || ChatDialog.getTitle() != null ||
					 (text != null && !text.startsWith(Players.getLocal().getName() + ": ")) || ChatDialog.getOptions().size() != 0; //I wanted to pull my hair out @ the "text" & options part :)))
		}
		if (timedTaskDoneHandler != null)
		{
			if (isOpen && !wasOpen)
			{
				timedTaskDoneHandler.onTimedTaskDone();
			}
			wasOpen = isOpen;
		}
		return isOpen;
	}
}
