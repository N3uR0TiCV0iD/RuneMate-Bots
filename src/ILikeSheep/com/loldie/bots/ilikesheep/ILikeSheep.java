package com.loldie.bots.ilikesheep;
import javafx.scene.Node;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;

import com.loldie.bots.common.AllWorldsOccupiedException;
import com.loldie.bots.common.banks.Lumbridge;
import com.loldie.bots.common.logic.LogicTree;
import javafx.beans.property.SimpleObjectProperty;
import com.runemate.game.api.hybrid.util.Resources;
import com.loldie.bots.ilikesheep.tasks.GetWoolTask;
import com.loldie.bots.common.tasks.BankEverythingTask;
import com.runemate.game.api.script.framework.LoopingBot;
import com.loldie.bots.ilikesheep.tasks.JumpOverStileTask;
import com.loldie.bots.common.branches.InventoryFullBranch;
import com.loldie.bots.ilikesheep.branches.IsNearPenBranch;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.loldie.bots.ilikesheep.branches.IsInSheepPenBranch;
@SuppressWarnings("restriction")
public class ILikeSheep extends LoopingBot implements EmbeddableUI
{
	private ObjectProperty<Node> botInterface;
	private UIController uiController;
	private LogicTree logicTree;
	public ILikeSheep()
	{
		setEmbeddableUI(this);
		this.uiController = new UIController();
		this.logicTree = new LogicTree(new InventoryFullBranch(new IsInSheepPenBranch( new JumpOverStileTask(), Lumbridge.getNorthBankAccessBranch(new BankEverythingTask(this.uiController)) ),
															   new IsInSheepPenBranch(new GetWoolTask(), new IsNearPenBranch())
															  ), false);
	}
	@Override
	public void onStart(String... args)
	{
		setLoopDelay(500, 750);
		System.out.println("Loaded ILikeSheep v1.0");
	}
	@Override
	public void onLoop()
	{
		try
		{
			logicTree.runTree();
		}
		catch (AllWorldsOccupiedException ex)
		{
			ex.printStackTrace();
			this.stop();
		}
		uiController.update();
		Platform.runLater(uiController);
	}
	@Override
	public ObjectProperty<Node> botInterfaceProperty()
	{
		if (botInterface == null)
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setController(uiController);
			try
			{
				Node node = fxmlLoader.load(this.getPlatform().invokeAndWait( () -> Resources.getAsStream("com/loldie/bots/ilikesheep/main_design.fxml") ));
				botInterface = new SimpleObjectProperty<Node>(node);
			}
			catch (IOException | ExecutionException | InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
		return botInterface;
	}
}
