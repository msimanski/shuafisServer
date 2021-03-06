package com.mfsimanski.shuafisserver.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.mfsimanski.shuafisserver.model.Statistics;
import com.mfsimanski.shuafisserver.model.StatisticsRepository;

/**
 * @author michaelsimanski
 * Spring controller that is responsible for database statistics.
 */
@Controller
@CrossOrigin //("http://localhost:2908")
public class StatisticsController
{
	// CRUD database repository
	@Autowired
	StatisticsRepository statisticsRepository;

	/**
	 * Upon request, return a payload with statistics from database.
	 * @return Payload with statistics from database.
	 */
	@GetMapping("/getstats")
	public ResponseEntity<Map<String, Object>> getStats() 
	{
		Optional<Statistics> statsOptional = statisticsRepository.findById(1);
		Statistics stats = statsOptional.get();
		
		HashMap<String, Object> temp = new HashMap<String, Object>();
		temp.put("indexedprofiles", stats.getIndexedProfiles());
		temp.put("totalqueries", stats.getTotalQueries());
		temp.put("totalidentqueries", stats.getTotalIdentQueries());
		
		return ResponseEntity.status(HttpStatus.OK).body(temp);
	}
}
