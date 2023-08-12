package com.loldie.bots.common.tasks;
import com.loldie.bots.common.TreeType;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.region.GameObjects;
public class ChopDownTreeTask extends LogicTask
{
	private TreeType treeType;
	private String taskDescription;
	public ChopDownTreeTask(TreeType treeType)
	{
		this.treeType = treeType;
		this.taskDescription = "Chopping down " + treeType.getName() + "...";
	}
	@Override
	public void runNode()
	{
		GameObject tree = GameObjects.newQuery().names(treeType.getName()).results().nearest();
		notifyNewTask(taskDescription);
		if (tree != null)
		{
			Utils.turnCameraOrClick(tree);
		}
	}
}
