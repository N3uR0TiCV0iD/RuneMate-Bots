package com.loldie.bots.common.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.loldie.bots.common.banks.IBankingDoneHandler;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
public class BankEverythingTask extends LogicTask
{
	private IBankingDoneHandler bankingHandler;
	public BankEverythingTask(IBankingDoneHandler bankingHandler)
	{
		this.bankingHandler = bankingHandler;
	}
	@Override
	public void runNode()
	{
		notifyNewTask("Banking everything...");
		Bank.depositInventory();
		bankingHandler.onBankingDone();
	}
}
