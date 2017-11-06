/* Description, April 2017 long challenge
   Given 10 integers a0 to a9. An integer x is said to be bad if any of its digit i
   show up exactly ai times.
   Find all good numbers between L and R
*/
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// DGTCNT medium, https://discuss.codechef.com/questions/95667/dgtcnt-editorial
// inclusion-exclusion principle, dp - bitmask and digit dp
class Apr17DigitCount extends io {
    
    int A[];    //0 ≤ ai ≤ 18
    long L, R;  // 1 ≤ L ≤ R ≤ 10^18
    int digits[]=new int[10];
    // count digits
    boolean digitsCheck(long num)
    {
        Arrays.fill(digits, 0);
        while (num>0) {
            digits[(int)(num%10)]++;
            num /=10;
        }
        for (int i=0; i<10; i++)
            if (A[i]==digits[i])
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
    
    final int MAX_DIGITS=18;
    final int MAX_MASK=1<<10;
    long dp[][][]=new long[MAX_DIGITS][MAX_MASK][2];
    long dp(int d, int tight, int mask, int []ndigit)
    {
        if (mask == MAX_MASK-1) {
            return pow10(ndigit.length-d);
        }
        if (d>=ndigit.length) {
            return 0;
        }
        int k=9;
        if (tight>0) {
            k=ndigit[d];
        }
        long total=0;
        for (int i=0; i<=k; i++) {
            int newtight=(i==k)?tight:0;
            int newMask=mask;
            if (i>0 || mask>0)
                newMask=mask|(1<<i);
            total +=dp(d+1, newtight, newMask, ndigit);
        }
        return total;
    }
    
    int[] digits(long n) {
        String s=Long.toString(n);
        int []v=new int[s.length()];
        for (int i=0; i<s.length();i++)
            v[i]=s.charAt(i)-'0';
        return v;
    }
    // all digits must appreat at least once
    long subtask2(long L, long R)
    {
        if (R<1023456789) // 1023456798 1023456879 1023456897 1023456978 1023456987 1023457689
            return 0;//9 81 18 81 9 702
        int [] d=digits(R);
        out.println(Arrays.toString(d));
        long cnt1=dp(0, 1, 0, d);
        out.println(cnt1);
        d=digits(L-1);
        long cnt2=dp(0, 1, 0, d);
        return cnt1-cnt2;
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
    
    public static void test()
    {
        int []m2=new int[]{0,0,0,0,0,0,0,0,0,0};
        new Apr17DigitCount(233, 1023456798, m2).solve();
        new Apr17DigitCount(21, 28, new int[]{5, 4, 3, 2, 1, 1, 2, 3, 4, 5}).solve();
        new Apr17DigitCount(233, 23333, new int[]{2, 3, 3, 3, 3, 2, 3, 3, 3, 3}).solve();
    }
    
    public static void main(String[] args)
    {   
        test();
    }
     
    public static void judge()
    {
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