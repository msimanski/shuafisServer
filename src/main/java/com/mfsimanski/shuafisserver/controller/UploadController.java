package com.mfsimanski.shuafisserver.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.Profile;
import com.mfsimanski.shuafisserver.model.ProfileRepository;
import com.mfsimanski.shuafisserver.model.Statistics;
import com.mfsimanski.shuafisserver.model.StatisticsRepository;
import com.mfsimanski.shuafisserver.service.FilesStorageService;
import com.mfsimanski.shuafisserver.utility.Utility;

@Controller
@CrossOrigin //("http://localhost:2908")
public class UploadController
{
	@Autowired
	FilesStorageService storageService;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	StatisticsRepository statisticsRepository;

	/**
	 * Post handler to upload a new profile.
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
	 * @param name
	 * @param address
	 * @param city
	 * @param state
	 * @param zip
	 * @param phone
	 * @param ssid
	 * @param dob
	 * @return
	 */
	@PostMapping("/uploadprofile")
	public ResponseEntity<Map<String, Object>> uploadProfile(
			@RequestParam("leftLittleFile") MultipartFile leftLittleFile,
			@RequestParam("leftRingFile") MultipartFile leftRingFile,
			@RequestParam("leftMiddleFile") MultipartFile leftMiddleFile,
			@RequestParam("leftIndexFile") MultipartFile leftIndexFile,
			@RequestParam("leftThumbFile") MultipartFile leftThumbFile,
			@RequestParam("rightLittleFile") MultipartFile rightLittleFile,
			@RequestParam("rightRingFile") MultipartFile rightRingFile,
			@RequestParam("rightMiddleFile") MultipartFile rightMiddleFile,
			@RequestParam("rightIndexFile") MultipartFile rightIndexFile,
			@RequestParam("rightThumbFile") MultipartFile rightThumbFile,
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("city") String city,
			@RequestParam("state") String state,
			@RequestParam("zip") String zip,
			@RequestParam("phone") String phone,
			@RequestParam("ssid") String ssid,
			@RequestParam("dob") String dob
			) 
	{
		// Create the Profile instance, then add it to the database.
		Profile newProfile = new Profile();
		newProfile.setName(name);
		newProfile.setAddress(address);
		newProfile.setCity(city);
		newProfile.setState(state);
		newProfile.setZip(zip);
		newProfile.setPhone(phone);
		newProfile.setSsid(ssid);
		newProfile.setDob(dob);
		
		// Simultaneously get the id and save the new profile.
		int id = profileRepository.save(newProfile).getId();
		newProfile.setId(id);
		SHUAFISMain.candidates.add(newProfile);
		
		// Save the files to new folder.
		try
		{
			Utility.saveNewTencardImages(id, leftLittleFile, leftRingFile, leftMiddleFile, leftIndexFile, 
					leftThumbFile, rightLittleFile, rightRingFile, rightMiddleFile, rightIndexFile, rightThumbFile);
		} catch (IOException e1)
		{
			e1.printStackTrace();
			
		    HashMap<String, Object> temp = new HashMap<String, Object>();
		    temp.put("succeeded", false);
		    temp.put("message", "Failed to save files on server. Contact your system administrator.");
		    Map<String, Object> r = temp;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
		}
		
		// Cache the files.
		try
		{
			Utility.cacheTencardImages(new File("prints/" + Integer.toString(id)));
		} catch (IOException e2)
		{
			e2.printStackTrace();
		    HashMap<String, Object> temp = new HashMap<String, Object>();
		    temp.put("succeeded", false);
		    temp.put("message", "Failed to cache files on server. Contact your system administrator.");
		    Map<String, Object> r = temp;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
		}
		
		// Associate the cache with the new instance.
		try
		{
			Utility.associatePrintsWithProfile(newProfile);
		} catch (IOException e3)
		{
			e3.printStackTrace();
		    HashMap<String, Object> temp = new HashMap<String, Object>();
		    temp.put("succeeded", false);
		    temp.put("message", "Failed to associate cache on server. Contact your system administrator.");
		    Map<String, Object> r = temp;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
		}
		
		// Update profile count.
		Optional<Statistics> statsOptional = statisticsRepository.findById(1);
		Statistics stats = statsOptional.get();
		stats.setIndexedProfiles((int)profileRepository.count());
		statisticsRepository.save(stats);
		
	    HashMap<String, Object> temp = new HashMap<String, Object>();
	    temp.put("succeed", true);
	    temp.put("message", "Profile uploaed successfully.");
	    temp.put("id", id);
	    Map<String, Object> r = temp;
		return ResponseEntity.status(HttpStatus.OK).body(r);
	}
}
