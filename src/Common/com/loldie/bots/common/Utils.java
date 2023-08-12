package com.loldie.bots.common;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Predicate;
import java.io.IOException;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import com.loldie.bots.common.inherit.BaseTreeBot;
import com.loldie.bots.common.items.ItemAmount;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.Screen;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.loldie.bots.common.tasks.WithdrawBankMoneyTask;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.rs3.local.hud.interfaces.MoneyPouch;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.hybrid.local.hud.InteractableRectangle;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent.Type;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceContainers;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.queries.InterfaceComponentQueryBuilder;
public class Utils
{
	public static final int MINIMAP_WIDTH = 25;
	public static final int MAX_TILE_RANGE = 150;
	public static final Random GENERATOR = new Random();
	private static final int MAX_REGIONPATH_DISTANCE = 50;
	//NOTE: THESE MIGHT BE UNIQUE TO OSRS!!!
	private static final HashSet<Integer> IGNORED_INTERFACES = new HashSet<Integer>(
		Arrays.asList(7995392, 7995393, 7995394,
					  10616890, 10616910, 10682368,
					  10747904, 10747905, 10747906, 10747907, 10747908, 10747910, 10747911, 10747912, 10747913, 10747914, 10747915, 10747916, 10747917, 10747918, 10747919,
					  40239104, 40239106, 40239107, 40239108, 40239109, 40239118)
	);
	public static int getBotID()
	{
		return BaseTreeBot.getBotID(Environment.getBot());
	}
	public static boolean isEmptyString(String string)
	{
		return string == null || string.length() == 0;
	}
	public static String safeToString(Object object)
	{
		return object != null ? object.toString() : "null";
	}
	public static int randomInt(int min, int max)
	{
		return randomInt(min, max, GENERATOR);
	}
	public static int randomInt(int min, int max, Random generator)
	{
		if (min == max)
		{
			return min;
		}
		if (min > max)
		{
			throw new IllegalArgumentException("'min' may not be greater than 'max'");
		}
		return min + generator.nextInt(max - min);
	}
	public static <T> T randomElement(T[] array)
	{
		return randomElement(array, GENERATOR);
	}
	public static <T> T randomElement(T[] array, Random generator)
	{
		return array[randomIndex(array, generator)];
	}
	public static <T> T randomElement(List<T> list)
	{
		return randomElement(list, GENERATOR);
	}
	public static <T> T randomElement(List<T> list, Random generator)
	{
		return list.get(randomIndex(list, generator));
	}
	public static int randomIndex(Object[] array)
	{
		return randomIndex(array, GENERATOR);
	}
	public static int randomIndex(Object[] array, Random generator)
	{
		return generator.nextInt(array.length);		
	}
	public static int randomIndex(Collection<?> collection)
	{
		return randomIndex(collection, GENERATOR);
	}
	public static int randomIndex(Collection<?> collection, Random generator)
	{
		return generator.nextInt(collection.size());
	}
	public static boolean isPlayerIdle(Player player)
	{
		return player.getAnimationId() == -1 && !player.isMoving();
	}
	public static void moveTowards(Coordinate localPlayerPos, Coordinate locatablePos)
	{
		moveTowards(localPlayerPos, locatablePos, locatablePos);
	}
	public static void moveTowards(Coordinate localPlayerPos, Coordinate locatablePos, Locatable locatable)
	{
		if (Math.abs(localPlayerPos.getX() - locatablePos.getX()) <= MAX_REGIONPATH_DISTANCE &&
			Math.abs(localPlayerPos.getY() - locatablePos.getY()) <= MAX_REGIONPATH_DISTANCE)
		{
			try
			{
				RegionPath.buildTo(locatable).step();
			}
			catch (Exception ex)
			{
				BresenhamPath.buildTo(locatable).step();
			}
		}
		else
		{
			BresenhamPath.buildTo(locatable).step();
		}
	}
	public static int getPlayerGold()
	{
		if (Environment.isRS3())
		{
			return MoneyPouch.getContents();
		}
		else
		{
			return Inventory.getQuantity(WithdrawBankMoneyTask.GOLDCOINS_ID);
		}
	}
	public static void turnCameraOrInteract(LocatableEntity locatableEntity, String action)
	{
		if (makeEntityVisible(locatableEntity))
		{
			locatableEntity.interact(action);
		}
	}
	public static void turnCameraOrClick(LocatableEntity locatableEntity)
	{
		if (makeEntityVisible(locatableEntity))
		{
			locatableEntity.click();
		}
	}
	public static boolean makeEntityVisible(LocatableEntity locatableEntity)
	{
		if (locatableEntity.isVisible())
		{
			int direction;
			InterfaceComponent blockingInterface = getInterfaceInfrontOfEntity(locatableEntity);
			if (blockingInterface == null)
			{
				return true;
			}
			direction = blockingInterface.getBounds().x <= (Screen.getBounds().width / 2) ? 1 : -1;
			Camera.turnTo(getCorrectYaw(Camera.getYaw() + Utils.randomInt(45, 90) * direction));
		}
		else
		{
			Camera.turnTo(locatableEntity);
		}
		return false;
	}
	public static boolean isEntityBehindUI(LocatableEntity locatableEntity)
	{
		return getInterfaceInfrontOfEntity(locatableEntity) != null;
	}
	public static InterfaceComponent getInterfaceInfrontOfEntity(LocatableEntity locatableEntity)
	{
		Point entityPoint = locatableEntity.getInteractionPoint();
		//TMP
		for (InterfaceComponent currInterface : Interfaces.newQuery().visible().types(Type.CONTAINER, Type.SPRITE_GRID, Type.BOX)
																	 .filter(currInterface -> !IGNORED_INTERFACES.contains(currInterface.getId()) && interfaceContainsPoint(currInterface, entityPoint)).results())
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
		//END TMP
		return Interfaces.newQuery().visible().types(Type.CONTAINER, Type.SPRITE_GRID, Type.BOX)
									.filter(currInterface -> !IGNORED_INTERFACES.contains(currInterface.getId()) && interfaceContainsPoint(currInterface, entityPoint)).results().first();
	}
	public static boolean interfaceContainsPoint(InterfaceComponent interfaceComponent, Point point)
	{
		InteractableRectangle bounds = interfaceComponent.getBounds();
		return bounds != null && bounds.contains(point);
	}
	public static int getCorrectYaw(int yaw)
	{
		if (yaw >= 0)
		{
			if (yaw >= 360)
			{
				return yaw % 360;
			}
			return yaw;
		}
		else
		{
			if (yaw < -360)
			{
				return 360 + (yaw % 360);
			}
			return 360 + yaw;
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
		URL url;
		String priceLine;
		String databaseURL;
		HttpURLConnection httpConnection;
		if (Environment.isRS3())
		{
			databaseURL = "http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=";
		}
		else
		{
			databaseURL = "http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=";
		}
		try
		{
			url = new URL(databaseURL + itemID);
			httpConnection = (HttpURLConnection)url.openConnection();
			try (BufferedReader responseReader = new BufferedReader( new InputStreamReader(httpConnection.getInputStream()) )) //"AutoCloseable" try (C# "using" equivalent)
			{
				priceLine = responseReader.readLine().split(":", 16)[14];
				return Integer.parseInt(priceLine.substring(0, priceLine.length() - 9)); //Gets rid of the "},&quot;today&quot;" string | Note: &quot; => "
			}
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("ERROR: ItemID \"" + itemID + "\" does not exist!");
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
	public static int getNeededGold(ItemAmount itemAmount, int goldPerAmount)
	{
		return (Inventory.getQuantity(itemAmount.getItemID()) / itemAmount.getAmount()) * goldPerAmount;
	}
	public static boolean isInterfaceVisible(int id)
	{
		return isInterfaceVisible(id, null);
	}
	public static boolean isInterfaceVisible(int id, Predicate<InterfaceComponent> filter)
	{
		InterfaceComponent interfaceComponent = getInterfaceComponent(id, filter);
		if (interfaceComponent != null)
		{
			debugLog("IsInterfaceVisible(" + id + ") [" + safeToString(interfaceComponent.getText()) + "] = " + interfaceComponent.isVisible());
		}
		else
		{
			debugLog("IsInterfaceVisible(" + id + ") [???] = ???");
		}
		return interfaceComponent != null && interfaceComponent.isVisible();
	}
	public static InterfaceComponent getInterfaceComponent(int id)
	{
		return getInterfaceComponent(id, null);
	}
	public static InterfaceComponent getInterfaceComponent(int id, Predicate<InterfaceComponent> filter)
	{
		InterfaceComponentQueryBuilder interfaceQueryBuilder = Interfaces.newQuery().ids(id);
		if (filter != null)
		{
			interfaceQueryBuilder = interfaceQueryBuilder.filter(filter);
		}
		return interfaceQueryBuilder.results().first();
	}
	public static InterfaceComponent getInterfaceComponent(int... indices)
	{
		InterfaceComponent currInterface = InterfaceContainers.getAt(indices[0]).getComponent(indices[1]);
		for (int currArrayIndex = 2; currArrayIndex < indices.length; currArrayIndex++)
		{
			currInterface = currInterface.getChild(indices[currArrayIndex]);
		}
		return currInterface;
	}
	public static String toBinaryLeadingZeros(int number)
	{
		int bitValue = 0x40000000; //01000000 00000000 00000000 00000000
	    char[] binaryChars = new char[32];
	    int continueIndex = 0;
	    if (number >= 0)
	    {
	    	binaryChars[0] = '0';
	    }
	    else
	    {
    		binaryChars[0] = '1';
    		number = -number;
	    }
	    for (int currCharIndex = 1; currCharIndex < binaryChars.length; currCharIndex++)
	    {
	    	if (number >= bitValue)
	    	{
	    		binaryChars[currCharIndex] = '1';
	    		number -= bitValue;
	    		if (number == 0)
	    		{
	    			continueIndex = currCharIndex + 1;
	    			break;
	    		}
	    	}
	    	else
	    	{
	    		binaryChars[currCharIndex] = '0';
	    	}
	    	bitValue = bitValue >> 1;
	    }
	    for (int currCharIndex = continueIndex; currCharIndex < binaryChars.length; currCharIndex++)
	    {
	        binaryChars[currCharIndex] = '0';
	    }
	    return new String(binaryChars);
	}
	public static boolean isValidPlayerName(String playerName)
	{
		return !Utils.isEmptyString(playerName) && playerName.charAt(0) != '#'; //The second check is only relevant for the tutorial bot
	}
	/**
	 * Waits some random miliseconds (Useful for when many input actions need to be done within the same task)
	 */
	public static void randomInputDelay()
	{
		randomDelay(100, 350);
	}
	public static void randomDelay(int min, int max)
	{
		Execution.delay(Utils.randomInt(min, max));
	}
	public static void debugLog(String message)
	{
		if (Environment.isSDK())
		{
			System.out.println("[DEBUG] " + message);
		}
	}
	public static void debugLog(Object data)
	{
		if (data != null)
		{
			Class<?> dataClass = data.getClass();
			if (dataClass.isArray())
			{
				debugLogArray((Object[])data);
			}
			else if (Iterable.class.isAssignableFrom(dataClass))
			{
				debugLogIterable((Iterable<?>)data);
			}
			else
			{
				debugLog(data.toString());
			}
		}
		else
		{
			debugLog("null");
		}
	}
	public static void debugLogArray(Object[] data)
	{
		if (Environment.isSDK())
		{
			Arrays.toString(data);
		}
	}
	public static <T> void debugLogIterable(Iterable<T> data)
	{
		if (Environment.isSDK())
		{
			debugLog(iterableToString(data));
		}
	}
	public static <T> String iterableToString(Iterable<T> iterable)
	{
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<T> iterator = iterable.iterator();
		stringBuilder.append("[");
		if (iterator.hasNext())
		{
			stringBuilder.append( safeToString(iterator.next()) );
			while (iterator.hasNext())
			{
				stringBuilder.append(", " + safeToString(iterator.next()) );
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
