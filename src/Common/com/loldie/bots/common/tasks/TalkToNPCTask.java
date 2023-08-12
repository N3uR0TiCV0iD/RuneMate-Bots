package com.loldie.bots.common.tasks;
import com.runemate.game.api.hybrid.entities.Npc;
public class TalkToNPCTask extends InteractWithNPCTask
{
	private boolean notifyTask;
	public TalkToNPCTask(String... npcNames)
	{
		this(true, npcNames);
	}
	public TalkToNPCTask(boolean notifyTask, String... npcNames)
	{
		super(null, "Talk-to", npcNames);
		this.notifyTask = notifyTask;
	}
	@Override
	public void beforeNPCInteraction(Npc npc)
	{
		if (notifyTask)
		{
			super.taskDescription = "Talking to " + npc.getName() + "...";
		}
	}
}
