package com.loldie.bots.common.tasks;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.entities.GameObject;
public class InteractWithObjectTask extends LogicTask
{
	private String action;
	private String[] objectNames;
	protected String taskDescription;
	public InteractWithObjectTask(String taskDescription, String action, String... objectNames)
	{
		this.action = action;
		this.objectNames = objectNames;
		this.taskDescription = taskDescription;
	}
	@Override
	public void runNode()
	{
		GameObject object = GameObjects.newQuery().names(objectNames).results().nearest();
		notifyNewTask(taskDescription);
		if (object != null)
		{
			beforeObjectInteraction(object);
			Utils.turnCameraOrInteract(object, action);
		}
	}
	public void beforeObjectInteraction(GameObject gameObject) { } //Virtual method
}
