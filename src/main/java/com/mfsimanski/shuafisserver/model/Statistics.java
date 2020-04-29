package com.mfsimanski.shuafisserver.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statistics")
public class Statistics
{
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	public int indexedProfiles;
	public int totalQueries;
	public int totalIdentQueries;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getIndexedProfiles()
	{
		return indexedProfiles;
	}
	public void setIndexedProfiles(int indexedProfiles)
	{
		this.indexedProfiles = indexedProfiles;
	}
	public int getTotalQueries()
	{
		return totalQueries;
	}
	public void setTotalQueries(int totalQueries)
	{
		this.totalQueries = totalQueries;
	}
	public int getTotalIdentQueries()
	{
		return totalIdentQueries;
	}
	public void setTotalIdentQueries(int totalIdentQueries)
	{
		this.totalIdentQueries = totalIdentQueries;
	}
}
