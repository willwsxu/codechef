package LunchMar17;


import static java.lang.Integer.max;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

public class LunchMar17Haircut {
    
    int A, B; // best looking between days A and B
    BitSet special;
    int[]   dp;
    // borrow from editorial
    int solve()
    {
        for (int i=0; i<dp.length; i++) {
            if (i+1<dp.length)
                dp[i+1] = max(dp[i+1], dp[i]);
            int good=0;
            for (int j=A; j<=B && i+j+1<dp.length; ++j) {
                if (special.get(i+j))
                    good++;
                dp[i+j+1] = max(dp[i+j+1], dp[i]+good);
                out.println("J="+j+Arrays.toString(dp));
            }
            out.println("i="+i);
        }
        out.println(Arrays.toString(dp));
        return dp[dp.length-1];
    }
    LunchMar17Haircut(BitSet s, int max, int A, int B)
    {
        special = s;
        dp = new int[max+2];
        this.A = A;
        this.B = B;
        out.println(solve());
    }
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 6
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 to 150,000
            int A = scan.nextInt();  // between 1 and 10^9
            int B = scan.nextInt();  // between 1 and 10^9, B>=A
            BitSet special = new BitSet(1000000000);
            int maxDay=0;
            for (int j=0; j<N; j++) {
                int day = scan.nextInt();
                special.set(day);
                if ( day>maxDay)
                    maxDay = day;
            }
            new LunchMar17Haircut(special, maxDay, A, B);
        }
    }
    public static void main(String[] args)
    {
        autoTest(); 
    } 
}
