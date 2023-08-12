package com.loldie.bots.common;

public enum TreeType
{
	TREE(0, "Tree"),
	OAK(1, "Oak"),
	WILLOW(2, "Willow"),
	YEW(3, "Yew");
	
	private int id;
	private String name;
	private TreeType(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	public int getID()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
}
