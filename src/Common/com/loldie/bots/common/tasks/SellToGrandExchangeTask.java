package com.loldie.bots.common.tasks;
import com.runemate.game.api.hybrid.net.GrandExchange;
import com.runemate.game.api.hybrid.net.GrandExchange.Offer;
import com.loldie.bots.common.tasks.inherit.GrandExchangeTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
public class SellToGrandExchangeTask extends GrandExchangeTask
{
	public SellToGrandExchangeTask(String itemName, int goldPerItem)
	{
		super(itemName, goldPerItem, false, Offer::isSellOffer, GrandExchange::placeSellOffer);
	}
	@Override
	public void runNode()
	{
		notifyNewTask("Selling \"" + itemName + "\" to the Grand Exchange...");
		super.runNode();
	}
	@Override
	protected int getOfferQuantity()
	{
		return Inventory.getQuantity(itemName);
	}
}
