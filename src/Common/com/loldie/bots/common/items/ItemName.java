package com.loldie.bots.common.items;

public class ItemName
{
	private String pluralName;
	private String singularName;
	public ItemName(String name)
	{
		this.singularName = name;
		this.pluralName = name;
	}
	public ItemName(String singularName, String pluralName)
	{
		this.singularName = singularName;
		this.pluralName = pluralName;
	}
	public String getSingularName()
	{
		return singularName;
	}
	public String getPluralName()
	{
		return pluralName;
	}
}
