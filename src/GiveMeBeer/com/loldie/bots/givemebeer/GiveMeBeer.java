package com.loldie.bots.givemebeer;
import javafx.scene.Node;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;

import com.loldie.bots.common.AllWorldsOccupiedException;
import com.loldie.bots.common.banks.Falador;
import javafx.beans.property.ObjectProperty;
import com.loldie.bots.common.logic.LogicTree;
import java.util.concurrent.ExecutionException;
import javafx.beans.property.SimpleObjectProperty;
import com.runemate.game.api.hybrid.util.Resources;
import com.loldie.bots.common.branches.HasGoldBranch;
import com.loldie.bots.common.tasks.BankEverythingTask;
import com.runemate.game.api.script.framework.LoopingBot;
import com.loldie.bots.common.tasks.WithdrawBankMoneyTask;
import com.loldie.bots.common.branches.InventoryFullBranch;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.loldie.bots.givemebeer.branches.InFaladorBarBranch;
@SuppressWarnings("restriction")
public class GiveMeBeer extends LoopingBot implements EmbeddableUI
{
	private static final int FAILSAFE_AMOUNT = 3 * (28 + 1);
	private static final int WITHDRAW_AMOUNT = 3 * (28 * 2);
	private ObjectProperty<Node> botInterface;
	private UIController uiController;
	private LogicTree logicTree;
	public GiveMeBeer()
	{
		WithdrawBankMoneyTask withdrawBankMoneyTask = new WithdrawBankMoneyTask(WITHDRAW_AMOUNT);
		setEmbeddableUI(this);
		this.uiController = new UIController();
		this.logicTree = new LogicTree(new HasGoldBranch(FAILSAFE_AMOUNT, new InventoryFullBranch(Falador.getWestBankAccessBranch(new HasGoldBranch(WITHDRAW_AMOUNT, new BankEverythingTask(this.uiController), withdrawBankMoneyTask) ), new InFaladorBarBranch()),
													 	 Falador.getWestBankAccessBranch(withdrawBankMoneyTask)
													    ), false);
	}
	@Override
	public void onStart(String... args)
	{
		setLoopDelay(450, 650);
		System.out.println("Loaded GiveMeBeer v1.0");
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
				Node node = fxmlLoader.load(this.getPlatform().invokeAndWait( () -> Resources.getAsStream("com/loldie/bots/givemebeer/main_design.fxml") ));
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
