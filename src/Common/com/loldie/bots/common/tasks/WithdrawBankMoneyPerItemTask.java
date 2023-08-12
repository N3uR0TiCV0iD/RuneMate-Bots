package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.items.ItemAmount;
public class WithdrawBankMoneyPerItemTask extends WithdrawBankMoneyTask
{
	private int goldPerAmount;
	private ItemAmount itemAmount;
	public WithdrawBankMoneyPerItemTask(ItemAmount itemAmount, int goldPerAmount)
	{
		super(0);
		this.itemAmount = itemAmount;
		this.goldPerAmount = goldPerAmount;
	}
	@Override
	public void runNode()
	{
		super.amount = Utils.getNeededGold(itemAmount, goldPerAmount);
		super.runNode();
	}
}
