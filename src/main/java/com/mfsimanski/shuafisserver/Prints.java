package com.mfsimanski.shuafisserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintTemplate;

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
	
	FingerprintTemplate leftIndex;
	FingerprintTemplate leftLittle;
	FingerprintTemplate leftMiddle;
	FingerprintTemplate leftRing;
	FingerprintTemplate leftThumb;
	
	FingerprintTemplate rightIndex;
	FingerprintTemplate rightLittle;
	FingerprintTemplate rightMiddle;
	FingerprintTemplate rightRing;
	FingerprintTemplate rightThumb;
	
	public Iterable<FingerprintTemplate> getIterableOfPrints()
	{
		ArrayList<FingerprintTemplate> temp = new ArrayList<FingerprintTemplate>();
		
		temp.add(leftIndex);
		temp.add(leftLittle);
		temp.add(leftMiddle);
		temp.add(leftRing);
		temp.add(leftThumb);
		temp.add(rightIndex);
		temp.add(rightLittle);
		temp.add(rightMiddle);
		temp.add(rightRing);
		temp.add(rightThumb);
		
		Iterable<FingerprintTemplate> dummy = temp;
		
		return dummy;
	}
	
	public void loadTemplatesToMemory(int index, ArrayList<String> pathArrayList, boolean cache) throws IOException 
	{
		if (cache)
		{
			byte[] probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			FingerprintTemplate probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			// This snippet saves it to a compressed format for faster load times
			byte[] serialized = probe.toByteArray();
			String directory = Paths.get(pathArrayList.get(index)).getParent().toString();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.leftIndex = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.leftLittle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.leftMiddle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.leftRing = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.leftThumb = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.rightIndex = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.rightLittle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.rightMiddle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.rightRing = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));
			
			serialized = probe.toByteArray();
			Files.write(Paths.get(directory + "/cache/" + (index + 1) + ".json.gz"), serialized);

			this.rightThumb = probe;
			index++;
		}
		else 
		{
			byte[] probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			FingerprintTemplate probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.leftIndex = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.leftLittle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.leftMiddle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.leftRing = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.leftThumb = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.rightIndex = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.rightLittle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.rightMiddle = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.rightRing = probe;
			index++;
			
			probeImage = Files.readAllBytes(Paths.get(pathArrayList.get(index)));
			probe = new FingerprintTemplate(
				    new FingerprintImage()
				        .dpi(500)
				        .decode(probeImage));

			this.rightThumb = probe;
			index++;
		}
	}
}
