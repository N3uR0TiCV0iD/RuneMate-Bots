package com.loldie.bots.common.logic;
import com.loldie.bots.common.tasks.GoToTask;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.Players;
public class LogicAreaBranch extends LogicBranch
{
	private Area area;
	public LogicAreaBranch(Area area, ILogicNode truePath, String areaName)
	{
		super( truePath, new GoToTask(areaName, area.getCenter()) );
		this.area = area;
	}
	public LogicAreaBranch(Area area, ILogicNode truePath, ILogicNode falsePath)
	{
		super(truePath, falsePath);
		this.area = area;
	}
	@Override
	protected boolean condition()
	{
		return area.contains(Players.getLocal());
	}
}
