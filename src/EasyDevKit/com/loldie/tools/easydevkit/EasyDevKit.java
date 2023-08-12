package com.loldie.tools.easydevkit;
import javafx.scene.Node;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.GameEvents;
import com.runemate.game.api.hybrid.local.Varpbits;
import com.runemate.game.api.hybrid.local.hud.InteractableRectangle;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.util.Resources;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingBot;
import com.runemate.game.api.script.framework.listeners.AnimationListener;
import com.runemate.game.api.script.framework.listeners.MenuInteractionListener;
import com.runemate.game.api.script.framework.listeners.VarbitListener;
import com.runemate.game.api.script.framework.listeners.VarcListener;
import com.runemate.game.api.script.framework.listeners.VarpListener;
import com.runemate.game.api.script.framework.listeners.events.AnimationEvent;
import com.runemate.game.api.script.framework.listeners.events.MenuInteractionEvent;
import com.runemate.game.api.script.framework.listeners.events.VarbitEvent;
import com.runemate.game.api.script.framework.listeners.events.VarcEvent;
import com.runemate.game.api.script.framework.listeners.events.VarpEvent;
import com.loldie.bots.common.Utils;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
public class EasyDevKit extends LoopingBot implements EmbeddableUI, VarbitListener, VarpListener, VarcListener, MenuInteractionListener, AnimationListener
{
	//THE IDEA OF THIS "BOT" is to display info when you hover over NPCs, items, menus, etc...
	private ObjectProperty<Node> botInterface;
	private ToolOverlayWindow overlayWindow;
	private UIController uiController;
	public EasyDevKit()
	{
		//this.overlayWindow = new ToolOverlayWindow();
		this.uiController = new UIController();
		this.setEmbeddableUI(this);
	}
	@Override
	public void onStart(String... args)
	{
        GameEvents.Universal.UNEXPECTED_ITEM_HANDLER.disable();
        GameEvents.Universal.INTERFACE_CLOSER.disable();
        GameEvents.Universal.LOGIN_HANDLER.disable();
        GameEvents.Universal.LOBBY_HANDLER.disable();
		System.out.println("Loaded EasyDevKit v1.0");
		/*
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				overlayWindow.setVisible(true);
			}
		});
		*/
		this.getEventDispatcher().addListener(this);
		this.setLoopDelay(350, 750);
	}
	@Override
	public void onLoop()
	{
		for (InterfaceComponent currInterface : Interfaces.newQuery().visible().results().asList())
		{
			String text = currInterface.getText();
			String name = currInterface.getName();
			com.runemate.game.api.hybrid.local.hud.InteractableRectangle bounds = currInterface.getBounds();
			if (name == null)
			{
				name = "null";
			}
			if (text == null)
			{
				text = "null";
			}
			if (bounds == null)
			{
				bounds = new InteractableRectangle(0, 0, 1, 1);
			}
			Utils.debugLog( "[" + currInterface.getId() + "] " + name + ": " + text + " | (" + bounds.x + ", " + bounds.y + ") " + bounds.width + "x" + bounds.height + ") | " + Utils.iterableToString(currInterface.getActions()) );
		}
		Utils.debugLog("_________________________________________________________");
		Execution.delay(10000);
		//Container 465 = GE
		//uiController.update();
		//Platform.runLater(uiController);
		/*
		if (uiController.getShowPosition())
		{
			
		}
		if (uiController.getShowObjects())
		{
			for (GameObject currGameObject : GameObjects.getLoaded())
			{
				currGameObject.
			}
		}
		if (uiController.getShowNPCs())
		{
		}
		if (uiController.getShowItems())
		{
			
		}
		*/
		//overlayWindow.update();
	}
	//[IVarbitListener]
	@Override
	public void onValueChanged(VarbitEvent e)
	{
		uiController.addVarbitChange(e);
	}
	//[IVarpListener]
	@Override
	public void onValueChanged(VarpEvent e)
	{
		uiController.addVarpChange(e);
	}
	//[IVarcListener]
	@Override
	public void onIntChanged(VarcEvent e)
	{
		uiController.addVarcIntChange(e);
	}
	@Override
	public void onStringChanged(VarcEvent e)
	{
		uiController.addVarcStringChange(e);
	}
	//[IMenuInteractionListener]
	@Override
	public void onInteraction(MenuInteractionEvent e)
	{
		uiController.addMenuInteraction(e);
	}
	//[IAnimationListener]
	@Override
	public void onAnimationChanged(AnimationEvent e)
	{
		uiController.addAnimationChange(e);
	}
	/*
	@Override
	public void onPaint(Graphics2D arg0)
	{
		// TODO Auto-generated method stub
		arg0.drawString("Hello is me :D", 15, 45);
	}
	*/
	@Override
	public ObjectProperty<Node> botInterfaceProperty()
	{
		if (botInterface == null)
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setController(uiController);
			try
			{
				Node node = fxmlLoader.load(Resources.getAsStream("com/loldie/tools/easydevkit/main_design.fxml"));
				botInterface = new SimpleObjectProperty<Node>(node);
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return botInterface;
	}
	@Override
	public void onStop()
	{
		//overlayWindow.dispose();
	}
}
