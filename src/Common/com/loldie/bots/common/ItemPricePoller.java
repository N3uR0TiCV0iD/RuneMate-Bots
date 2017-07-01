package com.loldie.bots.common;
public class ItemPricePoller
{
	private static final int PRICE_CHECKDELAY = 1800000; //Check every 30 minutes
	private int itemID;
	private int itemPrice;
	private long lastPriceCheck;
	private long nextPriceCheck;
	public ItemPricePoller(int itemID)
	{
		this.itemID = itemID;
		updateItemPrice();
	}
	public void update()
	{
		if (System.currentTimeMillis() >= nextPriceCheck)
		{
			updateItemPrice();
		}
	}
	private void updateItemPrice()
	{
		lastPriceCheck = System.currentTimeMillis();
		itemPrice = Utils.getGrandExchangePrice(itemID);
		nextPriceCheck = lastPriceCheck + PRICE_CHECKDELAY;
	}
	public int getItemPrice()
	{
		return itemPrice;
	}
	public String getLastUpdateString()
	{
		return Utils.getTimeFromMs(System.currentTimeMillis() - lastPriceCheck);
	}
}
