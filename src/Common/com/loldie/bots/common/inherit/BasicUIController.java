package com.loldie.bots.common.inherit;
import java.net.URL;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
/**
 * The base class for all UIControllers (Every UIController should contain a task label)
 */
public class BasicUIController implements Initializable, Runnable
{
	private boolean updateTextDescription;
	private String newTaskDescription;
	@FXML
	private Label currTaskLabel;
	public void setTaskDescription(String taskDescription)
	{
		newTaskDescription = taskDescription;
		updateTextDescription = true;
	}
	@Override
	public final void initialize(URL location, ResourceBundle resources)
	{
		addUI();
		run();
	}
	/**
	 * This method runs in the JavaFX thread!
	 */
	@Override
	public void run()
	{
		if (updateTextDescription)
		{
			currTaskLabel.setText("Current Task: " + newTaskDescription);
			updateTextDescription = false;
		}
	}
	/**
	 * This method runs in the Bot's thread!
	 */
	public void update() { } //Virtual method
	public void addUI() { } //Virtual method
}
