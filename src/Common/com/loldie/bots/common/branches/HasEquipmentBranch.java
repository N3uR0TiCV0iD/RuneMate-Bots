package com.loldie.bots.common.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
public class HasEquipmentBranch extends LogicBranch
{
	private int[] itemIDs;
	public HasEquipmentBranch(int itemID, ILogicNode truePath, ILogicNode falsePath)
	{
		this(new int[] { itemID }, truePath, falsePath);
	}
	public HasEquipmentBranch(int[] itemIDs, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.itemIDs = itemIDs;
	}
	@Override
	protected boolean condition()
	{
		for (int currItemID : itemIDs)
		{
			if (!Equipment.contains(currItemID))
			{
				return false;
			}
		}
		return true;
	}
}
