package com.mfsimanski.shuafisserver.utility;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.mfsimanski.shuafisserver.Prints;
import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.Profile;

/**
 * @author michaelsimanski
 * Class housing miscellaneous utility helper methods.
 */
public class SHUAFISUtility
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
		System.out.println(LocalDateTime.now() + " [INFO]: Encaching directory " + directory.getAbsolutePath() + ".");
		
		// Array containing all files in the directory
		File[] listOfFiles = directory.listFiles(File::isFile);
		Arrays.sort(listOfFiles);
		
		// Create a new directory to put the compressed files in.
		File newDirectory = new File(directory.getAbsolutePath() + "/cache");
		newDirectory.mkdir();
		
		// Iterate through each file in directory. Load each file into memory and encode, then compress.
		for (File file : listOfFiles) 
		{
			System.out.println(LocalDateTime.now() + " [INFO]: Encaching file " + file.getAbsolutePath() + ".");
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
	 * Saves supplied tencard files to a directory named the same as the id.
	 * @param id
	 * @param leftLittleFile
	 * @param leftRingFile
	 * @param leftMiddleFile
	 * @param leftIndexFile
	 * @param leftThumbFile
	 * @param rightLittleFile
	 * @param rightRingFile
	 * @param rightMiddleFile
	 * @param rightIndexFile
	 * @param rightThumbFile
	 */
	public static void saveNewTencardImages(
			int id,
			MultipartFile leftLittleFile,
			MultipartFile leftRingFile,
			MultipartFile leftMiddleFile,
			MultipartFile leftIndexFile,
			MultipartFile leftThumbFile,
			MultipartFile rightLittleFile,
			MultipartFile rightRingFile,
			MultipartFile rightMiddleFile,
			MultipartFile rightIndexFile,
			MultipartFile rightThumbFile) throws IOException
	{
		// Make a new folder with the ID of the new instance.
		File newDirectory = new File("prints/" + id);
		newDirectory.mkdir();
		
		// Save the files.
		Path filepath = Paths.get(newDirectory.getAbsolutePath(), leftIndexFile.getOriginalFilename());
	    OutputStream os = Files.newOutputStream(filepath);
	    os.write(leftIndexFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), leftLittleFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(leftLittleFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), leftMiddleFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(leftMiddleFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), leftRingFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(leftRingFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), leftThumbFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(leftThumbFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), rightIndexFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(rightIndexFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), rightLittleFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(rightLittleFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), rightMiddleFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(rightMiddleFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), rightRingFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(rightRingFile.getBytes());
	    filepath = Paths.get(newDirectory.getAbsolutePath(), rightThumbFile.getOriginalFilename());
	    os = Files.newOutputStream(filepath);
	    os.write(rightThumbFile.getBytes());
	}
	
	/**
	 * Generates cached images for all directories in "/prints".
	 * @throws IOException Throws IOException if directory is invalid:
	 * @see IOException
	 */
	public static void encacheAllPrintsDirectories() throws IOException 
	{
		System.out.println(LocalDateTime.now() + " [INFO]: Encaching all directories in /prints. This is an extremely expensive operation, so it will take some time.");
		File printsDirectory = new File("prints/");
		// Array containing all directories in the directory
		File[] listOfFiles = printsDirectory.listFiles(File::isDirectory);
		Arrays.sort(listOfFiles);
		
		// Each file in that array will have it's own files, so we have to loop through them.
		for (File file : listOfFiles)
		{
			// Call a helper method.
			cacheTencardImages(file);
		}
	}
	
	/**
	 * Associate given profile with cached prints found in /prints/profile.id/cache.
	 * @param profile Profile to be associated.
	 * @throws IOException If file or directory invalid:
	 * @see IOException
	 */
	public static void associatePrintsWithProfile(Profile profile) throws IOException 
	{
		// Initialize prints.
		profile.prints = new Prints();
		
		// Directory of cached files.
		File temp = new File("prints/" + Integer.toString(profile.id) + "/cache");
		File[] arrayOfFiles = temp.listFiles((dir, name) -> name.toLowerCase().endsWith(".gz"));
		Arrays.sort(arrayOfFiles);
		
		profile.prints.leftIndex = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[0].getAbsolutePath())));
		profile.prints.leftLittle = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[1].getAbsolutePath())));
		profile.prints.leftMiddle = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[2].getAbsolutePath())));
		profile.prints.leftRing = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[3].getAbsolutePath())));
		profile.prints.leftThumb = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[4].getAbsolutePath())));
		profile.prints.rightIndex = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[5].getAbsolutePath())));
		profile.prints.rightLittle = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[6].getAbsolutePath())));
		profile.prints.rightMiddle = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[7].getAbsolutePath())));
		profile.prints.rightRing = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[8].getAbsolutePath())));
		profile.prints.rightThumb = new FingerprintTemplate(Files.readAllBytes(Paths.get(arrayOfFiles[9].getAbsolutePath())));
	}
	
	/**
	 * Associates SHUAFISMain.canidates with their respective prints in /prints.
	 * @see associatePrintsWithProfile()
	 */
	public static void associateAllPrints() 
	{
		for (Profile profile : SHUAFISMain.candidates) 
		{
			try
			{
				associatePrintsWithProfile(profile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

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
