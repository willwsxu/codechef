package Cookoff.CookOffMar17;

import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;


// safe robot, ROBOTG easy (or beginner)
class CookOffMar17Robot {
        
    public String isSafe(int rows, int cols, String moves)
    {
        int[]mb = codechef.GridHelper.movingBox(moves);
        if ( mb[2]-mb[0]>=rows || mb[3]-mb[1]>=cols)
            return "unsafe";
        return "safe";
    }
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 10^3
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 to 10
            int M = scan.nextInt();
            String moves = scan.nextLine();
            if (moves.isEmpty())
                moves = scan.nextLine();
            out.println(new CookOffMar17Robot().isSafe(N, M, moves));
        }
    }
    public static void main(String[] args)
    {
        scan = codechef.ContestHelper.getFileScanner("robot0317.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
