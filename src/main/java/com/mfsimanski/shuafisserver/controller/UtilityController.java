package com.mfsimanski.shuafisserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfsimanski.shuafisserver.model.Profile;
import com.mfsimanski.shuafisserver.model.ProfileRepository;

/**
 * @author michaelsimanski
 * Spring controller that handles miscellaneous utility requests.
 */
@Controller
@CrossOrigin //("http://localhost:2908")
public class UtilityController
{
	@Autowired
	ProfileRepository profileRepository;

	/**
	 * When requested, return all indexed profiles from database.
	 * @return A list of all indexed profiles.
	 */
	@GetMapping("/all")
	public @ResponseBody Iterable<Profile> getAllUsers() 
	{
	    return profileRepository.findAll();
	}

}
