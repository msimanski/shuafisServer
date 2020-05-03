package com.mfsimanski.shuafisserver;

import java.util.ArrayList;

import com.machinezoo.sourceafis.FingerprintTemplate;

/**
 * @author michaelsimanski
 * Class representing collection of prints for a Profile object. Has associated helper methods.
 */
public class Prints
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
	
	public FingerprintTemplate leftIndex;
	public FingerprintTemplate leftLittle;
	public FingerprintTemplate leftMiddle;
	public FingerprintTemplate leftRing;
	public FingerprintTemplate leftThumb;
	
	public FingerprintTemplate rightIndex;
	public FingerprintTemplate rightLittle;
	public FingerprintTemplate rightMiddle;
	public FingerprintTemplate rightRing;
	public FingerprintTemplate rightThumb;
	
	
	/**
	 * Generates an iterable of the prints from the caller instance.
	 * @return An FingerprintTemplate iterable of the prints from the caller instance.
	 */
	public Iterable<FingerprintTemplate> getIterableOfPrints()
	{
		// Create a temporary list to put the prints in
		ArrayList<FingerprintTemplate> temp = new ArrayList<FingerprintTemplate>();
		
		// Add the prints to the temp
		temp.add(this.leftIndex);
		temp.add(this.leftLittle);
		temp.add(this.leftMiddle);
		temp.add(this.leftRing);
		temp.add(this.leftThumb);
		temp.add(this.rightIndex);
		temp.add(this.rightLittle);
		temp.add(this.rightMiddle);
		temp.add(this.rightRing);
		temp.add(this.rightThumb);
		
		// Utilize the iterable interface
		Iterable<FingerprintTemplate> dummy = temp;
		
		// Return the prints iterable
		return dummy;
	}
}
