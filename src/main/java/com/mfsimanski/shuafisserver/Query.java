package com.mfsimanski.shuafisserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.mfsimanski.shuafisserver.model.Profile;

/**
 * @author michaelsimanski
 * Class consisting of helper methods that perform queries.
 */
public class Query
{	
	/**
	 * Perform a 1:1 query.
	 * @param threshold Threshold by which prints are considered ident.
	 * @param probeImage Probe image in byte form.
	 * @param canidateImage Candidate image in byte form.
	 * @return Results mapping payload.
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
		
		// Decode the byte arrays and save them to new templates
		FingerprintTemplate probe = new FingerprintTemplate(new FingerprintImage().dpi(500).decode(probeImage));
		FingerprintTemplate candidate = new FingerprintTemplate(new FingerprintImage().dpi(500).decode(canidateImage));
		
		// Temp variable for the results
		HashMap<String, Object> results = new HashMap<String, Object>();
		
		// Score the comparison and record the elapsed time
		double startTime = System.currentTimeMillis();
		double score = new FingerprintMatcher().index(probe).match(candidate);
		double stopTime = System.currentTimeMillis();
		double timeResult = stopTime - startTime;
		
		// Process the payload
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
		
		// Utilize the interface and return
		Map<String, Object> iHateInterfaces = results;
		return iHateInterfaces;
	}

	/**
	 * Perform a 1:N query.
	 * @param probeRaw Byte array of the probe image.
	 * @param candidates List of candidates to search through.
	 * @return A results payload mapping.
	 */
	public static Map<String, Object> compareOneToN(byte[] probeRaw, ArrayList<Profile> candidates)
	{	
		// Decode the probe image and initialize a matcher
		FingerprintTemplate probe = new FingerprintTemplate(new FingerprintImage().dpi(500).decode(probeRaw));
		FingerprintMatcher matcher = new FingerprintMatcher().index(probe);
		Profile match = null;
		double high = 0;
		
		// Time the process
		double startTime = System.currentTimeMillis();
		
		// For each candidate profile, look for the best match
		for (Profile candidate : candidates)
		{	// For each print in that profile
			for(FingerprintTemplate template : candidate.prints.getIterableOfPrints())
			{
				// Find the best match
				double score = matcher.match(template);
				if (score > high)
				{
					high = score;
					match = candidate;
				}
			}
		}
		// If the best meets the threshold, keep it. Otherwise make it null
		double threshold = 40;
		Profile possibleMatch = (high >= threshold) ? match : null;
		
		// End timing
		double stopTime = System.currentTimeMillis();
		double timeResult = stopTime - startTime;
		
		// Process the payload
		if (possibleMatch != null)
		{
			HashMap<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("ident", true);
			tempMap.put("message", "Profile found within ident threshold.");
			tempMap.put("time", Double.toString(timeResult));
			tempMap.putAll(toMap(possibleMatch));
			Map<String, Object> inter = tempMap;
			return inter;
		}
		else
		{
			HashMap<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("ident", false);
			tempMap.put("message", "No profile found within ident threshold.");
			Map<String, Object> inter = tempMap;
			return inter;
		}
	}
	
	/**
	 * Convert a profile into a JSON-like map.
	 * @param toBeMapified The profile to be converted to a map.
	 * @return The resulting map.
	 */
	private static Map<String, Object> toMap(Profile toBeMapified) 
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
		
		Map<String, Object> inter = tempMap;
		return inter;
	}
}
