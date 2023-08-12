package com.loldie.bots.dotutorial.choices;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicChoiceBranches;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class TutorialStageChoiceBranches extends LogicChoiceBranches<String>
{
	private static final int TUTORIAL_STAGE_LABEL_ID = 17235969;
	public TutorialStageChoiceBranches(ILogicNode defaultPath)
	{
		super(defaultPath);
	}
	@Override
	protected String getCurrentValue()
	{
		InterfaceComponent tutorialStageLabel = Utils.getInterfaceComponent(TUTORIAL_STAGE_LABEL_ID, currInterface -> currInterface.getText() != null);
		if (tutorialStageLabel != null)
		{
			return Utils.safeToString(tutorialStageLabel.getText());
		}
		return "null";
	}
	@Override
	protected boolean evaluatePath(String left, String right)
	{
		return left.startsWith(right);
	}
}
