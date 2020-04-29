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
public class UtilityController
{
	@Autowired
	ProfileRepository profileRepository;

	/**
	 * @return
	 */
	@GetMapping("/all")
	public @ResponseBody Iterable<Profile> getAllUsers() 
	{
	    return profileRepository.findAll();
	}

}
