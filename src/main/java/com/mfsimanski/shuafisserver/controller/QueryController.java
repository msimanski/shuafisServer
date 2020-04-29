package com.mfsimanski.shuafisserver.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mfsimanski.shuafisserver.Query;
import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.ProfileRepository;
import com.mfsimanski.shuafisserver.model.Statistics;
import com.mfsimanski.shuafisserver.model.StatisticsRepository;

@Controller
@CrossOrigin //("http://localhost:2908")
public class QueryController
{
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	StatisticsRepository statisticsRepository;

	/**
	 * @param file
	 * @return
	 */
	@PostMapping("/compareoneton")
	public ResponseEntity<Map<String, Object>> compareOneToN(@RequestParam("file") MultipartFile file)
	{
		// Increment the query number
		Optional<Statistics> statsOptional = statisticsRepository.findById(1);
		Statistics stats = statsOptional.get();
		stats.setTotalQueries(stats.getTotalQueries() + 1);
		statisticsRepository.save(stats);
		
		System.out.println(LocalDateTime.now() + " [INFO]: 1:N comparison request made.");
		
		String message = "";
		try
		{
			//storageService.save(file);
	
			Map<String, Object> result = Query.compareOneToN(file.getBytes(), SHUAFISMain.candidates);
			
			// Increment ident number
			if (result.get("ident").equals(true)) 
			{
				// Increment the query number
				statsOptional = statisticsRepository.findById(1);
				stats = statsOptional.get();
				stats.setTotalIdentQueries(stats.getTotalIdentQueries() + 1);
				statisticsRepository.save(stats);
			}
			
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
	 * @param file1
	 * @param file2
	 * @return
	 */
	@PostMapping("/compareonetoone")
	public ResponseEntity<Map<String, Object>> compareOneToOne(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) 
	{
		// Increment the query number
		Optional<Statistics> statsOptional = statisticsRepository.findById(1);
		Statistics stats = statsOptional.get();
		stats.setTotalQueries(stats.getTotalQueries() + 1);
		statisticsRepository.save(stats);
				
	    String message = "";
	    
	    System.out.println(LocalDateTime.now() + " [INFO]: 1:1 comparison request made.");
	    
	    try 
	    {
	      // Candidate
	      //storageService.save(file1);
	      // probe
	      //storageService.save(file2);
	      
	      Map<String, Object> result = Query.compareOneToOne(40, file1.getBytes(), file2.getBytes());
	      
	      // Increment ident number
		  if (result.get("ident").equals(true)) 
		  {
			// Increment the query number
			statsOptional = statisticsRepository.findById(1);
			stats = statsOptional.get();
			stats.setTotalIdentQueries(stats.getTotalIdentQueries() + 1);
			statisticsRepository.save(stats);
		  }
	      
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

}
