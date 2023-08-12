package com.loldie.bots.common.locations;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.OpenBankTask;
import com.loldie.bots.common.branches.IsBankOpenBranch;
public class Edgeville
{
	public static LogicBranch getBankAccessBranch(ILogicNode logicNode)
	{
		return new IsBankOpenBranch(logicNode, new InEdgevilleBankBranch(OpenBankTask.DEFAULT_OPENBANK_TASK));
	}
}
