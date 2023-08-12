package com.loldie.bots.dotutorial;
import com.loldie.bots.common.MembershipPreference;
import com.loldie.bots.common.RockType;
import com.loldie.bots.common.TreeType;
import com.loldie.bots.common.branches.HasEquipmentBranch;
import com.loldie.bots.common.branches.IsBankOpenBranch;
import com.loldie.bots.common.branches.IsChatDialogOpenBranch;
import com.loldie.bots.common.branches.IsInterfaceVisibleBranch;
import com.loldie.bots.common.inherit.StandardTreeBot;
import com.loldie.bots.common.logic.EmptyTask;
import com.loldie.bots.common.logic.ILogicNode;
import com.loldie.bots.common.logic.LogicAreaBranch;
import com.loldie.bots.common.tasks.CastSpellOnNPCTask;
import com.loldie.bots.common.tasks.ChopDownTreeTask;
import com.loldie.bots.common.tasks.ClickOnInterfaceTask;
import com.loldie.bots.common.tasks.InteractWithNPCTask;
import com.loldie.bots.common.tasks.InteractWithObjectTask;
import com.loldie.bots.common.tasks.MineRocksTask;
import com.loldie.bots.common.tasks.OperateDoorTask;
import com.loldie.bots.common.tasks.SelectDialogOptionsTask;
import com.loldie.bots.common.tasks.StartFishingTask;
import com.loldie.bots.common.tasks.StopBotTask;
import com.loldie.bots.common.tasks.TalkToNPCTask;
import com.loldie.bots.common.tasks.UseItemOnItemTask;
import com.loldie.bots.common.tasks.UseItemOnObject;
import com.loldie.bots.common.tasks.UseItemTask;
import com.loldie.bots.common.inherit.BasicUIController;
import com.loldie.bots.dotutorial.branches.HasValidDisplayNameBranch;
import com.loldie.bots.dotutorial.branches.IsInRatPitBranch;
import com.loldie.bots.dotutorial.choices.TutorialStageChoiceBranches;
import com.loldie.bots.dotutorial.tasks.ClickContinueTask;
import com.loldie.bots.dotutorial.tasks.RandomizeLookTask;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.osrs.local.hud.interfaces.Magic;
public class DoTutorial extends StandardTreeBot
{
	private static final int SHORTBOW_ID = 841;
	private static final int BRONZE_ARROW_ID = 882;
	private static final int BRONZE_DAGGER_ID = 1205;
	private static final int CLOSE_BANK_BUTTON_ID = 786435;
	private static final int MAGIC_MENU_BUTTON_ID = 10747961;
	private static final int COMBAT_MENU_BUTTON_ID = 10747955;
	private static final int SKILLS_MENU_BUTTON_ID = 10747956;
	private static final int QUESTS_MENU_BUTTON_ID = 10747957;
	private static final int PRAYER_MENU_BUTTON_ID = 10747960;
	private static final int SMITH_DAGGER_BUTTON_ID = 20447241;
	private static final int ACCOUNT_MENU_BUTTON_ID = 10747941;
	private static final int FRIENDS_MENU_BUTTON_ID = 10747942;
	private static final int OPTIONS_MENU_BUTTON_ID = 10747943;
	private static final int EQUIPMENT_MENU_BUTTON_ID = 10747959;
	private static final int CLOSE_EQUIPMENT_BUTTON_ID = 5505028;
	private static final int TUTORIAL_PROGRESS_LABEL_ID = 40239125;
	private static final int EQUIP_YOUR_CHARACTER_LABEL_ID = 5505027;
	private static final int EQUIPMENT_STATS_MENU_BUTTON_ID = 25362449;
	private static final Coordinate DOOR1_POS = new Coordinate(3097, 3107, 0);
	private static final Coordinate DOOR2_POS = new Coordinate(3079, 3084, 0);
	private static final Coordinate DOOR3_POS = new Coordinate(3073, 3090, 0);
	private static final Coordinate DOOR4_POS = new Coordinate(3086, 3126, 0);
	private static final Coordinate DOOR5_POS = new Coordinate(3124, 3124, 0);
	private static final Coordinate DOOR6_POS = new Coordinate(3129, 3124, 0);
	private static final Coordinate DOOR7_POS = new Coordinate(3122, 3103, 0);
	private static final Coordinate ROCKS_POS = new Coordinate(3080, 9503, 0);
	private static final Coordinate UPLADDER_POS = new Coordinate(3111, 9525, 0);
	private static final Area RATPIT_ENTRANCE_AREA = new Area.Circular(new Coordinate(3111, 9518, 0), 5);
	private static final Area CHAPEL_AREA = new Area.Circular(new Coordinate(3124, 3106, 0), 10);
	private static final Area WIZARD_AREA = new Area.Circular(new Coordinate(3141, 3086, 0), 10);
	private static final Area UPLADDER_AREA = new Area.Circular(UPLADDER_POS, 10);
	private static final Area ROCKS_AREA = new Area.Circular(ROCKS_POS, 10);
	private static final Area DOOR4_AREA = new Area.Circular(DOOR4_POS, 10);
	public DoTutorial()
	{
		super(new BasicUIController(), MembershipPreference.NONE, false, true);
		
		ILogicNode talkToQuestGuideBranch = talkToNPCBranch("Quest Guide");
		ILogicNode talkToPrayerGuideBranch = talkToNPCBranch("Brother Brace");
		ILogicNode talkToAccountGuideBranch = talkToNPCBranch("Account Guide");
		ILogicNode talkToMiningGuideBranch = talkToNPCBranch("Mining Instructor");
		ILogicNode talkToCombatGuideBranch = talkToNPCBranch("Combat Instructor");
		ILogicNode talkToSurvivalExpertBranch = talkToNPCBranch("Survival Expert");
		ILogicNode openGateTask = new InteractWithObjectTask("Opening gate...", "Open", "Gate");
		ILogicNode attackRatTask = new InteractWithNPCTask("Attacking Rat...", "Attack", "Giant rat");
		ILogicNode talkToGuideBranch = talkToNPCBranch("Gielinor Guide", "I am an experienced player.");
		ILogicNode talkToMagicGuideBranch = talkToNPCBranch("Magic Instructor", "Yes.", "No, I'm not planning to do that.");
		ILogicNode clickEquipmentMenuTask = new ClickOnInterfaceTask("Clicking equipment button...", EQUIPMENT_MENU_BUTTON_ID);
		ILogicNode pollBoothBranch = new IsChatDialogOpenBranch(new ClickContinueTask(), new InteractWithObjectTask("Using Poll-booth...", "Use", "Poll booth"));
		ILogicNode tutorialCompleteBranch = new IsInterfaceVisibleBranch(TUTORIAL_PROGRESS_LABEL_ID, new EmptyTask(), new StopBotTask("Tutorial Island Completed!", this));
		TutorialStageChoiceBranches stageBranches = new TutorialStageChoiceBranches(tutorialCompleteBranch);

		stageBranches.addPath("To the mainland!", talkToMagicGuideBranch);
		stageBranches.addPath("Magic casting", new CastSpellOnNPCTask(Magic.WIND_STRIKE, null, "Chicken"));
		stageBranches.addPath("Magic\r\nThis is your magic interface", talkToMagicGuideBranch);
		stageBranches.addPath("Open up your final menu", new ClickOnInterfaceTask("Clicking magic menu button...", MAGIC_MENU_BUTTON_ID));
		stageBranches.addPath("Your final instructor!\r\nFollow the path", new LogicAreaBranch(WIZARD_AREA, talkToMagicGuideBranch, "Wizard house"));
		stageBranches.addPath("Your final instructor!\r\nYou're almost finished", new OperateDoorTask(DOOR7_POS));
		stageBranches.addPath("Friends and Ignore lists\r\nThese two lists", talkToPrayerGuideBranch);
		stageBranches.addPath("Friends and Ignore lists\r\nYou should now see another", new ClickOnInterfaceTask("Clicking friends button...", FRIENDS_MENU_BUTTON_ID));
		stageBranches.addPath("Prayer menu\r\nTalk with Brother Brace", talkToPrayerGuideBranch);
		stageBranches.addPath("Prayer menu\r\nClick on the flashing", new ClickOnInterfaceTask("Clicking prayer menu button...", PRAYER_MENU_BUTTON_ID));
		stageBranches.addPath("Prayer\r\nFollow the path", new LogicAreaBranch(CHAPEL_AREA, talkToPrayerGuideBranch, "Chapel"));
		stageBranches.addPath("Moving on\r\nContinue through the next door", new OperateDoorTask(DOOR6_POS));
		stageBranches.addPath("Account Management\r\nThis is your Account Management", talkToAccountGuideBranch);
		stageBranches.addPath("Account Management\r\nClick on the flashing", new ClickOnInterfaceTask("Clicking account management button...", ACCOUNT_MENU_BUTTON_ID));
		stageBranches.addPath("Account Management\r\nThe guide here will", talkToAccountGuideBranch);
		stageBranches.addPath("Moving on\r\nPolls are run periodically", new OperateDoorTask(DOOR5_POS));
		stageBranches.addPath("Poll booths", pollBoothBranch);
		stageBranches.addPath("Banking\r\nThis is your bank", new IsBankOpenBranch(new ClickOnInterfaceTask("Closing bank window...", CLOSE_BANK_BUTTON_ID, currInterface -> currInterface.getActions().size() != 0),
																				   pollBoothBranch));
		stageBranches.addPath("Banking\r\nFollow the path", new InteractWithObjectTask("Using bank booth...", "Use", "Bank booth"));
		stageBranches.addPath("Moving on\r\nYou have completed the tasks here", new LogicAreaBranch(UPLADDER_AREA, new InteractWithObjectTask("Going up the ladder...", "Climb-up", "Ladder"), "Ladder"));
		stageBranches.addPath( "Rat ranging", new HasEquipmentBranch(SHORTBOW_ID, new HasEquipmentBranch(BRONZE_ARROW_ID, attackRatTask, new UseItemTask("Bronze arrow")), new UseItemTask("Shortbow")) );
		stageBranches.addPath("Well done, you've made your first kill!", new IsInRatPitBranch(openGateTask, talkToCombatGuideBranch));
		stageBranches.addPath("Attacking", new IsInRatPitBranch(attackRatTask, openGateTask));
		stageBranches.addPath("Combat interface\r\nThis is your combat interface", new LogicAreaBranch(RATPIT_ENTRANCE_AREA, openGateTask, "Rat pit entrance"));
		stageBranches.addPath("Combat interface\r\nClick on the flashing crossed", new ClickOnInterfaceTask("Clicking combat menu button...", COMBAT_MENU_BUTTON_ID));
		stageBranches.addPath( "Unequipping items", new HasEquipmentBranch(BRONZE_DAGGER_ID, new UseItemTask("Bronze sword"), new UseItemTask("Wooden shield")) );
		stageBranches.addPath("Equipment stats\r\nYou're now holding your dagger",
							  new IsInterfaceVisibleBranch(EQUIP_YOUR_CHARACTER_LABEL_ID, new ClickOnInterfaceTask("Closing equipment window...", CLOSE_EQUIPMENT_BUTTON_ID),
									  					   talkToCombatGuideBranch));
		stageBranches.addPath("Equipment stats\r\nYou can see what items", new UseItemTask("Bronze dagger"));
		stageBranches.addPath("Worn inventory", new IsInterfaceVisibleBranch(EQUIPMENT_STATS_MENU_BUTTON_ID, new ClickOnInterfaceTask("Clicking equipment stats button...", EQUIPMENT_STATS_MENU_BUTTON_ID),
																			 clickEquipmentMenuTask));
		stageBranches.addPath("Equipping items", clickEquipmentMenuTask);
		stageBranches.addPath("Combat\r\nIn this area", talkToCombatGuideBranch);
		stageBranches.addPath("Moving on\r\nCongratulations, you've made your first weapon", openGateTask);
		stageBranches.addPath("Smithing a dagger\r\nNow you have the smithing menu open", new ClickOnInterfaceTask("Smithing dagger...", SMITH_DAGGER_BUTTON_ID));
		stageBranches.addPath("Smithing a dagger\r\nTo smith you'll need a hammer", new InteractWithObjectTask("Using the anvil...", "Smith", "Anvil"));
		stageBranches.addPath("Smelting\r\nYou've made a bronze bar!", talkToMiningGuideBranch);
		stageBranches.addPath("Smelting\r\nYou now have some tin", new InteractWithObjectTask("Using the furnace...", "Use", "Furnace"));
		stageBranches.addPath("Mining\r\nNow that you have some tin", new MineRocksTask(RockType.COPPER));
		stageBranches.addPath("Mining\r\nIt's quite simple really", new MineRocksTask(RockType.TIN));
		stageBranches.addPath("Mining and Smithing", new LogicAreaBranch(ROCKS_AREA, talkToMiningGuideBranch, "Rocks"));
		stageBranches.addPath("Moving on\r\nIt's time to enter some caves", new InteractWithObjectTask("Going down the ladder...", "Climb-down", "Ladder"));
		stageBranches.addPath("Quest journal\r\nThis is your quest journal", talkToQuestGuideBranch);
		stageBranches.addPath("Quest journal\r\nClick on the flashing", new ClickOnInterfaceTask("Clicking quests button...", QUESTS_MENU_BUTTON_ID));
		stageBranches.addPath("Quests", talkToQuestGuideBranch);
		stageBranches.addPath("Fancy a run?", new LogicAreaBranch(DOOR4_AREA, new OperateDoorTask(DOOR4_POS), "Fourth door"));
		stageBranches.addPath("Moving on\r\nWell done! You've baked", new OperateDoorTask(DOOR3_POS));
		stageBranches.addPath("Cooking dough", new UseItemOnObject("Bread dough", "Range"));
		stageBranches.addPath("Making dough", new UseItemOnItemTask("Pot of flour", "Bucket of water"));
		stageBranches.addPath("Cooking\r\nTalk to the chef", talkToNPCBranch("Master Chef"));
		stageBranches.addPath("Moving on\r\nFollow the path", new OperateDoorTask(DOOR2_POS));
		stageBranches.addPath("Moving on\r\nWell done, you've just cooked", openGateTask);
		stageBranches.addPath("Cooking\r\nNow it's time", new UseItemOnObject("Raw shrimps", "Fire"));
		stageBranches.addPath("Firemaking", new UseItemOnItemTask("Tinderbox", "Logs"));
		stageBranches.addPath("Woodcutting", new ChopDownTreeTask(TreeType.TREE));
		stageBranches.addPath("Skills and Experience", talkToSurvivalExpertBranch);
		stageBranches.addPath("You've gained some experience", new ClickOnInterfaceTask("Clicking skills button...", SKILLS_MENU_BUTTON_ID));
		stageBranches.addPath("Fishing", new StartFishingTask());
		stageBranches.addPath("You've been given an item", new ClickOnInterfaceTask("Clicking inventory button...", INVENTORY_MENU_BUTTON_ID));
		stageBranches.addPath("Moving around", talkToSurvivalExpertBranch);
		stageBranches.addPath("Moving on\r\nIt's time to meet", new OperateDoorTask(DOOR1_POS));
		stageBranches.addPath("Options menu\r\nOn the side panel, you can now see", talkToGuideBranch);
		stageBranches.addPath("Options menu\r\nPlease click on the flashing spanner", new ClickOnInterfaceTask("Clicking options button...", OPTIONS_MENU_BUTTON_ID));
		stageBranches.addPath("Getting started", talkToGuideBranch);
		stageBranches.addPath("Setting your appearance", new RandomizeLookTask());
		super.logicTree = new HasValidDisplayNameBranch(stageBranches);
	}
	private ILogicNode talkToNPCBranch(String npcName, String... optionSelectors)
	{
		return new IsChatDialogOpenBranch(new SelectDialogOptionsTask("Talking with " + npcName + "...", optionSelectors), new TalkToNPCTask(true, npcName));
	}
	@Override
	public void onStart(String... args)
	{
		this.setLoopDelay(500, 750);
		System.out.println("Loaded DoTutorial v1.0");
	}
}
