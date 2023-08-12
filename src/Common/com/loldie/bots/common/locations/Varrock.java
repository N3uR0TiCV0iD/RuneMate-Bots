package com.loldie.bots.common.locations;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.OpenBankTask;
import com.loldie.bots.common.tasks.InteractWithNPCTask;
import com.loldie.bots.common.branches.IsBankOpenBranch;
import com.loldie.bots.common.branches.IsGrandExchangeOpenBranch;
public class Varrock
{
	private static final InteractWithNPCTask OPENGRANDEXCHANGE_TASK = new InteractWithNPCTask("Opening the Grand Exchange", "Exchange", "Grand Exchange Clerk");
	public static LogicBranch getWestBankAccessBranch(ILogicNode logicNode)
	{
		return new IsBankOpenBranch(logicNode, new InVarrockWestBankBranch(OpenBankTask.DEFAULT_OPENBANK_TASK));
	}
	public static LogicBranch getEastBankAccessBranch(ILogicNode logicNode)
	{
		return new IsBankOpenBranch(logicNode, new InVarrockEastBankBranch(OpenBankTask.DEFAULT_OPENBANK_TASK));
	}
	public static LogicBranch getGrandExchangeBankAccessBranch(ILogicNode logicNode)
	{
		return new IsBankOpenBranch(logicNode, new InGrandExchangeBranch(OpenBankTask.DEFAULT_OPENBANK_TASK));
	}
	public static LogicBranch getGrandExchangeAccessBranch(ILogicNode logicNode)
	{
		return new IsGrandExchangeOpenBranch(logicNode, new InGrandExchangeBranch(OPENGRANDEXCHANGE_TASK));
	}
}
