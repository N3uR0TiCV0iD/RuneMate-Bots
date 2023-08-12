package com.loldie.bots.knightsword.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
public class StartKSQuestTask extends LogicTask
{
	private static final String killme = "Hello. I am the squire of Sir Vyvin";
	private static final String killme2 = "So how are you doing getting that sword?";
	private static final String questdone = "Hello friend!"; //If it starts with that we are done :)
	//When talking to Reldo, use option 3
	//Chose an option: (3 options) [Have a branch "Has Spoken To Reldo"]
	
	
	//When talking to Thrugo: ["What would you like to ask?"] -> Option 2 * 2
	
	//DIFFERENT BOT:
	//Lvl30 smithing:	13363 | XP per bronze bar:	6.2  | Copper+Tin cost: 50gp				| Withdraw 14 tin+copper
	//Lvl35 smithing:	22406 | XP per steel bar:	17.5 | Coal cost: 200gp; Iron cost: 250gp	| Withdraw 18 coal + All iron
	
	@Override
	public void runNode()
	{
		int optionsCount = ChatDialog.getOptions().size();
		switch (optionsCount)
		{
			default:
				ChatDialog.getContinue().select();
			break;
			case 2:
				ChatDialog.getOption(1).select();
			break;
			case 3:
				ChatDialog.getOption(2).select();
			break;
		}
	}
}
