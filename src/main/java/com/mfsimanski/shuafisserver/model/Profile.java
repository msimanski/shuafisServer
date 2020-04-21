package com.mfsimanski.shuafisserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mfsimanski.shuafisserver.Prints;

@Entity
@Table(name = "profile")
public class Profile
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	public String name;
	public String address;
	public String city;
	public String state;
	public String zip;
	public String phone;
	public String ssid;
	
	@Transient
	public Prints prints;
	
	public Profile(int id) 
	{
		this.id = id;
		this.name = "default";
		this.address = "default";
		this.city = "default";
		this.state = "default";
		this.zip = "default";
		this.phone = "default";
		this.ssid = "### ## ####";
	}

	public Profile()
	{
		this.name = "default";
		this.address = "default";
		this.city = "default";
		this.state = "default";
		this.zip = "default";
		this.phone = "default";
		this.ssid = "### ## ####";
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getSsid()
	{
		return ssid;
	}

	public void setSsid(String ssid)
	{
		this.ssid = ssid;
	}

	public Prints getPrints()
	{
		return prints;
	}

	public void setPrints(Prints prints)
	{
		this.prints = prints;
	}
}
