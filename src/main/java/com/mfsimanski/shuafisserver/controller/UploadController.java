package com.mfsimanski.shuafisserver.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.mfsimanski.shuafisserver.Query;
import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.FileInfo;
import com.mfsimanski.shuafisserver.model.Profile;
import com.mfsimanski.shuafisserver.model.ProfileRepository;
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

//	@PostMapping("/upload")
//	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file)
//	{
//		String message = "";
//		try
//		{
//			storageService.save(file);
//
//			message = "Uploaded the file successfully: " + file.getOriginalFilename();
//			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//		} catch (Exception e)
//		{
//			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//		}
//	}

	/**
	 * @param file1
	 * @param file2
	 * @return
	 */
	@PostMapping("/compareonetoone")
	public ResponseEntity<Map<String, Object>> compareOneToOne(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) 
	{
	    String message = "";
	    
	    System.out.println(LocalDateTime.now() + " [INFO]: 1:1 comparison request made.");
	    
	    try 
	    {
	      // Candidate
	      //storageService.save(file1);
	      // probe
	      //storageService.save(file2);
	      
	      Map<String, Object> result = Query.compareOneToOne(40, file1.getBytes(), file2.getBytes());
	      
	      System.out.println(LocalDateTime.now() + " [INFO]: 1:1 comparison successful.");
	      
	      return ResponseEntity.status(HttpStatus.OK).body(result);
	    } 
	    catch (Exception e) 
	    {
	    	System.out.println(LocalDateTime.now() + " [ERROR]: 1:1 comparison failed.");
	    	
	    	message = "Could not upload the files! " + e.getStackTrace();
	    	HashMap<String, Object> temp = new HashMap<String, Object>();
	    	temp.put("message", message);
	    	Map<String, Object> r = temp;
	    	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(r);
	    }
	}

	/**
	 * @param file
	 * @return
	 */
	@PostMapping("/compareoneton")
	public ResponseEntity<Map<String, Object>> compareOneToN(@RequestParam("file") MultipartFile file)
	{
		System.out.println(LocalDateTime.now() + " [INFO]: 1:N comparison request made.");
		
		String message = "";
		try
		{
			//storageService.save(file);

			Map<String, Object> result = Query.compareOneToN(file.getBytes(), SHUAFISMain.candidates);
			
			System.out.println(LocalDateTime.now() + " [INFO]: 1:N comparison request successful.");
			
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} 
		catch (Exception e)
		{
			System.out.println(LocalDateTime.now() + " [ERROR]: 1:N comparison request failed.");
			
			message = "Could not upload the files! " + ExceptionUtils.getStackTrace(e);
		    HashMap<String, Object> temp = new HashMap<String, Object>();
		    temp.put("message", message);
		    Map<String, Object> r = temp;
		    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(r);
		}
	}

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
			return ResponseEntity.status(HttpStatus.OK).body(r);
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
			return ResponseEntity.status(HttpStatus.OK).body(r);
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
			return ResponseEntity.status(HttpStatus.OK).body(r);
		}
		
		boolean result = true;
	    HashMap<String, Object> temp = new HashMap<String, Object>();
	    temp.put("succeeded", result);
	    Map<String, Object> r = temp;
		return ResponseEntity.status(HttpStatus.OK).body(r);
	}

	
	/**
	 * @param name
	 * @return
	 */
	@PostMapping("/add")
	public @ResponseBody String addNewUser (@RequestParam String name) 
	{
		Profile n = new Profile();
		n.setName(name);
		profileRepository.save(n);
		return "Saved";
	}

	/**
	 * @return
	 */
	@GetMapping("/all")
	public @ResponseBody Iterable<Profile> getAllUsers() 
	{
	    return profileRepository.findAll();
	}
	
	/**
	 * @return
	 */
	@GetMapping("/files")
	public ResponseEntity<List<FileInfo>> getListFiles()
	{
		List<FileInfo> fileInfos = storageService.loadAll().map(path ->
		{
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(UploadController.class, "getFile", path.getFileName().toString()).build().toString();

			return new FileInfo(filename, url);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

	/**
	 * @param filename
	 * @return
	 */
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename)
	{
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
