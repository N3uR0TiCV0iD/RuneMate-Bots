package com.loldie.bots.common.locations;
import com.loldie.bots.common.BankEntity;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.OpenBankTask;
import com.loldie.bots.common.branches.IsBankOpenBranch;
public class Lumbridge
{
	private static final OpenBankTask OPEN_NORTH_BANK_TASK = new OpenBankTask(new BankEntity("Bank chest", "Use"), null);
	public static LogicBranch getNorthBankAccessBranch(ILogicNode logicNode)
	{
		return new IsBankOpenBranch(logicNode, new InLumbridgeNorthBankBranch(OPEN_NORTH_BANK_TASK));
	}
}
