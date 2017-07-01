package com.loldie.bots.common;
import java.net.URL;
import java.util.Locale;
import java.io.IOException;
import java.io.BufferedReader;
import java.security.Security;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
public class Utils
{
	public static final int MINIMAP_WIDTH = 25;
	public static final int MAX_TILE_RANGE = 150;
	static
	{
		//Source: https://stackoverflow.com/a/38434933
		Security.setProperty("jdk.tls.disabledAlgorithms", ""); //Enables SSLv3 ( Disabled by default in Java 1.8 >:C )
	}
	/**
	 * Warning: Do not use this method in the UI thread :)
	 * @return
	 */
	public static long getBotID()
	{
		return Thread.currentThread().getId();
	}
	public static void turnCameraOrInteract(LocatableEntity locatableEntity, String action)
	{
		if (locatableEntity.isVisible())
		{
			locatableEntity.interact(action);
		}
		else
		{
			Camera.turnTo(locatableEntity);
		}
	}
	public static String getTimeFromMs(long milliseconds)
	{
		if (milliseconds < 1000)
		{
			return "less than a second";
			
		}
		else if (milliseconds < 60000)
		{
			return (milliseconds / 1000) + " seconds";
		}
		else if (milliseconds < 3600000)
		{
			return (milliseconds / 60000) + " minutes";
		}
		else
		{
			return (milliseconds / 3600000) + " hours";
		}
	}
	public static int getGrandExchangePrice(int itemID)
	{
		String[] splitLine;
		HttpURLConnection httpConnection;
		try
		{
			URL url = new URL("https://www.tip.it/runescape/json/ge_single_item?item=" + itemID);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.setRequestProperty("Connection", "close"); //Make the Powerbot Sandbox happy :D
			try (BufferedReader responseReader = new BufferedReader( new InputStreamReader(httpConnection.getInputStream()) ))
			{
				responseReader.readLine(); //Ignore the first line (It is always empty :P)
				splitLine = responseReader.readLine().split("\"", 13);
				if (splitLine.length != 3)
				{
					return Integer.parseInt(splitLine[11]);
				}
				else
				{
					//Invalid itemID!
				}
			}
		}
		catch (MalformedURLException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return -1;
	}
	public static String getRoundedGP(int gp)
	{
		if (gp >= 950000)
		{
			return String.format(Locale.US, "%.2f", gp / (float)1000000) + "M";
		}
		else if (gp >= 150000)
		{
			return String.format(Locale.US, "%.2f", gp / (float)1000) + "k";
		}
		else
		{
			return Integer.toString(gp);
		}
	}
}
