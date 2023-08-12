package com.loldie.bots.common.items;
public class ItemAmount
{
	private int itemID;
	private int amount;
	public ItemAmount(int itemID, int amount)
	{
		this.itemID = itemID;
		this.amount = amount;
	}
	public int getItemID()
	{
		return itemID;
	}
	public int getAmount()
	{
		return amount;
	}
}
