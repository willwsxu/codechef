
import static java.lang.System.out;
import java.util.Scanner;


public class CookOffMar17Xor {
    static void fillArray(int [] a)
    {
        for (int i=0; i<a.length; i++) {
            a[i] = scan.nextInt();  // between 0 and Integer.MAX_VALUE
        }
    }
    static final int MOD = 1000000007;
    
    int count(int seq[], int idx, int b) {
        if (idx+1==seq.length)
            return 1;
        int current = seq[idx]^b;
        int cnt=0;
        for (int j=b; j<=Integer.MAX_VALUE; j++)
        {
            int next = seq[idx+1]^j;
            if ( next>=current )
            cnt += count(seq, idx+1, j);
        }
        return cnt%MOD;
    }
    int count(int seq[])
    {
        int cnt=0;
        for (int b=0; b<=Integer.MAX_VALUE; b++)
            cnt += count(seq, 0, b);
        return cnt%MOD;
    }
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 100
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 to 5
            int seq[] = new int[N];
            fillArray(seq);
            out.println(new CookOffMar17Xor().count(seq));
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
