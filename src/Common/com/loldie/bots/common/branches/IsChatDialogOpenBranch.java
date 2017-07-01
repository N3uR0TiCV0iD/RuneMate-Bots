package com.loldie.bots.common.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
public class IsChatDialogOpenBranch extends LogicBranch
{
	public IsChatDialogOpenBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
	}
	@Override
	protected boolean condition()
	{
		return ChatDialog.getText() != null || ChatDialog.getTitle() != null;
	}
}
