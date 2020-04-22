package com.mfsimanski.shuafisserver;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.mfsimanski.shuafisserver.model.Profile;
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
	DatabaseInitService databaseInitService;

	public static ArrayList<Profile> canidates;

	public static void main(String[] args)
	{	
		Utility.throwBanner();
		try
		{
			Utility.encapsulatePrintsInDirectory();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try
//		{
//			Utility.encacheAllPrintsDirectories();
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//SpringApplication.run(SHUAFISMain.class, args);
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... arg) throws Exception
	{
		// storageService.deleteAll();
		storageService.init();
		
	}
}
