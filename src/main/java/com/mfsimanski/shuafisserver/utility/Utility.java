package com.mfsimanski.shuafisserver.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.mfsimanski.shuafisserver.Prints;
import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.Profile;

public class Utility
{	
	/**
	 * When supplied with a directory, this method will create cached versions of the ten fingerprint
	 * images in .json.gz format. The compressed files will be deposited within a new sub-directory called
	 * "cache/".
	 * @param directory A directory containing 10 images of fingerprints to be cached.
	 * @throws IOException Throws IOException if file or directory is invalid:
	 * @see IOException
	 */
	public static void cacheTencardImages(File directory) throws IOException 
	{
		// Array containing all files in the directory
		File[] listOfFiles = directory.listFiles();
		
		// Create a new directory to put the compressed files in.
		File newDirectory = new File(directory.getAbsolutePath() + "/cache");
		newDirectory.mkdir();
		
		// Iterate through each file in directory. Load each file into memory and encode, then compress.
		for (File file : listOfFiles) 
		{
			byte[] probeImage = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			FingerprintTemplate probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			// This snippet saves it to a compressed format for faster load times.
			byte[] serialized = probe.toByteArray();
			Files.write(Paths.get(newDirectory.getAbsolutePath() + "/" + file.getName() + ".json.gz"), serialized);
		}
	}
	
	/**
	 * Generates cached images for all directories in "/prints".
	 * @throws IOException Throws IOException if directory is invalid:
	 * @see IOException
	 */
	public static void encacheAllPrintsDirectories() throws IOException 
	{
		File printsDirectory = new File("prints/");
		// Array containing all directories in the directory
		File[] listOfFiles = printsDirectory.listFiles(File::isDirectory);
		
		// Each file in that array will have it's own files, so we have to loop through them.
		for (File file : listOfFiles)
		{
			// Call a helper method.
			cacheTencardImages(file);
		}
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public static void encapsulatePrintsInDirectory() throws IOException 
	{
		//ArrayList<Profile> canidates = new ArrayList<Profile>(SHUAFISMain.canidates);
		//Iterable<Profile> iterable = canidates;
		
//		for (Profile profile : iterable) 
//		{
			//Path dir = Paths.get("prints/" + Integer.toString(profile.id));
			//Files.createDirectory(dir);
			
			File folder = new File("prints/");
			File[] listOfFiles = folder.listFiles();
			Arrays.sort(listOfFiles);
			
			ArrayList<File[]> fubar = new ArrayList<File[]>();
			for (int i = 0; i < 6000; i += 10) 
			{
				fubar.add(Arrays.copyOfRange(listOfFiles, i, i + 10));
			}
			
			int foo = 0;
			
			for (File[] file : fubar) 
			{
				File dest = new File("prints/" + Integer.toString(foo + 1));
				foo++;
				for (File unit : file) 
				{
					File source = new File(unit.getAbsolutePath());
					
					try {
					    FileUtils.copyFileToDirectory(source, dest);
					} catch (IOException e) {
					    e.printStackTrace();
					}
				}
			}
	}
			
//			int iterate = 0;
//			
//			for (int i = 0; i < 600; i += 10)
//			{
//				iterate = i;
//				for (int j = 0; j < 10; j++) 
//				{
//					File source = new File(listOfFiles[iterate].getAbsolutePath());
//					File dest = new File("prints/" + Integer.toString(profile.id));
//					try {
//					    FileUtils.copyFileToDirectory(source, dest);
//					} catch (IOException e) {
//					    e.printStackTrace();
//					}
//					iterate++;
//				}
//			}
		//}
	
//	/**
//	 * @param canidates
//	 */
//	public static void initializeProfiles(ArrayList<Profile> canidates) 
//	{
//		for (int i = 0; i < 600; i++)
//		{
//			canidates.add(new Profile(i + 1));
//			canidates.get(i).prints = new Prints();
//		}
//	}
//
//	/**
//	 * @param cache
//	 * @param loadFromCache
//	 */
//	public static void loadPrints(boolean cache, boolean loadFromCache)
//	{
//		SHUAFISMain.canidates = new ArrayList<Profile>();
//		initializeProfiles(SHUAFISMain.canidates);
//		ArrayList<String> pathArrayList = new ArrayList<String>();
//	
//		// Before starting the server, load the prints into memory
//		System.out.println(LocalDateTime.now() + " [INFO]: Loading prints into memory. This will take a long time.");
//	
//		try (Stream<Path> paths = Files
//				.walk(Paths.get("prints/")))
//		{
//			paths.filter(Files::isRegularFile).forEach(path ->
//			{
//				pathArrayList.add(path.toString());
//			});
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	
//		pathArrayList.sort(String::compareToIgnoreCase);
//		try
//		{
//			Utility.loadPrintsToMemory(SHUAFISMain.canidates, pathArrayList, cache, loadFromCache);
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * @param canidates
//	 * @param pathArrayList
//	 * @param cache
//	 * @param loadFromCache
//	 * @throws IOException
//	 */
//	public static void loadPrintsToMemory(ArrayList<Profile> canidates, ArrayList<String> pathArrayList, boolean cache, boolean loadFromCache) throws IOException 
//	{
//		int index = 0;
//		
//		for (int i = 0; i < canidates.size(); i++)
//		{
//			System.out.println(LocalDateTime.now() + " [INFO]: Loading profile #" + (i +1));
//			
//			if (loadFromCache) 
//			{
//				canidates.get(i).prints.loadTemplatesFromCache(index, pathArrayList);
//			}
//			else
//			{
//				canidates.get(i).prints.loadTemplatesFromImage(index, pathArrayList, cache);
//			}
//			
//			index += 10;
//		}
//	}

	/**
	 * Displays logo banner to standard output.
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
//		   SETON  HILL  UNIVERSITY   AUTOMATED  FINGERPRINT  IDENTIFICATION  SYSTEM
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
		System.out.println("  SETON  HILL  UNIVERSITY   AUTOMATED  FINGERPRINT  IDENTIFICATION  SYSTEM");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println("\"Hazard, yet forward!\"");
		System.out.println();
	}
}
