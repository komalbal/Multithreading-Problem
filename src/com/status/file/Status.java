/**
 * This is main class executing all the threads.
 * First it starts the StatusList thread and the static Arraylist is updated.
 * The ExecutorService interface is used to execute all the threads.
 * The FixedThreadPool is set to 5.
 */

package com.status.file;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Status 
{	
	
	public static void main(String[] args) throws InterruptedException 
	{		
		/*Thread read=new Thread(new ReadStatus());
		read.start();*/
		
		ExecutorService exec=Executors.newFixedThreadPool(5);
		for(int i=0;i<5;i++)
		{
			Runnable t=new StatusChange();
			Thread.sleep(500);
			exec.execute(t);
		}
		exec.shutdown();
		
		/*Thread statusChange[]=new Thread[5];
		for(int i=0;i<5;i++)
		{
			statusChange[i]=new Thread(new StatusChange());
			Thread.sleep(2000);
			statusChange[i].start();
		}
		 */
		
		
	}

}