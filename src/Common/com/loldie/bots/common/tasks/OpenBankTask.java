package com.loldie.bots.common.tasks;
import java.util.Random;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.BankEntity;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
public class OpenBankTask extends LogicTask
{
	public static final OpenBankTask DEFAULT_OPENBANK_TASK = new OpenBankTask(BankEntity.DEFAULT_BANK_OBJECT, BankEntity.DEFAULT_BANK_NPC);
	private Random generator;
	private BankEntity npcEntity;
	private BankEntity objectEntity;
	public OpenBankTask(BankEntity objectEntity, BankEntity npcEntity)
	{
		if (objectEntity != null && npcEntity != null)
		{
			this.generator = new Random();
		}
		this.objectEntity = objectEntity;
		this.npcEntity = npcEntity;
	}
	@Override
	public void runNode()
	{
		boolean chooseObject;
		BankEntity chosenEntity;
		LocatableEntity locatableEntity;
		notifyNewTask("Opening bank...");
		if (generator != null)
		{
			chooseObject = generator.nextBoolean();
		}
		else
		{
			chooseObject = objectEntity != null;
		}
		if (chooseObject)
		{
			locatableEntity = GameObjects.newQuery().names(objectEntity.getEntityName()).results().nearest();
			chosenEntity = objectEntity;
		}
		else
		{
			locatableEntity = Npcs.newQuery().names(npcEntity.getEntityName()).results().nearest();
			chosenEntity = npcEntity;
		}
		/*
		locatableEntity = Banks.getLoaded().nearest(); //random();
		*/
		if (locatableEntity != null)
		{
			Utils.turnCameraOrInteract(locatableEntity, chosenEntity.getActionName());
		}
	}
}
