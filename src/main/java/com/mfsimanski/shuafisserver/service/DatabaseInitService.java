package com.mfsimanski.shuafisserver.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.Profile;
import com.mfsimanski.shuafisserver.model.ProfileRepository;
import com.mfsimanski.shuafisserver.utility.Utility;

@Service
public class DatabaseInitService
{
	@Autowired
	ProfileRepository profileRepository;
	
	/**
	 * 
	 */
	public void loadPrintsFromCacheToDatabase() 
	{
		Faker faker = new Faker(new Locale("en-US"));
		
		//Utility.loadPrints(false, true);
		
		ArrayList<Profile> canidates = new ArrayList<Profile>(SHUAFISMain.canidates);
		Iterable<Profile> iterable = canidates;
		
		for (Profile profile : iterable)
		{
			Profile temp = new Profile();
			temp.setName(faker.name().nameWithMiddle());
			temp.setAddress(faker.address().streetAddress());
			temp.setCity(faker.address().city());
			temp.setState(faker.address().state());
			temp.setZip(faker.address().zipCode());
			temp.setPhone(faker.phoneNumber().phoneNumber());
			temp.setSsid(faker.number().digits(12));
			profileRepository.save(temp);
			System.out.println(LocalDateTime.now() + " [INFO]: Uploaded profile #" + temp.id);
		}
	}
}
