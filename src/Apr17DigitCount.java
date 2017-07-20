/* Description
   Given 10 integers a0 to a9. An integer x is said to be bad if any of its digit i
   show up exactly ai times.
*/
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// DGTCNT medium, https://discuss.codechef.com/questions/95667/dgtcnt-editorial
// inclusion-exclusion principle, dp - bitmask and digit dp
class Apr17DigitCount extends io {
    
    int A[];
    long L, R;
    // count digits
    boolean digitsCheck(long num)
    {
        int a[]=new int[10];
        Arrays.fill(a, 0);
        while (num>0) {
            a[(int)(num%10)]++;
            num /=10;
        }
        for (int i=0; i<10; i++)
            if (A[i]==a[i])
                return false;  // bad if there is a match
        return true;
    }
    
    long bruteforce(long L, long R)
    {
        long good=0;
        for (long num=L; num<=R; num++)
            if (digitsCheck(num))
                good++;
        return good;        
    }
    
    long pow10(int exp) {
        long p=1;
        while (exp-->0)
            p *=10;
        return p;
    }
    long dp(int d, long tight, long n, int mask)
    {
        for (int i=0; i<10; i++) {
            
        }
        return 0;
    }
    
    // all digits must appreat at least once
    long subtask2(long L, long R)
    {
        if (R<1023456789) // 1023456798 1023456879 1023456897 1023456978 1023456987 1023457689
            return 0;//9 81 18 81 9 702
        long cnt=dp(0, 0, R, 0);
        return cnt;
    }
    Apr17DigitCount()
    {
        this(nl(), nl(), ria(10));
    }
    Apr17DigitCount(long L, long R, int A[])
    {   // 1 ≤ L ≤ R ≤ 10^18
        this.A=A; // digit count, 0 to 18
        this.L=L;
        this.R=R;
    }
    void solve()
    {
        int sum = 0;
        for (int s:A)
            sum += s;
        
        long good=0;
        if (sum==0)
            good = subtask2(L, R);
        else
            good = bruteforce(L, R);
        out.println(good);        
    }
    
    public static void main(String[] args)
    {        
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
        int TC = ni();  // between 1 and 20
        while (TC-- > 0)
            new Apr17DigitCount().solve();
    }
}

class io
{    
    static Scanner sc = new Scanner(System.in);    
        
    public static int ni()
    {
        return sc.nextInt();
    }
    public static long nl()
    {
        return sc.nextLong();
    }
    
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
}