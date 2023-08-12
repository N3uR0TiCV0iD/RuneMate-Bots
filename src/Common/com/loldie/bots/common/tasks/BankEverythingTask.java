package com.loldie.bots.common.tasks;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
public class BankEverythingTask extends LogicTask
{
	private ITimedTaskDoneHandler bankingHandler;
	public BankEverythingTask() { }
	public BankEverythingTask(ITimedTaskDoneHandler bankingHandler)
	{
		this.bankingHandler = bankingHandler;
	}
	@Override
	public void runNode()
	{
		notifyNewTask("Banking everything...");
		Bank.depositInventory();
		if (bankingHandler != null)
		{
			bankingHandler.onTimedTaskDone();
		}
	}
}
