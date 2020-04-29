package com.mfsimanski.shuafisserver;

import java.util.ArrayList;
import java.util.Optional;

import javax.annotation.Resource;

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
import com.mfsimanski.shuafisserver.service.FilesStorageService;
import com.mfsimanski.shuafisserver.utility.Utility;

/**
 * @author michaelsimanski
 */
@ComponentScan("com.mfsimanski.shuafisserver")
@SpringBootApplication()
public class SHUAFISMain implements CommandLineRunner
{
	@Resource
	FilesStorageService storageService;
	
	@Autowired
	StatisticsRepository statisticsRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	DatabaseInitService databaseInitService;

	public static ArrayList<Profile> candidates;

	public static void main(String[] args)
	{	
		Utility.throwBanner();
		
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
	
	private void bootProcess() 
	{
		Optional<Statistics> statsOptional = statisticsRepository.findById(1);
		Statistics stats = statsOptional.get();
		stats.setIndexedProfiles((int)profileRepository.count());
		statisticsRepository.save(stats);
		
		// Initialize candidates
		candidates = new ArrayList<Profile>();
		// storageService.deleteAll();
		storageService.init();
		
		databaseInitService.loadCanidates();
		Utility.associateAllPrints();
	}
}
