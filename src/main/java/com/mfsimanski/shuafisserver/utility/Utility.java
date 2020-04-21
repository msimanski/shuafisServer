package com.mfsimanski.shuafisserver.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.mfsimanski.shuafisserver.Prints;
import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.Profile;

public class Utility
{
	/**
	 * @param canidates
	 */
	public static void initializeProfiles(ArrayList<Profile> canidates) 
	{
		for (int i = 0; i < 600; i++)
		{
			canidates.add(new Profile(i + 1));
			canidates.get(i).prints = new Prints();
		}
	}

	/**
	 * @param cache
	 * @param loadFromCache
	 */
	public static void loadPrints(boolean cache, boolean loadFromCache)
	{
		SHUAFISMain.canidates = new ArrayList<Profile>();
		initializeProfiles(SHUAFISMain.canidates);
		ArrayList<String> pathArrayList = new ArrayList<String>();
	
		// Before starting the server, load the prints into memory
		System.out.println(LocalDateTime.now() + " [INFO]: Loading prints into memory. This will take a long time.");
	
		try (Stream<Path> paths = Files
				.walk(Paths.get("/Users/michaelsimanski/eclipse-workspace/shuafisServer/prints/")))
		{
			paths.filter(Files::isRegularFile).forEach(path ->
			{
				pathArrayList.add(path.toString());
			});
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	
		pathArrayList.sort(String::compareToIgnoreCase);
		try
		{
			Utility.loadPrintsToMemory(SHUAFISMain.canidates, pathArrayList, cache, loadFromCache);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param canidates
	 * @param pathArrayList
	 * @param cache
	 * @param loadFromCache
	 * @throws IOException
	 */
	public static void loadPrintsToMemory(ArrayList<Profile> canidates, ArrayList<String> pathArrayList, boolean cache, boolean loadFromCache) throws IOException 
	{
		int index = 0;
		
		for (int i = 0; i < canidates.size(); i++)
		{
			System.out.println(LocalDateTime.now() + " [INFO]: Loading profile #" + (i +1));
			
			if (loadFromCache) 
			{
				canidates.get(i).prints.loadTemplatesFromCache(index, pathArrayList);
			}
			else
			{
				canidates.get(i).prints.loadTemplatesFromImage(index, pathArrayList, cache);
			}
			
			index += 10;
		}
	}

	/**
	 * 
	 */
	public static void throwBanner() 
	{
//		  .d8888b.  888    888 888     888        d8888 8888888888 8888888  .d8888b.  
//		 d88P  Y88b 888    888 888     888       d88888 888          888   d88P  Y88b 
//		 Y88b.      888    888 888     888      d88P888 888          888   Y88b.      
//		  "Y888b.   8888888888 888     888     d88P 888 8888888      888    "Y888b.   
//		     "Y88b. 888    888 888     888    d88P  888 888          888       "Y88b. 
//		       "888 888    888 888     888   d88P   888 888          888         "888 
//		 Y88b  d88P 888    888 Y88b. .d88P  d8888888888 888          888   Y88b  d88P 
//		  "Y8888P"  888    888  "Y88888P"  d88P     888 888        8888888  "Y8888P" 
//		 ----------------------------------------------------------------------------
//		   SETON       HILL    UNIVERSITY   AUTOMATED	FINGERPRINT  ID      SYSTEM
//		 ----------------------------------------------------------------------------
		 
		System.out.println(" .d8888b.  888    888 888     888        d8888 8888888888 8888888  .d8888b.  ");
		System.out.println("d88P  Y88b 888    888 888     888       d88888 888          888   d88P  Y88b ");
		System.out.println("Y88b.      888    888 888     888      d88P888 888          888   Y88b.      ");
		System.out.println(" \"Y888b.   8888888888 888     888     d88P 888 8888888      888    \"Y888b.   ");
		System.out.println("    \"Y88b. 888    888 888     888    d88P  888 888          888       \"Y88b. ");
		System.out.println("      \"888 888    888 888     888   d88P   888 888          888         \"888 ");
		System.out.println("Y88b  d88P 888    888 Y88b. .d88P  d8888888888 888          888   Y88b  d88P ");
		System.out.println(" \"Y8888P\"  888    888  \"Y88888P\"  d88P     888 888        8888888  \"Y8888P\"  ");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("  SETON       HILL    UNIVERSITY   AUTOMATED   FINGERPRINT  ID      SYSTEM");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println();
		System.out.println();
	}
}
