package com.loldie.bots.dotutorial.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.loldie.bots.dotutorial.NameGenerator;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class PickNameTask extends LogicTask
{
	private static final int BACKSPACE_KEYCODE = 8;
	private static final int DIALOG_INPUT_LABEL_ID = 10616877;
	@Override
	public void runNode()
	{
		InterfaceComponent dialogInputLabel = Utils.getInterfaceComponent(DIALOG_INPUT_LABEL_ID);
		if (dialogInputLabel != null)
		{
			String generatedName = NameGenerator.generateName();
			String dialogInputText = dialogInputLabel.getText();
			int realInputLength = dialogInputText.length() - 1;
			notifyNewTask("Picking \"" + generatedName + "\" as a display name...");
			if (realInputLength != 0) //Does the input contain anything other than "*"? (This should only happen if the user has tampered with the input before launching the bot)
			{
				//Yes it does! Let's see how many characters we need to remove to start matching the letters
				int charsToDelete;
				boolean match = true;
				int matchingChars = 0;
				int maxCompareChars = Math.min(realInputLength, generatedName.length());
				while (match && matchingChars < maxCompareChars)
				{
					if (dialogInputText.charAt(matchingChars) == generatedName.charAt(matchingChars))
					{
						matchingChars++;
					}
					else
					{
						match = false;
					}
				}
				charsToDelete = realInputLength - matchingChars;
				while (charsToDelete > 0)
				{
					Keyboard.pressKey(BACKSPACE_KEYCODE);
					Utils.randomInputDelay();
					charsToDelete--;
				}
				generatedName = generatedName.substring(matchingChars);
			}
			Keyboard.type(generatedName, true);
		}
	}
}
