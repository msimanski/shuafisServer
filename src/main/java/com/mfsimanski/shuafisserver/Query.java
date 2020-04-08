package com.mfsimanski.shuafisserver;

import org.json.JSONObject;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

public class Query
{
	public static boolean compareNToN(int threshold, byte[] probeImage, byte[] canidateImage, boolean[] fingers) 
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
		
		double score = new FingerprintMatcher().index(probe).match(candidate);
		
		if (score >= threshold)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public static JSONObject compareNToN(int threshold, byte[] probeImage, byte[] canidateImage) 
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
		
		JSONObject results = new JSONObject();
		
		long startTime = System.currentTimeMillis();
		double score = new FingerprintMatcher().index(probe).match(candidate);
		long stopTime = System.currentTimeMillis();
		long timeResult = stopTime - startTime;
		
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
		
		return results;
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
