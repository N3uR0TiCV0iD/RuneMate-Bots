package com.loldie.bots.dyemaker;
import com.loldie.bots.common.logic.LogicBranch;
import com.loldie.bots.common.tasks.StopBotTask;
import com.loldie.bots.common.locations.Draynor;
import com.loldie.bots.common.inherit.StandardTreeBot;
import com.loldie.bots.common.branches.HasItemsBranch;
import com.loldie.bots.common.tasks.BankEverythingTask;
import com.loldie.bots.common.tasks.WithdrawBankItemTask;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.MembershipPreference;
import com.loldie.bots.common.branches.HasBankItemsBranch;
import com.loldie.bots.common.inherit.StandardUIController;
import com.loldie.bots.common.items.ItemAmount;
import com.loldie.bots.common.items.ItemName;
import com.loldie.bots.common.items.ItemPricePoller;
import com.loldie.bots.common.items.UIControllerItem;
import com.loldie.bots.common.branches.HasGoldPerItemBranch;
import com.loldie.bots.dyemaker.branches.InAggieHouseBranch;
import com.loldie.bots.common.tasks.WithdrawBankMoneyPerItemTask;
public class DyeMaker extends StandardTreeBot
{
	public static final int ONION_ID = 1957;
	public static final int BLUEDYE_ID = 1767;
	public static final int WOADLEAF_ID = 1793;
	public static final int YELLOWDYE_ID = 1765;
	private static final int ITEMS_PER_BANK = 13;
	private static final ItemPricePoller YELLOWDYE_PRICEPOLLER = new ItemPricePoller(YELLOWDYE_ID);
	private static final ItemPricePoller BLUEDYE_PRICEPOLLER = new ItemPricePoller(BLUEDYE_ID);
	private static final ItemAmount WOADLEAVES_COSTAMOUNT = new ItemAmount(WOADLEAF_ID, 2);
	private static final ItemAmount WOADLEAVES_GETAMOUNT = new ItemAmount(WOADLEAF_ID, 26);
	private static final ItemName YELLOWDYE_NAME = new ItemName("YellowDye", "YellowDyes");
	private static final ItemName BLUEDYE_NAME = new ItemName("BlueDye", "BlueDyes");
	private static final ItemAmount ONIONS_COSTAMOUNT = new ItemAmount(ONION_ID, 2);
	private static final ItemAmount ONIONS_GETAMOUNT = new ItemAmount(ONION_ID, 26);
	private static final ItemAmount[] DYES = new ItemAmount[] {
		new ItemAmount(BLUEDYE_ID, 1), new ItemAmount(YELLOWDYE_ID, 1)
	};
	public DyeMaker()
	{
		super(new StandardUIController(createUIControllerItems()), MembershipPreference.PREFERED, true, true);
		StopBotTask stopBotTask;
		LogicBranch hasDyesBranch;
		LogicBranch storeItemsBranch;
		LogicBranch getBDMoneyBranch; //BD = Blue dyes
		LogicBranch getYDMoneyBranch; //YD = Yellow dyes
		LogicBranch hasMaterialsBranch;
		LogicBranch getMaterialsBranch;
		LogicBranch makeBlueDyesBranch;
		LogicBranch makeYellowDyesBranch;
		stopBotTask = new StopBotTask("Not enough dye ingredients found!", this);
		getYDMoneyBranch = Draynor.getBankAccessBranch(new WithdrawBankMoneyPerItemTask(ONIONS_COSTAMOUNT, 5));
		getBDMoneyBranch = Draynor.getBankAccessBranch(new WithdrawBankMoneyPerItemTask(WOADLEAVES_COSTAMOUNT, 5));
		storeItemsBranch = Draynor.getBankAccessBranch(new BankEverythingTask((ITimedTaskDoneHandler)this.uiController));
		getMaterialsBranch = Draynor.getBankAccessBranch( new HasBankItemsBranch(ONIONS_COSTAMOUNT, new WithdrawBankItemTask("onions", ONIONS_GETAMOUNT), 
				  										  new HasBankItemsBranch(WOADLEAVES_COSTAMOUNT, new WithdrawBankItemTask("woad leaves", WOADLEAVES_GETAMOUNT), stopBotTask)) );
		makeBlueDyesBranch = new HasGoldPerItemBranch(WOADLEAVES_COSTAMOUNT, 5, new InAggieHouseBranch(true), getBDMoneyBranch);
		makeYellowDyesBranch = new HasGoldPerItemBranch(ONIONS_COSTAMOUNT, 5, new InAggieHouseBranch(false), getYDMoneyBranch);
		hasDyesBranch = new HasItemsBranch(DYES, false, storeItemsBranch, getMaterialsBranch); //If we currently have no dyes either, we'll just get the materials
		hasMaterialsBranch = new HasItemsBranch(ONIONS_COSTAMOUNT, true, makeYellowDyesBranch,
				 			 new HasItemsBranch(WOADLEAVES_COSTAMOUNT, true, makeBlueDyesBranch, hasDyesBranch)); //If we have no materials, we'll check if we have dyes to be stored ;)
		super.logicTree = hasMaterialsBranch;
	}
	private static UIControllerItem[] createUIControllerItems()
	{
		return new UIControllerItem[] {
			new UIControllerItem(YELLOWDYE_NAME, YELLOWDYE_PRICEPOLLER, ITEMS_PER_BANK),
			new UIControllerItem(BLUEDYE_NAME, BLUEDYE_PRICEPOLLER, ITEMS_PER_BANK)
		};
	}
	@Override
	public void onStart(String... args)
	{
		super.onStart(args);
		this.setLoopDelay(450, 650);
		System.out.println("Loaded DyeMaker v1.0");
	}
}
