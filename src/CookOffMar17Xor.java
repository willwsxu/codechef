
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


public class CookOffMar17Xor {
    static void fillArray(int [] a)
    {
        for (int i=0; i<a.length; i++) {
            a[i] = scan.nextInt();  // between 0 and Integer.MAX_VALUE
        }
    }
    static final int MOD = 1000000007;
    int [][][]dp;
    int count(int seq[], int msk1, int msk2, int bit) {
        if (bit == -1) return 1;
        if (dp[bit][msk1][msk2] != -1) return dp[bit][msk1][msk2];
        int res = 0;
        outer:
        for (int cur = 0; cur < 1 << seq.length; cur++) {// 2^n way to fill current B bit
            int nmsk1 = msk1, nmsk2 = msk2;
            for (int i = 0; i + 1 < seq.length; i++) {
                int b1 = (cur >> i) & 1, b2 = (cur >> (i + 1)) & 1;
                if (b1 == 1 && b2 == 0 && ((msk1 >> i) & 1) == 0) 
                    continue outer;  // b1>b2
                int a1 = ((seq[i] >> bit) & 1) ^ b1, a2 = ((seq[i + 1] >> bit) & 1) ^ b2;
                if (a1 == 1 && a2 == 0 && ((msk2 >> i) & 1) == 0) continue outer;

                if (b1 == 0 && b2 == 1) nmsk1 |= 1 << i; // mask 1 set if bi is increasing
                if (a1 == 0 && a2 == 1) nmsk2 |= 1 << i; // mask 2 set if bi ^ ai increase
            }
            res += count(seq, nmsk1, nmsk2, bit - 1);
            if (res >= MOD) res -= MOD;
        }
        //out.println("bit "+bit+" msk1 "+msk1+" msk2 "+msk2+" res "+res);
        return dp[bit][msk1][msk2] = res;
    }
    int solve(int seq[])
    {
        int masks = 1<<(seq.length-1);
        dp = new int[31][masks][masks];
        for (int[][] x : dp) for (int[] y : x) Arrays.fill(y, -1);
        
        return count(seq, 0, 0, 30);
    }
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 100
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 to 5
            int seq[] = new int[N];
            fillArray(seq);
            out.println(new CookOffMar17Xor().solve(seq));
        }
    }
    public static void main(String[] args)
    {
        scan = codechef.CodeChef.getFileScanner("xor0317.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
