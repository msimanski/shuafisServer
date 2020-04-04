package com.mfsimanski.shuafisserver;

import java.io.IOException;
import java.util.ArrayList;

public class Profile
{
	int id;
	String name;
	String address;
	String city;
	String state;
	String zip;
	String phone;
	String ssid;
	Prints prints;
	
	public static void loadPrintsToMemory(ArrayList<Profile> canidates, ArrayList<String> pathArrayList, boolean cache, boolean loadFromCache) throws IOException 
	{
		int index = 0;
		
		for (int i = 0; i < canidates.size(); i++)
		{
			System.out.println("[INFO]: Loading profile #" + (i +1));
			
			if (loadFromCache) 
			{
				canidates.get(i).prints.loadTemplatesFromCache(index, pathArrayList);
			}
			else
			{
				canidates.get(i).prints.loadTemplatesFromImage(index, pathArrayList, cache);
			}
			
			index += 10;
		}
	}
	
	public static void initializeProfiles(ArrayList<Profile> canidates) 
	{
		for (int i = 0; i < 600; i++)
		{
			canidates.add(new Profile());
			canidates.get(i).prints = new Prints();
		}
	}
}
