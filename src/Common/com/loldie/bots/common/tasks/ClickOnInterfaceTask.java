package com.loldie.bots.common.tasks;
import java.util.function.Predicate;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
public class ClickOnInterfaceTask extends LogicTask
{
	private int interfaceID;
	private String taskDescription;
	private Predicate<InterfaceComponent> predicate;
	public ClickOnInterfaceTask(String taskDescription, int interfaceID)
	{
		this.interfaceID = interfaceID;
		this.taskDescription = taskDescription;
	}
	public ClickOnInterfaceTask(String taskDescription, int interfaceID, Predicate<InterfaceComponent> predicate)
	{
		this(taskDescription, interfaceID);
		this.predicate = predicate;
	}
	@Override
	public void runNode()
	{
		InterfaceComponent interfaceComponent = Utils.getInterfaceComponent(interfaceID, predicate);
		notifyNewTask(taskDescription);
		if (interfaceComponent != null)
		{
			interfaceComponent.click();
		}
	}
}
