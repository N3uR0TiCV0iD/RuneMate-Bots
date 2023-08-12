package com.loldie.bots.ilikecabbage.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.location.Coordinate;
public class IsInCabbageFarmBranch extends LogicBranch
{
	public IsInCabbageFarmBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
	}
	@Override
	protected boolean condition()
	{
		Coordinate localPlayerPos = Players.getLocal().getPosition();
		int playerXPos = localPlayerPos.getX();
		int playerYPos = localPlayerPos.getY();
		return playerXPos >= 3041 && playerXPos <= 3068 &&
			   playerYPos >= 3283 && playerYPos <= 3299;
	}
}
