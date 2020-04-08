package com.mfsimanski.shuafisserver;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

public class Query
{	
	public static Map<String, String> compareNToN(int threshold, byte[] probeImage, byte[] canidateImage) 
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
		
		HashMap<String, String> results = new HashMap<String,String>();
		
		double startTime = System.currentTimeMillis();
		double score = new FingerprintMatcher().index(probe).match(candidate);
		double stopTime = System.currentTimeMillis();
		double timeResult = stopTime - startTime;
		
		if (score >= threshold)
		{
			results.put("ident", "true");
		}
		else 
		{
			results.put("ident", "false");
		}
		results.put("score", Double.toString(score));
		results.put("time", Double.toString(timeResult));
		
		Map<String, String> iHateInterfaces = results;
		return iHateInterfaces;
	}

	public static Profile compareOneToN(FingerprintTemplate probe, Iterable<Profile> candidates)
	{
		FingerprintMatcher matcher = new FingerprintMatcher().index(probe);
		Profile match = null;
		double high = 0;
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
		return high >= threshold ? match : null;
	}
}
