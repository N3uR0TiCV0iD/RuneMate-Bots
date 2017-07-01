package com.loldie.bots.givemebeer.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.location.navigation.basic.ViewportPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog.Continue;
public class OrderBeerTask extends LogicTask
{
	@Override
	public void runNode()
	{
		Continue continueOption = ChatDialog.getContinue();
		notifyNewTask("Ordering beer...");
		if (continueOption != null)
		{
			continueOption.select(true);
		}
		else if (ChatDialog.getTitle().equals("What would you like to say?"))
		{
			ChatDialog.getOption(3).select(true);
		}
		else
		{
			ViewportPath.convert( RegionPath.buildTo(Players.getLocal().getPosition()) );
		}
	}
}
