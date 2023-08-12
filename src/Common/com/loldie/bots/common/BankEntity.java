package com.loldie.bots.common;
public class BankEntity
{
	public static final BankEntity DEFAULT_BANK_OBJECT = new BankEntity("Bank booth", "Bank");
	public static final BankEntity DEFAULT_BANK_NPC = new BankEntity("Banker", "Bank");
	private String entityName;
	private String actionName;
	public BankEntity(String entityName, String actionName)
	{
		this.entityName = entityName;
		this.actionName = actionName;
	}
	public String getEntityName()
	{
		return entityName;
	}
	public String getActionName()
	{
		return actionName;
	}
}
