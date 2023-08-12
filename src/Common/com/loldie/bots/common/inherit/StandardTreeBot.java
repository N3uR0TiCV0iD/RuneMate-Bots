package com.loldie.bots.common.inherit;
import java.util.HashMap;
import javafx.scene.Node;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import com.runemate.game.api.hybrid.util.Resources;
import com.loldie.bots.common.MembershipPreference;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
/**
 * A BaseTreeBot that contains a UI
 */
public abstract class StandardTreeBot extends BaseTreeBot implements EmbeddableUI
{
	private static HashMap<Integer, BasicUIController> treeBotUIControllers = new HashMap<Integer, BasicUIController>();
	public static void setUIBotTaskDescription(int botID, String taskDescription)
	{
		BasicUIController uiController = treeBotUIControllers.get(botID);
		if (uiController != null)
		{
			uiController.setTaskDescription(taskDescription);
		}
	}
	protected BasicUIController uiController;
	private ObjectProperty<Node> botInterface;
	public StandardTreeBot(BasicUIController uiController)
	{
		commonInit(uiController);
	}
	public StandardTreeBot(BasicUIController uiController, MembershipPreference membershipPreference, boolean playerAware, boolean canShareWorlds)
	{
		super(membershipPreference, playerAware, canShareWorlds);
		commonInit(uiController);
	}
	private void commonInit(BasicUIController uiController)
	{
		treeBotUIControllers.put(super.getBotID(), uiController);
		this.uiController = uiController;
		this.setEmbeddableUI(this);
	}
	@Override
	public void onLoop()
	{
		super.onLoop();
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
				Node node = fxmlLoader.load(Resources.getAsStream("com/loldie/bots/common/main_design.fxml"));
				botInterface = new SimpleObjectProperty<Node>(node);
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return botInterface;
	}
}
