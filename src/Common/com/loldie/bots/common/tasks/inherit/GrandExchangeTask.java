package com.loldie.bots.common.tasks.inherit;
import java.util.ArrayList;
import java.util.function.Function;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.net.GrandExchange;
import com.loldie.bots.common.GrandExchangeOfferDetails;
import com.loldie.bots.common.IGrandExchangeOfferOperation;
import com.runemate.game.api.hybrid.net.GrandExchange.Slot;
import com.runemate.game.api.hybrid.net.GrandExchange.Offer;
import com.runemate.game.api.hybrid.net.GrandExchange.Offer.State;
public abstract class GrandExchangeTask extends LogicTask
{
	protected String itemName;
	protected int goldPerItem;
	private boolean collectNotes;
	private IGrandExchangeOfferOperation offerOperation;
	private Function<Offer, Boolean> isExpectedOperationFunction;
	public GrandExchangeTask(String itemName, int goldPerItem, boolean collectNotes, Function<Offer, Boolean> isExpectedOperationFunction, IGrandExchangeOfferOperation offerOperation)
	{
		//TODO: Support multiple items(?)
		this.itemName = itemName;
		this.goldPerItem = goldPerItem;
		this.collectNotes = collectNotes;
		this.offerOperation = offerOperation;
		this.isExpectedOperationFunction = isExpectedOperationFunction;
	}
	@Override
	public void runNode()
	{
		Utils.debugLog("GrandExchangeTask.runNode()"); //TMP
		int expectedSlotIndex = -1;
		boolean waitForOffer = false;
		ArrayList<Integer> bankingOffers = new ArrayList<Integer>();
		ArrayList<Integer> collectingOffers = new ArrayList<Integer>();
		for (Slot currGESlot : GrandExchange.getSlots())
		{
			int currSlotIndex = currGESlot.getIndex();
			if (currGESlot.inUse())
			{
				Offer currSlotOffer = currGESlot.getOffer();
				if (currSlotOffer.getState() == State.COMPLETED)
				{
					if (isExpectedOffer(currSlotOffer))
					{
						expectedSlotIndex = currSlotIndex;
					}
					if (isExpectedOperationFunction.apply(currSlotOffer))
					{
						collectingOffers.add(currSlotIndex);
					}
					else
					{
						bankingOffers.add(currSlotIndex);
					}
				}
				else if (isExpectedOffer(currSlotOffer))
				{
					expectedSlotIndex = currSlotIndex;
					waitForOffer = true;
				}
			}
		}
		Utils.debugLog("collectingOffers.size() = " + collectingOffers.size()); //TMP
		Utils.debugLog("bankingOffers.size() = " + bankingOffers.size()); //TMP
		if (collectingOffers.size() != 0 && bankingOffers.size() == 0)
		{
			Utils.debugLog("Collecting everything to the inventory!"); //TMP
			GrandExchange.collectToInventory();
		}
		else if (bankingOffers.size() != 0 && collectingOffers.size() == 0)
		{
			Utils.debugLog("Collecting everything to the bank!"); //TMP
			GrandExchange.collectToBank();
		}
		else
		{
			for (int currSlotIndex : collectingOffers)
			{
				Utils.debugLog("Collecting offer slot " + currSlotIndex + " to the inventory!"); //TMP
				GrandExchangeOfferDetails.openOfferDetails(currSlotIndex);
				GrandExchangeOfferDetails.collectOfferToInventory(collectNotes);
			}
			for (int currSlotIndex : bankingOffers)
			{
				Utils.debugLog("Collecting offer slot " + currSlotIndex + " to the bank!"); //TMP
				GrandExchangeOfferDetails.openOfferDetails(currSlotIndex);
				GrandExchangeOfferDetails.collectOfferToBank();
			}
		}
		Utils.debugLog("expectedSlotIndex = " + expectedSlotIndex); //TMP
		if (expectedSlotIndex == -1)
		{
			placeOffer();
		}
		else if (waitForOffer)
		{
			collectWhenReady(expectedSlotIndex);
		}
	}
	private boolean isExpectedOffer(Offer slotOffer)
	{
		return isExpectedOperationFunction.apply(slotOffer) && slotOffer.getItem().getName().equals(itemName);
	}
	private void placeOffer()
	{
		Utils.debugLog("GrandExchangeTask.placeOffer()"); //TMP
		int matchingSlotIndex;
		offerOperation.placeOffer(itemName, getOfferQuantity(), goldPerItem);
		matchingSlotIndex = getMatchingItemSlotIndex();
		if (matchingSlotIndex != -1)
		{
			collectWhenReady(matchingSlotIndex);
		}
	}
	private int getMatchingItemSlotIndex()
	{
		Utils.debugLog("getMatchingItemOfferSlot()"); //TMP
		Execution.delay(250);
		for (Slot currGESlot : GrandExchange.getSlots())
		{
			if ( currGESlot.inUse() && isExpectedOffer(currGESlot.getOffer()) )
			{
				return currGESlot.getIndex();
			}
		}
		return -1;
	}
	private void collectWhenReady(int itemSlotIndex)
	{
		Utils.debugLog("collectWhenReady(" + itemSlotIndex + ")"); //TMP
		Offer targetOffer;
		boolean collected = false;
		GrandExchangeOfferDetails.openOfferDetails(itemSlotIndex);
		targetOffer = GrandExchange.getSlot(itemSlotIndex).getOffer();
		while (!Environment.getBot().isStopped() && !collected)
		{
			Execution.delay(3500);
			if (targetOffer.getState() == State.COMPLETED)
			{
				Utils.debugLog("Ready to collect!"); //TMP
				GrandExchangeOfferDetails.collectOfferToInventory(collectNotes);
				collected = true;
			}
		}
	}
	protected abstract int getOfferQuantity();
}
