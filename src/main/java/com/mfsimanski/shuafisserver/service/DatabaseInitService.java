package com.mfsimanski.shuafisserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.mfsimanski.shuafisserver.SHUAFISMain;
import com.mfsimanski.shuafisserver.model.Profile;
import com.mfsimanski.shuafisserver.model.ProfileRepository;

@Service
public class DatabaseInitService
{
	@Autowired
	ProfileRepository profileRepository;
	
	/**
	 * Loads candidates to local memory in the form of an ArrayList in SHUAFISMain.
	 */
	public void loadCanidates() 
	{	
		// Iterable of all profiles loaded in database.
		Iterable<Profile> repositoryIterable = profileRepository.findAll();
		
		// For each profile in repository, load the table data into a temporary instance of Profile and
		// add it to SHUAFISMain.candidates.
		for (Profile profile : repositoryIterable)
		{
			Profile temp = new Profile();
			temp.setId(profile.getId());
			temp.setName(profile.getName());
			temp.setAddress(profile.getAddress());
			temp.setCity(profile.getCity());
			temp.setState(profile.getState());
			temp.setZip(profile.getZip());
			temp.setPhone(profile.getPhone());
			temp.setSsid(profile.getSsid());
			SHUAFISMain.candidates.add(temp);
			System.out.println(LocalDateTime.now() + " [INFO]: Retrived profile ID: " + temp.getId() + ".");
		}
	}
}
