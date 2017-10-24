/*
 * Brief Description: N numbers in a expression with 2 operators + x
 * Find sum of result of all possible expressions
*/
import static java.lang.System.out;
import java.util.Scanner;

// PLUSMUL, Smackdown 2017 online elimination round. dp
// https://discuss.codechef.com/questions/100802/how-to-solve-the-question-plusmul-from-snackdown-elimination-round
class AddMultiply {
    int a[];
    static final int MOD=1000000007;
    int n;
    AddMultiply()
    {
        n=sc.nextInt(); // 1 ≤ n ≤ 100000
        a=ria(n, sc);
    }
    AddMultiply(int a[])
    {
        this.a=a;
        n=a.length;
    }
    
    void solve()
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
                long ans1 = (prod * (1<<m2) * (1<<m1))%MOD;
                //out.println("p="+prod+" a "+ans1);
                ans = (ans+ans1)%MOD;
            }
        }
        out.println(ans);
    }
    
    static void test()
    {
        new AddMultiply(new int[]{1,2,4}).solve();
        new AddMultiply(new int[]{1,1,1,1}).solve();
        new AddMultiply(new int[]{2,3,5,7}).solve();
        new AddMultiply(new int[]{200000000,300000000,500000000,700000000}).solve();
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
        //test();
        int T=sc.nextInt(); // 1 ≤ T ≤ 500
        while (T-->0)
            new AddMultiply().solve();
    }
}
