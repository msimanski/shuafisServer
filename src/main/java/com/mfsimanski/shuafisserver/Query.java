package com.mfsimanski.shuafisserver;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

public class Query
{
	public static boolean findNToN(int threshold, byte[] probeImage, byte[] canidateImage, boolean[] fingers) 
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

	public static Profile findOneToN(FingerprintTemplate probe, Iterable<Profile> candidates)
	{
		FingerprintMatcher matcher = new FingerprintMatcher().index(probe);
		Profile match = null;
		double high = 0;
		for (Profile candidate : candidates)
		{
			double score = matcher.match(candidate.template);
			if (score > high)
			{
				high = score;
				match = candidate;
			}
		}
		double threshold = 40;
		return high >= threshold ? match : null;
	}
}