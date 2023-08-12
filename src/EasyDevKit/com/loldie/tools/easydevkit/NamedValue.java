package com.loldie.tools.easydevkit;
public class NamedValue
{
	private String name;
	private String value;
	public NamedValue(String name)
	{
		this.name = name;
		this.value = "";
	}
	public NamedValue(String name, String value)
	{
		this.name = name;
		this.value = value;
	}
	public String getName()
	{
		return name;
	}
	public String getValue()
	{
		return value;
	}
}
