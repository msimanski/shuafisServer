package com.mfsimanski.shuafisserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		profileRepository.deleteAll();
		
		Utility.loadPrints(false, true);
		
		ArrayList<Profile> canidates = new ArrayList<Profile>(SHUAFISMain.canidates);
		Iterable<Profile> iterable = canidates;
		
		for (Profile profile : iterable)
		{
			Profile temp = new Profile();
			temp.setName(profile.getName());
			temp.setAddress(profile.getAddress());
			temp.setCity(profile.getCity());
			temp.setState(profile.getState());
			temp.setZip(profile.getZip());
			temp.setPhone(profile.getPhone());
			temp.setSsid(profile.getSsid());
			profileRepository.save(temp);
			System.out.println(LocalDateTime.now() + " [INFO]: Uploaded profile #" + temp.id);
		}
	}

}
