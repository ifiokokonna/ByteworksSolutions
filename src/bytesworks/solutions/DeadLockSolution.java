/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bytesworks.solutions;
 
/**
 *
 * @author Ifiok Okonna
 * 
 * By Reordering the pattern of accessing the two resources and 
 * adding a little wait time, We are able to resolve the deadlock problem
 * 
 * The following program illustrates one of the solution to a deadlock scenario
 * 
 */
public class DeadLockSolution {

    final String lockResource1 = "1";
    final String lockResource2 = "2";
    
    public DeadLockSolution(){}
    
    public Thread thread1 = new Thread(){
        
        @Override
        public void run(){
            while(true){
                //Thread 1 Locks to Resource 1
                synchronized(lockResource1){

                    System.out.println("Thread 1 Locked on LockResource1");

                    System.out.println("Thread 1 waiting to Lock on Resource2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    //While Locked to Resource 1 Thread 1 Locks to Resource 2
                    synchronized(lockResource2){
                        System.out.println("Thread 1 Locked on LockResource2");
                    }
                        
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }   
            }
        }
        
        
    };
    
    public Thread thread2 = new Thread(){
         
        @Override
        public void run(){
            while(true){
                //Thread 1 Locks to Resource 1
                synchronized(lockResource1){

                    System.out.println("Thread 2 Locked on LockResource1");

                    System.out.println("Thread 2 waiting to Lock on Resource2");
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    //While Locked to Resource 1 Thread 1 Locks to Resource 2
                    synchronized(lockResource2){
                        System.out.println("Thread 2 Locked on LockResource2");
                    }

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }                
            }
        }
           
    };
}
