package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
public class WithdrawBankMoneyTask extends LogicTask
{
	public static final int GOLDCOINS_ID = 995;
	protected int amount;
	public WithdrawBankMoneyTask(int amount)
	{
		this.amount = amount;
	}
	@Override
	public void runNode()
	{
		int withdrawAmount = amount - Utils.getPlayerGold();
		notifyNewTask("Withdrawing " + withdrawAmount + " coins...");
		Bank.withdraw(GOLDCOINS_ID, withdrawAmount);
	}
}
