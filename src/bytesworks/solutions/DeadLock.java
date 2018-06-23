/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bytesworks.solutions;
  
/**
 *
 * @author Ifiok Okonna
 * Deadlock: A Situation where two or more threads are trying to access a variable
 * that has been locked by either of them
 * 
 * The following program illustrates a Deadlock situation
 * 
 */
public class DeadLock {
    final String lockResource1 = "1";
    final String lockResource2 = "2";
    
    public DeadLock(){}
    
    public Thread thread1 = new Thread(){
        
        @Override
        public void run(){
            while(true)
                synchronized(lockResource1){

                    System.out.println("Thread 1 Locked on LockResource1");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    System.out.println("Thread 1 waiting to Lock on Resource2");
                    synchronized(lockResource2){
                        System.out.println("Thread 1 Locked on LockResource2");
                    }

                }
            
        }
        
        
    };
    
    public Thread thread2 = new Thread(){
         
        @Override
        public void run(){
            while(true)
                synchronized(lockResource2){

                    System.out.println("Thread 2 Locked on LockResource2");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    System.out.println("Thread 2 waiting to Lock on Resource1");
                    synchronized(lockResource1){
                        System.out.println("Thread 2 Locked on LockResource1");
                    }

                }
            
        }
           
    };
    
    
}


