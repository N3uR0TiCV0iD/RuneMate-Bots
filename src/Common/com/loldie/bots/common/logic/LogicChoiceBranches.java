package com.loldie.bots.common.logic;
import java.util.ArrayList;
import com.runemate.game.api.hybrid.util.collections.Pair;
public abstract class LogicChoiceBranches<T> implements ILogicNode
{
	private ILogicNode defaultPath;
	private ArrayList<Pair<T, ILogicNode>> paths;
	public LogicChoiceBranches(ILogicNode defaultPath)
	{
		this.defaultPath = defaultPath;
		this.paths = new ArrayList<Pair<T, ILogicNode>>();
	}
	public void addPath(T value, ILogicNode path)
	{
		paths.add(new Pair<T, ILogicNode>(value, path));
	}
	public void addPaths(Pair<T, ILogicNode>[] pathChoices)
	{
		for (Pair<T, ILogicNode> currPathChoice : pathChoices)
		{
			paths.add(currPathChoice);
		}
	}
	@Override
	public void runNode()
	{
		T value = getCurrentValue();
		ILogicNode path = defaultPath;
		for (Pair<T, ILogicNode> currPathChoice : paths)
		{
			if ( evaluatePath(value, currPathChoice.getLeft()) )
			{
				path = currPathChoice.getRight();
				break;
			}
		}
		path.runNode();
	}
	protected boolean evaluatePath(T left, T right)
	{
		return left.equals(right);
	}
	protected abstract T getCurrentValue();
}
