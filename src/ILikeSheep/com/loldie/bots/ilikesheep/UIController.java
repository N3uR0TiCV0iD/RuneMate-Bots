package com.loldie.bots.ilikesheep;
import java.net.URL;
import javafx.fxml.FXML;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.loldie.bots.common.Utils;
import com.loldie.bots.common.ItemPricePoller;
import com.loldie.bots.common.banks.IBankingDoneHandler;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
@SuppressWarnings("restriction")
public class UIController implements Initializable, IBankingDoneHandler, Runnable
{
	private static final int WOOL_ID = 1737;
	private static final ItemPricePoller PRICE_POLLER = new ItemPricePoller(WOOL_ID);
	private long bankTimeDuration;
	private long lastBankTime;
	private int bankedWool;
	private int totalWool;
	//TODO: Add current task label :D + Players that are being "Watched"
	@FXML
	private Label currGoldLabel;
	@FXML
	private Label currProfitLabel;
	@FXML
	private Label woolPriceLabel;
	public UIController()
	{
		this.bankTimeDuration = -1;
		this.lastBankTime = -1;
		this.bankedWool = -1;
		this.totalWool = -1;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		run();
	}
	public void update()
	{
		if (bankedWool != -1)
		{
			totalWool = bankedWool + Inventory.getQuantity(WOOL_ID);
		}
		PRICE_POLLER.update();
	}
	@Override
	public void onBankingDone()
	{
		if (lastBankTime != -1)
		{
			bankTimeDuration = System.currentTimeMillis() - lastBankTime;
		}
		bankedWool = Bank.getQuantity(WOOL_ID);
		lastBankTime = System.currentTimeMillis();
	}
	@Override
	public void run()
	{
		int woolPrice = PRICE_POLLER.getItemPrice();
		if (totalWool != -1)
		{
			currGoldLabel.setText("Total Wool: " + totalWool + " (" + Utils.getRoundedGP(totalWool * woolPrice) + " GP)");
		}
		else
		{
			currGoldLabel.setText("Total Wool: ?");
		}
		if (bankTimeDuration != -1)
		{
			double woolPerMin = 28 / (bankTimeDuration / (double)60000);
			currProfitLabel.setText("Profit: " + String.format(Locale.US, "%.3f", woolPerMin) + " Wool/min (" + Utils.getRoundedGP((int)(woolPerMin * 60 * woolPrice)) + " GP/hour)");
		}
		else
		{
			currProfitLabel.setText("Profit: ? Wool/min");
		}
		woolPriceLabel.setText("Wool Price: " + woolPrice + " (Updated " + PRICE_POLLER.getLastUpdateString() + " ago)");
	}
}
