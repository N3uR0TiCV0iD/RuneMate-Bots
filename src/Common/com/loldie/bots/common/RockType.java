package com.loldie.bots.common;
import java.awt.Color;
public enum RockType
{
	TIN(0, "Tin", 106, 106, 106),
	COPPER(1, "Copper", 60, 44, 34),
	IRON(2, "Iron", 0, 0, 0),
	COAL(3, "Coal", 0, 0, 0);
	
	private int id;
	private String name;
	private Color color;
	private RockType(int id, String name, int r, int g, int b)
	{
		this.id = id;
		this.name = name;
		this.color = new Color(r, g, b);
	}
	public int getID()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public Color getColor()
	{
		return color;
	}
}
