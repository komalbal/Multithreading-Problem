package com.status.file;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DBManipulation 
{
	static List<Files> list=Collections.synchronizedList(new ArrayList<Files>());
	static Connection con = null;
	
	public static void getConnection()
	{
		//Properties object to retrieve data from properties file
		Properties dbProperties=new Properties();
		String driver,url,username,password;
		
		try
		{
			int i=0;
			//Thread.sleep(1000);
			/*synchronized(this)
			{*/
			FileInputStream dbin=new FileInputStream("db.properties");
			dbProperties.load(dbin);
			dbin.close();
		
			driver=dbProperties.getProperty("driver");
			url=dbProperties.getProperty("url");
			username=dbProperties.getProperty("username");
			password=dbProperties.getProperty("password");
			
			//System.out.println(driver);
			Class.forName(driver);
			con=DriverManager.getConnection(url,username,password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void readstatus()
	{
		getConnection();
		list.removeAll(list);
		try
		{
			Statement st=con.createStatement();
			//Selecting for the records having status 0
			ResultSet rs=st.executeQuery("select * from file where status=0");
			
			while(rs.next())
			{
				Files f=new Files();
				f.id=rs.getInt(1);				
				f.filename=rs.getString(2);
				f.status=rs.getInt(4);
				synchronized(list)
				{
					list.add(f);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void changeStatus(Files f)
	{
		getConnection();
		try
		{
			PreparedStatement ps=con.prepareStatement("update file set status=? where id=?");
			ps.setInt(1, f.status);
			ps.setInt(2, f.id);
			ps.execute();
		}
		catch(Exception e)
		{
			//log.info("No record in database");
			e.printStackTrace();
		}
	}
	
	public static synchronized Files getFile() throws InterruptedException
	{
		if(!list.isEmpty())
		{
			synchronized(list)
			{			
				list.wait(100L);
				Files f=(Files)list.iterator().next();
				System.out.println(f.id);
				list.remove(f);
				list.notifyAll();
				return f;
			}
		}
		else
			return null;
	}
}
