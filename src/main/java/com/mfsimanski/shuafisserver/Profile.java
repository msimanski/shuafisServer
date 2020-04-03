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
	
	public static void loadPrintsToMemory(ArrayList<Profile> canidates, ArrayList<String> pathArrayList) throws IOException 
	{
		int index = 0;
		
		for (int i = 0; i < canidates.size(); i++)
		{
			System.out.println("[INFO]: Loading profile #" + (i +1));
			canidates.get(i).prints.loadTemplatesToMemory(index, pathArrayList);
			index += 10;
		}
	}
	
	public static void initializeProfiles(ArrayList<Profile> canidates) 
	{
		for (int i = 0; i < 10; i++)
		{
			canidates.add(new Profile());
			canidates.get(i).prints = new Prints();
		}
	}
}
