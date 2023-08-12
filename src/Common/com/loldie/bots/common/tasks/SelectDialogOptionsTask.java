package com.loldie.bots.common.tasks;
import java.util.List;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.location.navigation.basic.PredefinedPath;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog.Continue;
//import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
public class SelectDialogOptionsTask extends LogicTask
{
	private String taskDescription;
	private String[] optionSelectors;
	public SelectDialogOptionsTask(String taskDescription, String... optionSelectors)
	{
		this.taskDescription = taskDescription;
		this.optionSelectors = optionSelectors;
	}
	@Override
	public void runNode()
	{
		Continue continueOption = ChatDialog.getContinue();
		notifyNewTask(taskDescription);
		if (continueOption != null)
		{
			continueOption.select(true);
		}
		else
		{
			List<ChatDialog.Option> options = ChatDialog.getOptions();
			boolean choseOption = false;
			if (options.size() != 0)
			{
				for (ChatDialog.Option currOption : options)
				{
					String currOptionText = currOption.getText();
					for (String currOptionSelector : optionSelectors)
					{
						if (currOptionSelector.equals(currOptionText))
						{
							currOption.select(true);
							choseOption = true;
							break;
						}
					}
				}
			}
			if (!choseOption)
			{
				PredefinedPath.create(Players.getLocal().getPosition()); //Cancel the dialog by walking on the spot you are at
				//ViewportPath.convert( RegionPath.buildTo(Players.getLocal().getPosition()) ); 
			}
		}
	}
}
