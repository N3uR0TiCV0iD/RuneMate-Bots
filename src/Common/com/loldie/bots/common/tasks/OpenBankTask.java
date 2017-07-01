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
		this.npcEntity = npcEntity;
		this.generator = new Random();
		this.objectEntity = objectEntity;
	}
	@Override
	public void runNode()
	{
		boolean chooseObject;
		boolean hasObjectEntity;
		BankEntity chosenEntity;
		LocatableEntity locatableEntity;
		notifyNewTask("Opening bank...");
		hasObjectEntity = objectEntity != null;
		if (hasObjectEntity && npcEntity != null)
		{
			chooseObject = generator.nextBoolean();
		}
		else
		{
			chooseObject = hasObjectEntity;
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
		if (locatableEntity != null)
		{
			Utils.turnCameraOrInteract(locatableEntity, chosenEntity.getActionName());
		}
	}
}
