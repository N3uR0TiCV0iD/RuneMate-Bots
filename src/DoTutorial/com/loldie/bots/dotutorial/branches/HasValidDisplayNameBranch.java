package com.loldie.bots.dotutorial.branches;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicBranch;
import com.runemate.game.api.hybrid.region.Players;
import com.loldie.bots.dotutorial.choices.TutorialStageChoiceBranches;
public class HasValidDisplayNameBranch extends LogicBranch
{
	public HasValidDisplayNameBranch(TutorialStageChoiceBranches stageChoiceBranches)
	{
		super(stageChoiceBranches, new IsNameAvailableBranch());
	}
	@Override
	protected boolean condition()
	{
		return Utils.isValidPlayerName(Players.getLocal().getName());
	}
}
