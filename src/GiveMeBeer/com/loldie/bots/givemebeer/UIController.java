package com.loldie.bots.givemebeer;
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
	private static final int BEER_ID = 1913;
	private static final ItemPricePoller PRICE_POLLER = new ItemPricePoller(BEER_ID);
	private long bankTimeDuration;
	private long lastBankTime;
	private int bankedBeers;
	private int totalBeers;
	//TODO: Add current task label :D + Players that are being "Watched"
	@FXML
	private Label currGoldLabel;
	@FXML
	private Label currProfitLabel;
	@FXML
	private Label beerPriceLabel;
	public UIController()
	{
		this.bankTimeDuration = -1;
		this.lastBankTime = -1;
		this.bankedBeers = -1;
		this.totalBeers = -1;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		run();
	}
	public void update()
	{
		if (bankedBeers != -1)
		{
			totalBeers = bankedBeers + Inventory.getQuantity(BEER_ID);
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
		bankedBeers = Bank.getQuantity(BEER_ID);
		lastBankTime = System.currentTimeMillis();
	}
	@Override
	public void run()
	{
		int beerPrice = PRICE_POLLER.getItemPrice();
		if (totalBeers != -1)
		{
			currGoldLabel.setText("Total Beers: " + totalBeers + " (" + Utils.getRoundedGP(totalBeers * beerPrice) + " GP)");
		}
		else
		{
			currGoldLabel.setText("Total Beers: ?");
		}
		if (bankTimeDuration != -1)
		{
			double beersPerMin = 28 / (bankTimeDuration / (double)60000);
			currProfitLabel.setText("Profit: " + String.format(Locale.US, "%.3f", beersPerMin) + " Beers/min (" + Utils.getRoundedGP((int)(beersPerMin * 60 * beerPrice)) + " GP/hour)");
		}
		else
		{
			currProfitLabel.setText("Profit: ? Beers/min");
		}
		beerPriceLabel.setText("Beer Price: " + beerPrice + " (Updated " + PRICE_POLLER.getLastUpdateString() + " ago)");
	}
}
