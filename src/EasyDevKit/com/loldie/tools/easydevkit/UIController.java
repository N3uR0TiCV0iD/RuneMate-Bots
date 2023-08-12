package com.loldie.tools.easydevkit;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import com.loldie.bots.common.Utils;
import javafx.scene.control.TreeItem;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeTableView;
import com.runemate.game.api.script.framework.listeners.events.AnimationEvent;
import com.runemate.game.api.script.framework.listeners.events.MenuInteractionEvent;
import com.runemate.game.api.script.framework.listeners.events.VarbitEvent;
import com.runemate.game.api.script.framework.listeners.events.VarcEvent;
import com.runemate.game.api.script.framework.listeners.events.VarpEvent;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn;
import javafx.beans.property.ReadOnlyStringWrapper;
public class UIController implements Initializable, Runnable
{
	private ObservableList<TreeItem<NamedValue>> varpChangesItems;
	private boolean showPosition;
	private boolean showObjects;
	private boolean showItems;
	private boolean showNPCs;
	@FXML
	private TreeTableView<NamedValue> varpChangesTable;
	@FXML
	private TreeTableColumn<NamedValue, String> varpChangesNameColumn;
	@FXML
	private TreeTableColumn<NamedValue, String> varpChangesValueColumn;
	@FXML
	private Button clearVarpChangesButton;
	public UIController()
	{
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		TreeItem<NamedValue> rootItem = new TreeItem<>();
		varpChangesItems = rootItem.getChildren();
		varpChangesTable.setRoot(rootItem);
		varpChangesNameColumn.setCellValueFactory((CellDataFeatures<NamedValue, String> p) -> {
			return new ReadOnlyStringWrapper(p.getValue().getValue().getName());
		});
		varpChangesValueColumn.setCellValueFactory((CellDataFeatures<NamedValue, String> p) -> {
			return new ReadOnlyStringWrapper(p.getValue().getValue().getValue());
		});
		run();
	}
	public boolean getShowPosition()
	{
		return showPosition;
	}
	public boolean getShowObjects()
	{
		return showObjects;
	}
	public boolean getShowItems()
	{
		return showItems;
	}
	public boolean getShowNPCs()
	{
		return showNPCs;
	}
	public void addVarbitChange(VarbitEvent e)
	{
		
	}
	public void addVarpChange(VarpEvent e)
	{
		//Some more inspiration: https://www.runemate.com/community/threads/what-varps-and-varbits-are-and-how-to-work-with-them.11618/
		TreeItem<NamedValue> varpChangeItem = new TreeItem<NamedValue>( new NamedValue("Varps[" +  e.getVarp().getIndex() + "]", LocalTime.now().format(DateTimeFormatter.ISO_TIME)) );
		ObservableList<TreeItem<NamedValue>> childItems = varpChangeItem.getChildren();
		String oldBinaryString = Utils.toBinaryLeadingZeros(e.getOldValue());
		String newBinaryString = Utils.toBinaryLeadingZeros(e.getNewValue());
		childItems.add(new TreeItem<NamedValue>( new NamedValue("numberChange", e.getOldValue() + " => " + e.getNewValue()) ));
		childItems.add(new TreeItem<NamedValue>( new NamedValue("oldBinary", oldBinaryString) ));
		childItems.add(new TreeItem<NamedValue>( new NamedValue("newBinary", newBinaryString) ));
		varpChangesItems.add(varpChangeItem);
	}
	public void addVarcIntChange(VarcEvent e)
	{
		Utils.debugLog("VarcIntChange()");
		String oldBinaryString = Utils.toBinaryLeadingZeros((int)e.getPreviousValue());
		String newBinaryString = Utils.toBinaryLeadingZeros((int)e.getValue());
		//Utils.debugLog("Varcs[" + e.getIndex() + "]");
		Utils.debugLog(e.getPreviousValue() + " => " + e.getValue());
		Utils.debugLog("oldBinary: " + oldBinaryString);
		Utils.debugLog("newBinary: " + newBinaryString);
		//childItems.add(new TreeItem<NamedValue>( new NamedValue("oldBinary", oldBinaryString) ));
		//childItems.add(new TreeItem<NamedValue>( new NamedValue("newBinary", newBinaryString) ));
		Utils.debugLog("_________________________________________");
	}
	public void addVarcStringChange(VarcEvent e)
	{
		Utils.debugLog("VarcStringChange()");
		//Utils.debugLog("Varcs[" + e.getIndex() + "]");
		Utils.debugLog(e.getPreviousValue() + " => " + e.getValue());
		Utils.debugLog("_________________________________________");
	}
	public void addMenuInteraction(MenuInteractionEvent e)
	{
		Utils.debugLog("addMenuInteraction()");
		Utils.debugLog("_________________________________________");
	}
	public void addAnimationChange(AnimationEvent e)
	{
		
	}
	@FXML
    private void clearVarpChangesButton_OnAction(ActionEvent e)
	{
		varpChangesItems.clear();
    }
	public void update()
	{
	}
	@Override
	public void run()
	{
	}
}
