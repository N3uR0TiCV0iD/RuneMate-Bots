package com.loldie.bots.ilikesheep.tasks;
import com.loldie.bots.common.logic.LogicTask;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.entities.GameObject;
public class JumpOverStileTask extends LogicTask
{
	@Override
	public void runNode()
	{
		GameObject stile;
		Player localPlayer = Players.getLocal();
		notifyNewTask("Jumping over stile...");		
		if (localPlayer.getAnimationId() == -1)
		{
			stile = GameObjects.newQuery().names("Stile").results().first();
			if (stile != null)
			{
				if (stile.isVisible())
				{
					stile.interact("Climb-over");
					Execution.delay(1250);
				}
				else
				{
					Camera.turnTo(stile);
				}
			}
		}
	}
}
