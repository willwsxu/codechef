package smackdown.qualifier2017.elimination;

/*
 * Brief Description: N numbers in a expression with 2 operators + x
 * Find sum of result of all possible expressions
*/
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

class Power2
{
    static final int  MOD=1000000007;    // 10^9 + 7

    private static int power2[]=new int[300000+5];
    static
    {
        power2[0]=1;
        for (int i=1; i< power2.length; i++) {
            power2[i] = (2 * power2[i-1])%MOD;
        }
        //out.println(Arrays.toString(power2));
    }
    public static int get(int i) {
        return power2[i];
    }
}
// PLUSMUL, Smackdown 2017 online elimination round. dp
// Use Thread class to provide a large call stack for recursion
// https://discuss.codechef.com/questions/100802/how-to-solve-the-question-plusmul-from-snackdown-elimination-round
class AddMultiply implements Runnable {
    int a[];
    static final int MOD=1000000007;
    int n;
    long dp_f[];
    long dp_g[];
    long dp_h[];
    AddMultiply(int n)
    {
        this(ria(n, sc));
    }
    AddMultiply(int a[])
    {
        this.a=a;
        n=a.length;
        dp_f=new long[n];
        dp_g=new long[n];
        dp_h=new long[n];
        Arrays.fill(dp_f, -1);
        Arrays.fill(dp_g, -1);
        Arrays.fill(dp_h, -1);
        dp_f[0]=a[0];
        dp_g[0]=a[0];
        dp_h[0]=a[0];
    }
    
    // f(i)=g(i-1)+h(i)
    long f(int i)
    {
        if (dp_f[i]>=0)
            return dp_f[i];
        dp_f[i]=(g(i-1)+h(i))%MOD;
        return dp_f[i];
    }
    // g(i)=g(i-1)+f(i)
    long g(int i)
    {
        if (dp_g[i]>=0)
            return dp_g[i];
        dp_g[i]=(g(i-1)+f(i))%MOD;
        return dp_g[i];
    }
    // h(i)=h(i-1)xai + (2^i-1)ai
    long h(int i)
    {
        if (dp_h[i]>=0)
            return dp_h[i];
        long v1=h(i-1)*a[i];
        long v2=(long)Power2.get(i-1)*a[i];  // watch out for overflow of int *int
        dp_h[i] = (v1%MOD+v2%MOD)%MOD;
        return dp_h[i];
    }
    
    // pattern 
    // 1+2+3+4
    // 1+2+3x4
    // 1+2x3+4
    // 1+2x3x4
    // 1x2+3+4
    // 1x2+3x4
    // 1x2x3+4
    // 1x2x3x4
    // algorithm to finish up multiply first, working down the numbers
    // each outter loop would reduce number by 1 from left, and 
    // i=0, inner: j=0, m2=2, add up 4 1s; j=1, m2=1, add up 2 1x2; j=2, add 1x2x3; j=3, add 1x2x3x4
    // i=1, inner: j=1, m2=1, add up 2 2s; j=2, add up 2x3;         j=3, add up 2x3x4
    // i=2, inner: j=2, m1=1, add up 2 3s (one at top half, one at bottom half);    j=3, add 2 3x4
    // i=3, inner: j=3, m1=2, add up 4 4s (one from each quarter)
    long solve()  //O(n^2) TLE
    {
        long ans=0;
        for (int i=0; i<a.length; i++) {
            int m1=i>0?i-1:0;
            long prod=1;
            for (int j=i; j<a.length; j++) {
                prod =(prod*a[j])%MOD;
                int m2=n-2-j;
                if (m2<0)
                    m2=0;
                long ans1 = (prod * Power2.get(m2))%MOD;
                ans1 = (ans1 * Power2.get(m1))%MOD;
                //out.println("j="+j+" p="+prod+" a "+ans1);
                ans = (ans+ans1)%MOD;
            }
        }
        return ans;
    }
    long dp()
    {
        return f(n-1);
    }
    public void run()
    {
        out.println(f(n-1));
    }
    
    static void test()
    {
        out.println(new AddMultiply(new int[]{1,2,4}).solve()==30);
        out.println(new AddMultiply(new int[]{1,1,1,1}).solve()==20);
        out.println(new AddMultiply(new int[]{2,3,5,7}).solve()==494);
        out.println(new AddMultiply(new int[]{200000000,300000000,500000000,700000000}).solve()==822000021);
        out.println(new AddMultiply(new int[]{1,2,4}).dp()==30);
        out.println(new AddMultiply(new int[]{1,1,1,1}).dp()==20);
        out.println(new AddMultiply(new int[]{2,3,5,7}).dp()==494);
        out.println(new AddMultiply(new int[]{200000000,300000000,500000000,700000000}).dp()==822000021);
    }
    static void testbig()
    {
        int a[]=new int[100000];
        for (int i=0; i<a.length; i++)
            a[i]=i+1;
        threadSetup(new AddMultiply(a));
        threadSetup(new AddMultiply(new int[]{200000000,300000000,500000000,700000000}));
    }
      
    public static int[] ria(int N, Scanner sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        judge();
    }
    static void threadSetup( AddMultiply am)
    {
        Thread t =new Thread(null, am, "whatever", 1<<28);
        t.start();
        try {
            t.join();
        } catch (Exception e)
        {

        }        
    }
    static void judge()
    {
        int T=sc.nextInt(); // 1 ≤ T ≤ 500
        while (T-->0) {
            int n=sc.nextInt(); // 1 ≤ n ≤ 100000
            if (n<1000)
                out.println(new AddMultiply(n).dp());
            else
                threadSetup(new AddMultiply(n));
        }
    }
}
