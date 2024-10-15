package me.poho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter time limit in millis: ");
        long timeMillis = in.nextLong();
        System.out.println("Enter file name (without .txt) : ");
        String filename = in.next();
        in.close();
        try{
            generatePrimes(timeMillis, filename);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println("Task complete! (File saved as " + filename + ".txt)");
    }

    /*public static boolean isPrime(long n){
        // Handle small numbers and edge cases
        if (n <= 1) {
            return false; // 0 and 1 are not prime numbers
        }
        if (n == 2 || n == 3) {
            return true; // 2 and 3 are prime numbers
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false; // Eliminate multiples of 2 and 3
        }

        // Check for factors from 5 up to the square root of n
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false; // n is divisible by a factor
            }
        }

        return true; // If no factors are found, n is prime
    }*/

    public static boolean isPrime(BigInteger n){
        // Handle small numbers and edge cases
        if (n.compareTo(BigInteger.ONE) <= 0) return false; // 0 and 1 are not prime numbers
        if (n.compareTo(BigInteger.TWO) == 0 || n.compareTo(BigInteger.valueOf(3)) == 0) return true; // 2 and 3 are prime numbers
        if (n.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0 || n.mod(BigInteger.valueOf(3)).compareTo(BigInteger.ZERO) == 0) return false; // Eliminate multiples of 2 and 3

        // Check for factors from 5 up to the square root of n
        for (BigInteger i = BigInteger.valueOf(5); i.multiply(i).compareTo(n) <= 0; i = i.add(BigInteger.valueOf(6))) {
            if (n.mod(i).compareTo(BigInteger.ZERO) == 0 || n.mod(i.add(BigInteger.TWO)).compareTo(BigInteger.ZERO) == 0) return false; // n is divisible by a factor
        }

        return true; // If no factors are found, n is prime
    }

    public static void generatePrimes(long timeMillis, String filename) throws IOException {
        long timeStart = System.currentTimeMillis();
        File f = new File(filename + ".txt");
        if (f.createNewFile()){
            FileWriter writer = new FileWriter(f);
            BigInteger n = BigInteger.ONE;
            while (System.currentTimeMillis() - timeStart < timeMillis){
                n = n.add(BigInteger.ONE);
                if (isPrime(n))writer.write(n + "\n");
            }
            writer.close();
        }else{
            System.out.println("File already taken!");
        }
    }

}
