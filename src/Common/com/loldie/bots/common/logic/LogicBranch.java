package com.loldie.bots.common.logic;
public abstract class LogicBranch implements ILogicNode
{
	private ILogicNode truePath;
	private ILogicNode falsePath;
	public LogicBranch(ILogicNode truePath, ILogicNode falsePath)
	{
		this.truePath = truePath;
		this.falsePath = falsePath;
	}
	public void runNode()
	{
		ILogicNode path = condition() ? truePath : falsePath;
		path.runNode();
	}
	protected abstract boolean condition();
}
