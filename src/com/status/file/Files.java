/**
 * This is bean class having id, filename and info as attributes.
 * This is used to retrieve data from database and store it.
 */

package com.status.file;

import java.io.FileInputStream;
import java.io.InputStream;

public class Files 
{
	int id;
	String filename;
	//InputStream file;
	int status;
	
	//Constructor to set all values to null
	Files()
	{
		id=0;
		filename=null;
		status=0;
	}
	
	//getters and setters for all attributes
	public int getId()
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getFilename() 
	{
		return filename;
	}
	
	public void setFilename(String file)
	{
		this.filename = file;
	}
	
	public int getStatus() 
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}

}
