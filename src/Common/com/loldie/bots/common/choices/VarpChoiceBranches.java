package com.loldie.bots.common.choices;
import com.runemate.game.api.hybrid.local.Varp;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicChoiceBranches;
public class VarpChoiceBranches extends LogicChoiceBranches<Integer>
{
	private Varp varp;
	public VarpChoiceBranches(Varp varp, ILogicNode defaultPath)
	{
		super(defaultPath);
		this.varp = varp;
	}
	@Override
	protected Integer getCurrentValue()
	{
		return varp.getValue();
	}
}
