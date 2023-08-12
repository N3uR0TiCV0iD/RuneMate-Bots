package com.loldie.bots.common.items;

public class StaticItemPricePoller extends ItemPricePoller
{
	private int itemPrice;
	public StaticItemPricePoller(int itemID, int itemPrice)
	{
		super(itemID);
		this.itemPrice = itemPrice;
	}
	@Override
	public void update() { }
	@Override
	public int getItemPrice()
	{
		return itemPrice;
	}
	@Override
	public String getLastUpdateString()
	{
		return "STATIC PRICE";
	}
}
