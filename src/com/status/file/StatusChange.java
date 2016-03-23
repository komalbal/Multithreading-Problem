/**
 * This thread is to change the status of every new file.
 * The static arraylist is used to change the status.
 * Logger object is used to store  the output in log file.
 */

package com.status.file;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

class StatusChange implements Runnable
{
	static Logger log=Logger.getLogger(StatusChange.class.getName());
	public Files f=new Files();
	DBManipulation db=new DBManipulation();
	
	public void run() 
	{
		int i;
		
		while(true)
		{
			try
			{
				
				db.readstatus();
				Thread.sleep(1000);

				System.out.println("Size: "+DBManipulation.list.size());
				
				while(!DBManipulation.list.isEmpty())
				{
					f=db.getFile();
					//Thread.sleep(100);
					if(f!=null)
					{
					if(f.status==0)
					{						
						try
						{
							//System.out.println(f.filename+" is NEW");
							log.info(f.filename+" IS NEW");
							f.status=1;
							//System.out.println(f.filename+" is IN PROGRESS");
							log.info(f.filename+" is IN PROGRESS :-" +Thread.currentThread().getName());
							processFile(f);  //Appending String to file
							//Thread.sleep(1000);
										
							f.status=3;
							//System.out.println(f.filename+" is COMPLETED");
							log.info(f.filename+" is COMPLETED");
						}
						catch(Exception e)
						{
							f.status=4;
							Thread.sleep(3000);
							
						}
						
						System.out.println("StatusChange:: "+f.id+"            "+f.status);;
			
						//	Changing the status of the thread which is changed in ArrayList
						db.changeStatus(f);
						Thread.sleep(1000);
					}
					}
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
	//}
	}
	
	/**
	 * This method writes the data to file.
	 * If the file is already present in system then it appends the string to the file.
	 * The string is common to all files.
	 * The path of the file is stored in database and retrieved from DB. 
	 * @param f
	 */
	public void processFile(Files f)
	{
		File f1;
		String  input="Java Multithreading Assignment";	
		
		try
		{
			//f.wait(1000L);
			f1=new File(f.filename);
			/*if(f1.exists()!=true)
			{
				f1.createNewFile();
			}*/
							
			FileWriter fw=new FileWriter(f1,true);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write("\n"+input);
			bw.close();
			fw.close();
			//f.notifyAll();
		 	//}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

}
