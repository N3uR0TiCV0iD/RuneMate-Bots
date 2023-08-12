package com.loldie.bots.ilikesheep.branches;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.location.Coordinate;
public class IsInSheepPenBranch extends LogicBranch
{
	public IsInSheepPenBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
	}
	@Override
	protected boolean condition()
	{
		Coordinate localPlayerPos = Players.getLocal().getPosition();
		int playerXPos = localPlayerPos.getX();
		int playerYPos = localPlayerPos.getY();
		if (playerXPos >= 3184 && playerXPos <= 3202 && playerYPos <= 3296)
		{
			int yLimit = playerXPos >= 3191 ? 3287 : 3288;
			return playerYPos >= yLimit;
		}
		return false;
	}
}
