package com.mfsimanski.shuafisserver;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.mfsimanski.shuafisserver.model.Profile;
import com.mfsimanski.shuafisserver.model.ProfileRepository;
import com.mfsimanski.shuafisserver.model.Statistics;
import com.mfsimanski.shuafisserver.model.StatisticsRepository;
import com.mfsimanski.shuafisserver.service.DatabaseInitService;
import com.mfsimanski.shuafisserver.utility.SHUAFISUtility;

/**
 * @author michaelsimanski
 * Main class controlling the application. Central location of candidates in memory.
 */
@ComponentScan("com.mfsimanski.shuafisserver")
@SpringBootApplication()
public class SHUAFISMain implements CommandLineRunner
{
	// CRUD repository objects
	@Autowired
	StatisticsRepository statisticsRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	// Database service
	@Autowired
	DatabaseInitService databaseInitService;
	
	// List of candidates in memory
	public static ArrayList<Profile> candidates;

	public static void main(String[] args)
	{	
		SHUAFISUtility.throwBanner();
		
		SpringApplication.run(SHUAFISMain.class, args);
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... arg) throws Exception
	{
		bootProcess();
	}
	
	/**
	 * Manages the server boot process.
	 */
	private void bootProcess() 
	{
		// Initialize the server statistics
		Optional<Statistics> statsOptional = statisticsRepository.findById(1);
		Statistics stats = statsOptional.get();
		stats.setIndexedProfiles((int)profileRepository.count());
		statisticsRepository.save(stats);
		
		// Initialize candidates
		candidates = new ArrayList<Profile>();
		
		// Load the candidates from memory and associate each with their corresponding prints
		databaseInitService.loadCanidates();
		SHUAFISUtility.associateAllPrints();
	}
}
