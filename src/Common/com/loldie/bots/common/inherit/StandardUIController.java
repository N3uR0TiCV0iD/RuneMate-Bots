package com.loldie.bots.common.inherit;
import java.util.List;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import com.loldie.bots.common.Utils;
import javafx.scene.layout.AnchorPane;
import com.loldie.bots.common.items.ItemName;
import com.loldie.bots.common.ITimedTaskDoneHandler;
import com.loldie.bots.common.items.ItemPricePoller;
import com.loldie.bots.common.items.UIControllerItem;
public class StandardUIController extends BasicUIController implements ITimedTaskDoneHandler
{
	private static final int DEFAULT_X = 15;
	private static final int ITEMS_Y_DIFF = 25;
	private static final int ITEMS_Y_START = 10 + ITEMS_Y_DIFF;
	private UIControllerItem[] controllerItems;
	private Label[] currProfitLabels;
	private Label[] itemPriceLabels;
	private Label[] currGoldLabels;
	private long taskTimeDuration;
	private long lastTaskTime;
	@FXML
	private AnchorPane botInfoTab;
	public StandardUIController(ItemName itemName, ItemPricePoller pricePoller, int itemsPerTask)
	{
		this(new UIControllerItem[] { new UIControllerItem(itemName, pricePoller, itemsPerTask) });
	}
	public StandardUIController(UIControllerItem[] controllerItems)
	{
		this.currProfitLabels = new Label[controllerItems.length];
		this.itemPriceLabels = new Label[controllerItems.length];
		this.currGoldLabels = new Label[controllerItems.length];
		this.controllerItems = controllerItems;
		this.taskTimeDuration = -1;
		this.lastTaskTime = -1;
	}
	@Override
	public void addUI()
	{
		int currYPos = ITEMS_Y_START;
		List<Node> tabChildren = botInfoTab.getChildren();
		int nextFieldOffset = ITEMS_Y_DIFF * controllerItems.length;
		for (int currItemIndex = 0; currItemIndex < controllerItems.length; currItemIndex++)
		{
			Label newLabel = new Label();
			currYPos = ITEMS_Y_START + (ITEMS_Y_DIFF * currItemIndex);
			newLabel.setLayoutX(DEFAULT_X);
			newLabel.setLayoutY(currYPos);
			currGoldLabels[currItemIndex] = newLabel;
			tabChildren.add(newLabel);
			
			newLabel = new Label();
			currYPos += nextFieldOffset;
			newLabel.setLayoutX(DEFAULT_X);
			newLabel.setLayoutY(currYPos);
			currProfitLabels[currItemIndex] = newLabel;
			tabChildren.add(newLabel);
			
			newLabel = new Label();
			currYPos += nextFieldOffset;
			newLabel.setLayoutX(DEFAULT_X);
			newLabel.setLayoutY(currYPos);
			itemPriceLabels[currItemIndex] = newLabel;
			tabChildren.add(newLabel);
		}
	}
	@Override
	public void update()
	{
		for (int currItemIndex = 0; currItemIndex < controllerItems.length; currItemIndex++)
		{
			controllerItems[currItemIndex].update();
		}
	}
	@Override
	public void onTimedTaskDone()
	{
		if (lastTaskTime != -1)
		{
			taskTimeDuration = System.currentTimeMillis() - lastTaskTime;
		}
		for (int currItemIndex = 0; currItemIndex < controllerItems.length; currItemIndex++)
		{
			controllerItems[currItemIndex].onTimedTaskDone();
		}
		lastTaskTime = System.currentTimeMillis();
	}
	@Override
	public void run()
	{
		for (int currItemIndex = 0; currItemIndex < controllerItems.length; currItemIndex++)
		{
			UIControllerItem currControllerItem = controllerItems[currItemIndex];
			ItemName itemName = currControllerItem.getItemName();
			int totalItems = currControllerItem.getTotalItems();
			int itemPrice = currControllerItem.getItemPrice();
			if (totalItems != -1)
			{
				currGoldLabels[currItemIndex].setText("Total " + itemName.getPluralName() + ": " + totalItems + " (" + Utils.getRoundedGP(totalItems * itemPrice) + " GP)");
			}
			else
			{
				currGoldLabels[currItemIndex].setText(currControllerItem.getUnknownAmountString());
			}
			if (taskTimeDuration != -1)
			{
				double itemsPerMin = currControllerItem.getItemsPerTask() / (taskTimeDuration / (double)60000);
				currProfitLabels[currItemIndex].setText("Profit: " + String.format(Locale.US, "%.3f", itemsPerMin) + " " + itemName.getPluralName() + "/min (" + Utils.getRoundedGP((int)(itemsPerMin * 60 * itemPrice)) + " GP/hour)");
			}
			else
			{
				currProfitLabels[currItemIndex].setText(currControllerItem.getUnknownProfitString());
			}
			itemPriceLabels[currItemIndex].setText(itemName.getSingularName() + " Price: " + itemPrice + " (Updated " + currControllerItem.getLastUpdateString() + " ago)");
		}
		super.run();
	}
}
