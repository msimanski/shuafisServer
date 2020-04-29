package com.mfsimanski.shuafisserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.mfsimanski.shuafisserver.model.Profile;
import com.mfsimanski.shuafisserver.model.Statistics;
import com.mfsimanski.shuafisserver.model.StatisticsRepository;

public class Query
{	
	/**
	 * @param threshold
	 * @param probeImage
	 * @param canidateImage
	 * @return
	 */
	public static Map<String, Object> compareOneToOne(int threshold, byte[] probeImage, byte[] canidateImage) 
	{
		// Fingers goes:
		// Left index	0
		// Left little	1
		// Left middle	2
		// Left ring	3
		// Left thumb	4
		// Right index	5
		// Right little	6
		// Right middle	7
		// Right ring	8
		// Right thumb	9
		
		FingerprintTemplate probe = new FingerprintTemplate(new FingerprintImage().dpi(500).decode(probeImage));
		FingerprintTemplate candidate = new FingerprintTemplate(new FingerprintImage().dpi(500).decode(canidateImage));
		
		HashMap<String, Object> results = new HashMap<String, Object>();
		
		double startTime = System.currentTimeMillis();
		double score = new FingerprintMatcher().index(probe).match(candidate);
		double stopTime = System.currentTimeMillis();
		double timeResult = stopTime - startTime;
		
		if (score >= threshold)
		{
			results.put("ident", true);
		}
		else 
		{
			results.put("ident", false);
		}
		results.put("score", score);
		results.put("time", timeResult);
		
		Map<String, Object> iHateInterfaces = results;
		return iHateInterfaces;
	}

	/**
	 * @param probeRaw
	 * @param candidates
	 * @return
	 */
	public static Map<String, Object> compareOneToN(byte[] probeRaw, ArrayList<Profile> candidates)
	{	
		FingerprintTemplate probe = new FingerprintTemplate(new FingerprintImage().dpi(500).decode(probeRaw));
		FingerprintMatcher matcher = new FingerprintMatcher().index(probe);
		Profile match = null;
		double high = 0;
		
		double startTime = System.currentTimeMillis();
		
		for (Profile candidate : candidates)
		{
			for(FingerprintTemplate template : candidate.prints.getIterableOfPrints())
			{
				double score = matcher.match(template);
				if (score > high)
				{
					high = score;
					match = candidate;
				}
			}
		}
		double threshold = 40;
		Profile possibleMatch = (high >= threshold) ? match : null;
		
		double stopTime = System.currentTimeMillis();
		double timeResult = stopTime - startTime;
		
		if (possibleMatch != null)
		{
			HashMap<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("ident", true);
			tempMap.put("message", "Profile found within ident threshold.");
			tempMap.put("time", Double.toString(timeResult));
			tempMap.putAll(mapifyProfile(possibleMatch));
			Map<String, Object> iHateInterfaces = tempMap;
			return iHateInterfaces;
		}
		else
		{
			HashMap<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("ident", false);
			tempMap.put("message", "No profile found within ident threshold.");
			Map<String, Object> iHateInterfaces = tempMap;
			return iHateInterfaces;
		}
	}
	
	/**
	 * @param toBeMapified
	 * @return
	 */
	private static Map<String, Object> mapifyProfile(Profile toBeMapified) 
	{
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("id", toBeMapified.id);
		tempMap.put("name", toBeMapified.name);
		tempMap.put("address", toBeMapified.address);
		tempMap.put("city", toBeMapified.city);
		tempMap.put("state", toBeMapified.state);
		tempMap.put("zip", toBeMapified.zip);
		tempMap.put("phone", toBeMapified.phone);
		tempMap.put("ssid", toBeMapified.ssid);
		
		Map<String, Object> iHateInterfaces = tempMap;
		return iHateInterfaces;
	}
}
