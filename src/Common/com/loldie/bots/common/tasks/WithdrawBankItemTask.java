package com.loldie.bots.common.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.loldie.bots.common.items.ItemAmount;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
public class WithdrawBankItemTask extends LogicTask
{
	private String itemName;
	private ItemAmount itemAmount;
	public WithdrawBankItemTask(String itemName, ItemAmount itemAmount)
	{
		this.itemName = itemName;
		this.itemAmount = itemAmount;
	}
	@Override
	public void runNode()
	{
		int withdrawAmount = itemAmount.getAmount() - Inventory.getQuantity(itemAmount.getItemID());
		notifyNewTask("Withdrawing " + withdrawAmount + " " + itemName + "...");
		Bank.withdraw(itemAmount.getItemID(), withdrawAmount);
	}
}
