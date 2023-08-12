package com.loldie.bots.common.tasks;
import com.loldie.bots.common.tasks.inherit.GrandExchangeTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
public class BuyFromGrandExchangeTask extends GrandExchangeTask
{
	private int amount;
	public BuyFromGrandExchangeTask(String itemName, int amount, int goldPerItem, boolean collectNotes)
	{
		super(itemName, goldPerItem, collectNotes, null, null);
		this.amount = amount;
	}
	@Override
	public void runNode()
	{
		notifyNewTask("Buying \"" + itemName + "\" from the Grand Exchange...");
		super.runNode();
	}
	@Override
	protected int getOfferQuantity()
	{
		int quantity = amount - Inventory.getQuantity(itemName);
		return Math.max(quantity, 0);
	}
}
