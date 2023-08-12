package com.loldie.bots.common.branches;
import java.util.function.Predicate;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class IsInterfaceVisibleBranch extends LogicBranch
{
	private int interfaceID;
	private Predicate<InterfaceComponent> filter;
	public IsInterfaceVisibleBranch(int interfaceID, ILogicNode truePath, ILogicNode falsePath)
	{
		this(interfaceID, null, truePath, falsePath);
	}
	public IsInterfaceVisibleBranch(int interfaceID, Predicate<InterfaceComponent> filter, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.interfaceID = interfaceID;
		this.filter = filter;
	}
	@Override
	protected boolean condition()
	{
		return Utils.isInterfaceVisible(interfaceID, filter);
	}
}
