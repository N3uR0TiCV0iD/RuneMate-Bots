package com.loldie.bots.common;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.script.Execution;
public class GrandExchangeOfferDetails
{
	private static final int MAX_OFFERS = 8;
	private static final int OFFER_PANEL_INDEX = 2;
	private static final int OFFERS_START_INDEX = 7;
	private static final int GE_UI_CONTAINER_INDEX = 465;
	private static final int FIRST_COLLECT_SPRITE_INDEX = 2;
	private static final int SECOND_COLLECT_SPRITE_INDEX = 3;
	private static final int OFFER_STATUS_LABEL_ID = 30474242;
	private static final int OFFER_COLLECT_CONTAINER_INDEX = 23;
	public static boolean collectOfferToBank()
	{
		InterfaceComponent offerCollectContainer = Utils.getInterfaceComponent(GE_UI_CONTAINER_INDEX, OFFER_COLLECT_CONTAINER_INDEX);
		if (offerCollectContainer != null)
		{
			tryCollectInteract(offerCollectContainer.getChild(SECOND_COLLECT_SPRITE_INDEX), "Bank");
			return tryCollectInteract(offerCollectContainer.getChild(FIRST_COLLECT_SPRITE_INDEX), "Bank");
		}
		return false;
	}
	public static boolean collectOfferToInventory(boolean noted)
	{
		InterfaceComponent offerCollectContainer = Utils.getInterfaceComponent(GE_UI_CONTAINER_INDEX, OFFER_COLLECT_CONTAINER_INDEX);
		if (offerCollectContainer != null)
		{
			InterfaceComponent secondCollectSprite = offerCollectContainer.getChild(SECOND_COLLECT_SPRITE_INDEX);
			InterfaceComponent firstCollectSprite = offerCollectContainer.getChild(FIRST_COLLECT_SPRITE_INDEX);
			if (noted)
			{
				if (!tryCollectInteract(secondCollectSprite, "Collect-notes"))
				{
					tryCollectInteract(secondCollectSprite, "Collect");
				}
				return tryCollectInteract(firstCollectSprite, "Collect-notes") || tryCollectInteract(firstCollectSprite, "Collect");
			}
			else if (!tryCollectInteract(secondCollectSprite, "Collect-items"))
			{
				tryCollectInteract(secondCollectSprite, "Collect");
			}
			return tryCollectInteract(firstCollectSprite, "Collect-items") || tryCollectInteract(firstCollectSprite, "Collect");
		}
		return false;
	}
	private static boolean tryCollectInteract(InterfaceComponent collectSprite, String action)
	{
		return collectSprite != null && collectSprite.isValid() && collectSprite.interact(action);
	}
	public static boolean openOfferDetails(int offerIndex)
	{
		if (offerIndex < MAX_OFFERS)
		{
			InterfaceComponent offerPanel = Utils.getInterfaceComponent(GE_UI_CONTAINER_INDEX, OFFERS_START_INDEX + offerIndex, OFFER_PANEL_INDEX);
			if (offerPanel != null)
			{
				boolean result = offerPanel.click();
				Execution.delay(250);
				return result;
			}
			return false;
		}
		throw new IllegalArgumentException("offerIndex cannot be higher than " + (MAX_OFFERS - 1));
	}
	public static boolean isOpen()
	{
		return Utils.isInterfaceVisible( OFFER_STATUS_LABEL_ID, currInterface -> "Grand Exchange: Offer status".equals(currInterface.getText()) );
	}
}
