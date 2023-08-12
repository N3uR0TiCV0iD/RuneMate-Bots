package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.entities.Npc;
public class InteractWithNPCTask extends LogicTask
{
	private String action;
	private String[] npcNames;
	protected String taskDescription;
	public InteractWithNPCTask(String taskDescription, String action, String... npcNames)
	{
		this.action = action;
		this.npcNames = npcNames;
		this.taskDescription = taskDescription;
	}
	@Override
	public void runNode()
	{
		Npc npc = Npcs.newQuery().names(npcNames).results().nearest();
		beforeNPCInteraction(npc);
		if (taskDescription != null)
		{
			notifyNewTask(taskDescription);
		}
		if (npc != null)
		{
			Utils.turnCameraOrInteract(npc, action);
		}
	}
	public void beforeNPCInteraction(Npc npc) { } //Virtual method
}
