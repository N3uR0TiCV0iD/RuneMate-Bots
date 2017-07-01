package com.loldie.bots.common.banks;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.OpenBankTask;
import com.loldie.bots.common.branches.IsBankOpenBranch;
public class Falador
{
	public static LogicBranch getWestBankAccessBranch(ILogicNode logicNode)
	{
		return new IsBankOpenBranch(logicNode, new InFaladorWestBankBranch(OpenBankTask.DEFAULT_OPENBANK_TASK));
	}
}
