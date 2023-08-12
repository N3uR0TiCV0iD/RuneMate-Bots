package com.loldie.bots.dotutorial.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class LookUpNameTask extends LogicTask
{
	private static final int LOOK_UP_NAME_LABEL_ID = 36569105;
	@Override
	public void runNode()
	{
		InterfaceComponent lookUpNameLabel = Utils.getInterfaceComponent(LOOK_UP_NAME_LABEL_ID);
		notifyNewTask("Opening choose name dialog...");
		if (lookUpNameLabel != null)
		{
			lookUpNameLabel.click();
		}
	}
}
