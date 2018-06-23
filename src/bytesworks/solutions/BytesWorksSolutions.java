/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bytesworks.solutions;

import java.util.Scanner;

/**
 *
 * @author Ifiok Okonna
 */
public class BytesWorksSolutions {

    /**
     * @param args the command line arguments
     */
    static int myint = 0;
    public static void main(String[] args) {
        // TODO code application logic here
        
        menu();

        
    }
    private static void menu()
    {
        System.out.println("BYTEWORKS SOLUTIONS");
        System.out.println("");
        System.out.println("1. Deadlock Scenario");
        System.out.println("2. Deadlock Scenario Solution");
        System.out.println("3. Print 1 Million Prime Numbers");
        System.out.println("4. Start Crawler");
        System.out.println("5. Exit");
        System.out.println("");
        
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a Number");
        myint = keyboard.nextInt();
        
        switch(myint){
            case 1: 
                DeadLock deadLock = new DeadLock();
                deadLock.thread1.start();
                deadLock.thread2.start();
                break;
            case 2: 
                DeadLockSolution deadLocksolution = new DeadLockSolution();
                deadLocksolution.thread1.start();
                deadLocksolution.thread2.start();
                break;
            case 3: 
                PrimeNumbers prime = new PrimeNumbers();
                break;
            case 4: 
                Crawler crawler = new Crawler(1000);
                crawler.fireCrawler();
                break;
            case 5: 
                System.out.println("Exiting Program...");
                break;
            default:
                menu();
                break;
        }
    }
    
}
