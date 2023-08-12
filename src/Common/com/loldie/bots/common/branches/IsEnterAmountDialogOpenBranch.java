package com.loldie.bots.common.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.local.hud.interfaces.EnterAmountDialog;
public class IsEnterAmountDialogOpenBranch extends LogicBranch
{
	public IsEnterAmountDialogOpenBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
	}
	@Override
	protected boolean condition()
	{
		return EnterAmountDialog.isOpen();
	}
}
