package com.mfsimanski.shuafisserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mfsimanski.shuafisserver.service.FilesStorageService;

@SpringBootApplication
public class SpringBootUploadFilesApplication implements CommandLineRunner
{
	@Resource
	FilesStorageService storageService;

	static ArrayList<Profile> canidates;

	public static void main(String[] args)
	{
		loadPrints(false, true);
//		Iterable<Profile> temp = canidates;
//		System.out.println(Query.compareOneToN(canidates.get(0).prints.leftIndex, temp));
		SpringApplication.run(SpringBootUploadFilesApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception
	{
		// storageService.deleteAll();
		storageService.init();
	}

	public static void loadPrints(boolean cache, boolean loadFromCache)
	{
		canidates = new ArrayList<Profile>();
		Profile.initializeProfiles(canidates);
		ArrayList<String> pathArrayList = new ArrayList<String>();

		// Before starting the server, load the prints into memory
		System.out.println("[INFO]: Loading prints into memory. This will take a long time.");

		try (Stream<Path> paths = Files
				.walk(Paths.get("/Users/michaelsimanski/eclipse-workspace/shuafisServer/prints/")))
		{
			paths.filter(Files::isRegularFile).forEach(path ->
			{
				pathArrayList.add(path.toString());
			});
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pathArrayList.sort(String::compareToIgnoreCase);
		try
		{
			Profile.loadPrintsToMemory(canidates, pathArrayList, cache, loadFromCache);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
