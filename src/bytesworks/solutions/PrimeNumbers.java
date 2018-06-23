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
 * Prime Numbers: Numbers that can only divide itself and 1. 
 * Using multiple threads to process prime numbers generation will result in missing 
 * prime numbers and will not be serialized properly as I choose accuracy over speed.
 * If we force using multiple threads to process this numbers, we still have to put 
 * each of the threads on hold which will affect the performance of the system
 * 
 * The following program will generate any amount of prime numbers and will be
 * processed in batches for easy viewing and updating.
 * 
 */
public class PrimeNumbers {

    final int finalCount =  1000000;
    int interval =          1000;
    int intervalRange =     0;
    
    public PrimeNumbers() {
        System.out.println("Printing "+finalCount+" Prime Numbers...");
        getPrimeNumbers(0, interval);
    }
    private void getPrimeNumbers(final int start, final int count){
        Thread thread = new Thread(){
            int num = start, range = 0;
            @Override
            @SuppressWarnings("empty-statement")
            public void run(){
                int ret = 1;
                String output = "";
                while(range <= count){
                    if(isPrime(num)){
                        String r = (ret % 10)==0 ? "\n" : "";
                        output += "["+num+"] "+r;
                        ret++;
                        range++;
                    }
                    num++;
                }
                
                intervalRange += range;
                
                System.out.println(output);
                
                if(intervalRange <= finalCount)
                    getPrimeNumbers(intervalRange, count);
                
                System.out.println("Cummulative Count: " +intervalRange);
            }            
        };
        thread.start();
    }
    //Check if Its Prime Number
    private boolean isPrime(int n) {
        for(int i = 2; i <= n / 2; i++) 
            if (n % i == 0) 
                return false;
        return true;
    }
}
