package com.loldie.bots.dotutorial.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.loldie.bots.dotutorial.IGenderChoicesGetter;
import com.loldie.bots.dotutorial.PlayerLookButtonInfo;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class RandomizeLookTask extends LogicTask
{
	private static final PlayerLookButtonInfo[] NEXT_STYLE_BUTTONS = new PlayerLookButtonInfo[] {
		new PlayerLookButtonInfo(17629297, 25),			//Head
		new PlayerLookButtonInfo(17629298, 16, 1),		//Jaw
		new PlayerLookButtonInfo(17629299, 15, 12),		//Torso
		new PlayerLookButtonInfo(17629300, 13, 12),		//Arms
		new PlayerLookButtonInfo(17629301, 3),			//Hands
		new PlayerLookButtonInfo(17629302, 12, 16),		//Legs
		new PlayerLookButtonInfo(17629303, 3)			//Feet
	};
	private static final PlayerLookButtonInfo[] NEXT_COLOR_BUTTONS = new PlayerLookButtonInfo[] {
		new PlayerLookButtonInfo(17629305, 26),			//Hair
		new PlayerLookButtonInfo(17629311, 16),			//Torso
		new PlayerLookButtonInfo(17629313, 16),			//Legs
		new PlayerLookButtonInfo(17629314, 7),			//Feet
		new PlayerLookButtonInfo(17629315, 6)			//Skin
	};
	private static final int PREVIOUS_SKIN_COLOR_BUTTON_ID = 17629309;
	private static final int FEMALE_GENDER_BUTTON_ID = 17629321;
	private static final int ACCEPT_LOOK_BUTTON_ID = 17629283;
	private boolean hasResetSkinColor;
	@Override
	public void runNode()
	{
		IGenderChoicesGetter choicesGetter;
		InterfaceComponent acceptButton = Utils.getInterfaceComponent(ACCEPT_LOOK_BUTTON_ID);
		notifyNewTask("Randomizing player appearance...");
		if (acceptButton != null)
		{
			maybeResetSkinButton();
			if (Utils.GENERATOR.nextBoolean())
			{
				choicesGetter = PlayerLookButtonInfo::getMaleChoices;
			}
			else
			{
				Utils.getInterfaceComponent(FEMALE_GENDER_BUTTON_ID).click();
				choicesGetter = PlayerLookButtonInfo::getFemaleChoices;
			}
			maybeResetSkinButton();
			randomizeWithButtons(NEXT_STYLE_BUTTONS, choicesGetter);
			maybeResetSkinButton();
			randomizeWithButtons(NEXT_COLOR_BUTTONS, choicesGetter);
			acceptButton.click();
		}
	}
	private void maybeResetSkinButton()
	{
		if (!hasResetSkinColor && Utils.GENERATOR.nextBoolean())
		{
			Utils.getInterfaceComponent(PREVIOUS_SKIN_COLOR_BUTTON_ID).click();
			hasResetSkinColor = true;
		}
	}
	private void randomizeWithButtons(PlayerLookButtonInfo[] buttonInfos, IGenderChoicesGetter choicesGetter)
	{
		for (PlayerLookButtonInfo currButtonInfo : buttonInfos)
		{
			InterfaceComponent currButton = Utils.getInterfaceComponent(currButtonInfo.getInterfaceID());
			if (currButton != null)
			{
				int clicksLeft = Utils.GENERATOR.nextInt(choicesGetter.getChoices(currButtonInfo));
				while (clicksLeft > 0)
				{
					currButton.click();
					Utils.randomDelay(600, 1200);
					clicksLeft--;
				}
			}
		}
	}
}
