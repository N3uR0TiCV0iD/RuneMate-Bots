package com.loldie.bots.knightsword;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.BuyFromGrandExchangeTask;
import com.loldie.bots.common.tasks.SelectDialogOptionsTask;
import com.loldie.bots.common.tasks.StopBotTask;
import com.loldie.bots.common.tasks.TalkToNPCTask;
import com.loldie.bots.common.tasks.WithdrawBankItemTask;
import com.loldie.bots.common.tasks.WithdrawBankMoneyTask;
import com.loldie.bots.common.MembershipPreference;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.branches.HasBankItemsBranch;
import com.loldie.bots.common.branches.HasGoldBranch;
import com.loldie.bots.common.branches.HasItemsBranch;
import com.loldie.bots.common.branches.IsChatDialogOpenBranch;
import com.loldie.bots.common.choices.VarpChoiceBranches;
import com.loldie.bots.common.inherit.BaseTreeBot;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.locations.Varrock;
import com.loldie.bots.knightsword.branches.InFaladorCastleBranch;
import com.loldie.bots.knightsword.branches.InReldoLibraryBranch;
import com.loldie.bots.knightsword.branches.IsNearThurgoHouseBranch;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.Varps;
public class KnightsSwordQuest extends BaseTreeBot
{
	private static final int REDBERRY_PIE_ID = 2325;
	private static final ItemAmount REDBERRY_PIE = new ItemAmount(REDBERRY_PIE_ID, 1);
	private static final int REDBERRY_PIE_PRICE = (int)(Utils.getGrandExchangePrice(REDBERRY_PIE_ID) * 2.75F);
	private static final String[] QUEST_START_OPTIONSELECTORS = new String[] {
		"And how is life as a squire?",
		"I can make a new sword if you like...",
		"So would these dwarves make another one?",
		"Ok, I'll give it a go."
	};
	private static final String[] GIVE_PIE_OPTIONSELECTORS = new String[] {
			
	};
	private static final SelectDialogOptionsTask QUEST_START_SELECTOPTIONS_TASK = new SelectDialogOptionsTask("Starting quest...", QUEST_START_OPTIONSELECTORS);
	private static final SelectDialogOptionsTask GIVE_PIE_SELECTOPTIONS_TASK = new SelectDialogOptionsTask("Giving redberry pie to Thurgo...", GIVE_PIE_OPTIONSELECTORS);
	public KnightsSwordQuest()
	{
		super(MembershipPreference.NONE, false, true);
		//Varps[122]: 0 = Start quest
		//Varps[122]: 1 = Talk to Reldo
		//Varps[122]: 2 = Get pie and talk to thurgo
		//Varps[122]: 3 = Talk to thurgo about the sword
		//Varps[122]: 4 = Talked about the Sword
		//Varps[122]: 5 = Talk with Asrol about the the picture //HAS ITEM (Portrait) -> Get Iron Bars+Pickaxe then Talk to Thurgo, Else: Get portrait
		//Varps[122]: 6 = Handed in the portrait //HAS ITEM (Sword) -> Talk to Asrol, Else: Get Iron Bars+Pickaxe  then IF IS LVL10 Mining -> go get blurite ore, Else: Lvl up mining (Depending on RS version)
		//Varps[122]: 7 = Quest complete :D
		System.out.println("KnightsSwordQuest()");
		InFaladorCastleBranch talkToAsrolBranch = new InFaladorCastleBranch(new TalkToNPCTask(Environment.isRS3() ? "Squire Asrol" : "Squire"));
		LogicBranch withdrawPieOrGoldBranch = new HasBankItemsBranch(REDBERRY_PIE, new WithdrawBankItemTask("Redberry pie", REDBERRY_PIE),
																				   new WithdrawBankMoneyTask(REDBERRY_PIE_PRICE));
		VarpChoiceBranches questStageBranches = new VarpChoiceBranches(Varps.getAt(122), new StopBotTask("Quest completed!", this));
		LogicBranch giveThurgoPieBranch = new IsNearThurgoHouseBranch( new IsChatDialogOpenBranch(GIVE_PIE_SELECTOPTIONS_TASK, new TalkToNPCTask("Thurgo")) );
		LogicBranch getPieBranch = new HasGoldBranch(REDBERRY_PIE_PRICE, Varrock.getGrandExchangeAccessBranch(new BuyFromGrandExchangeTask("Redberry pie", 1, REDBERRY_PIE_PRICE, false)),
																		 Varrock.getWestBankAccessBranch(withdrawPieOrGoldBranch));
		questStageBranches.addPath(0, new IsChatDialogOpenBranch(QUEST_START_SELECTOPTIONS_TASK, talkToAsrolBranch)); //Start the quest
		questStageBranches.addPath(1, InReldoLibraryBranch.getInstance()); //Talk to Reldo
		questStageBranches.addPath(2, new HasItemsBranch(REDBERRY_PIE, true, giveThurgoPieBranch, getPieBranch)); //Get redberry pie & give it to Thurgo
		/*
		questStageBranches.addPath(2, new IsChatDialogOpenBranch(null, null)); //Get Pie and talk to Thurgo
		questStageBranches.addPath(3, new IsChatDialogOpenBranch(null, null)); //Talk to Thurgo 
		*/
		super.logicTree = questStageBranches;
	}
	@Override
	public void onStart(String... args)
	{
		super.onStart(args);
		System.out.println("Loaded KnightsSwordQuest v1.0");
		this.setLoopDelay(500, 750);
	}
}
