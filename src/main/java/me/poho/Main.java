package me.poho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Main {

    @SuppressWarnings("all")
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter time limit in millis: ");
        long timeMillis = in.nextLong();
        System.out.println("Enter file name (without .txt) : ");
        String filename = in.next();
        System.out.println("Enter primality checking method (Miller-Rabin or Trial Division): ");
        String mode = in.next();
        PrimalityCheckingMethod checkingMethod = switch (mode) {
            case "miller-rabin", "Miller-Rabin", "miller_rabin", "Miller Rabin" -> PrimalityCheckingMethod.MILLER_RABIN;
            default -> PrimalityCheckingMethod.TRIAL_DIVISION;
        };
        int iterationCount = 0;
        if (checkingMethod == PrimalityCheckingMethod.MILLER_RABIN){
            System.out.println("Enter how many times to iterate: ");
            iterationCount = in.nextInt();
        }else;
        in.close();
        long t = System.currentTimeMillis();
        try{
            generatePrimes(timeMillis, filename, checkingMethod, iterationCount);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println("Task complete! (File saved as " + filename + ".txt) Time elapsed: " + (System.currentTimeMillis()-t));
    }

    //Miller-Rabin Test
    public static boolean isPrimeMillerRabin(long n, int k) {
        // Edge cases
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        // Write n as d * 2^r + 1
        long d = n - 1;
        int r = 0;
        while (d % 2 == 0) {
            d /= 2;
            r++;
        }

        // Witness loop
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            long a = 2 + (Math.abs(rand.nextLong()) % (n - 4));
            long x = powerMod(a, d, n);

            if (x == 1 || x == n - 1) continue;

            boolean composite = true;
            for (int j = 0; j < r - 1; j++) {
                x = powerMod(x, 2, n);
                if (x == n - 1) {
                    composite = false;
                    break;
                }
            }

            if (composite) return false;
        }

        return true;
    }
    // Function to perform modular exponentiation
    private static long powerMod(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * base) % mod;
            exp >>= 1;
            base = (base * base) % mod;
        }
        return result;
    }

    public static boolean isPrimeTrialDivision(long n) {
        // Handle edge cases
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        // Check for divisors up to the square root of n
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    public static void generatePrimes(long timeMillis, String filename, PrimalityCheckingMethod method, int iterationCount) throws IOException {
        long timeStart = System.currentTimeMillis();
        File f = new File(filename + ".txt");
        if (f.createNewFile()){
            FileWriter writer = new FileWriter(f);
            long n = 1;
            while (System.currentTimeMillis() - timeStart < timeMillis){
                n++;
                switch (method){
                    case MILLER_RABIN -> {if (isPrimeMillerRabin(n, iterationCount))writer.write(n + "\n");}
                    case TRIAL_DIVISION -> {if (isPrimeTrialDivision(n))writer.write(n + "\n");}
                }
            }
            writer.close();
        }else{
            System.out.println("File already taken!");
        }
    }

}
