
import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
// CHEFFA, easy medium?, dynamic programming

class FibonacciArray {
    List<List<Integer>> fib1=new ArrayList<>();
    int index=0;
    int len=0;
    long total=0;
    static final int MOD=1000000007;
    
    FibonacciArray(int N)
    {
        dp(ria(N));
    }
    FibonacciArray()
    {
        
    }
    void bruteforce(int arr[])
    {
        int s=arr.length>1?arr[1]:0;
        int t=arr.length>2?arr[2]:0;
        out.println(solve(0, arr[0],s,t,arr));        
    }
    static final int MX=221;
    static final int offset=51;
    int dp[][][]=new int[MX][MX][MX];
    void dp(int arr[])
    {
        for (int i=0; i<dp.length; i++)
            for (int j=0; j<dp[0].length; j++)
                Arrays.fill(dp[i][j],-1);
        out.println(dpSolve(arr, 0, 0, 0));
    }
    int dpSolve(int arr[], int pos, int d, int dNxt)
    {
        if (pos>arr.length && d==0 && dNxt==0)
            return 1;
        int num1=pos<arr.length?arr[pos]+d:d;
        int num2=pos<arr.length-1?arr[pos+1]+dNxt:dNxt;
        int loops=min(num1, num2);
        //out.println("pos="+pos+" d="+d+" dNext="+dNxt+" loops="+loops);
        if (loops<0)
            return 0;
        int ans=dp[pos][d+offset][dNxt+offset];
        if (ans!=-1)  // already computed
            return ans;
        ans=0;
        for (int j=0; j<=loops; j++) {
            ans += dpSolve(arr, pos+1, dNxt-j, j);
            ans %= MOD;
        }
        dp[pos][d+offset][dNxt+offset]=ans;
        return ans;
    }

    static long solve(int index, int first, int second, int third, int arr[]) {
        if (first==0 && second==0 && third==0) {
            //out.println("index "+index);
            return 1;
        }
        int newThird=0;
        if (index+3<arr.length)
            newThird = arr[index+3];
        //out.println("index="+index+" #1="+first+" #2="+second+" #3="+third);
        long sum= solve(index+1, second, third, newThird, arr);
        while (first>0 && second>0) {
            first--;
            second--;
            third++;
            sum += solve(index+1, second, third, newThird, arr);
        }
        //out.println("index="+index+" sum="+sum);
        return sum;
    }
    static void test2() {
        out.println(solve(0, 1,0,0,new int[]{1}));
        out.println(solve(0, 1,1,0,new int[]{1,1}));
        out.println(solve(0, 2,3,0,new int[]{2,3}));
        out.println(solve(0, 3,4,0,new int[]{3,4}));
        out.println(solve(0, 4,4,4,new int[]{4,4,4}));
        out.println(solve(0, 2,3,1,new int[]{2,3,1}));
        out.println(solve(0, 0, 1,2,new int[]{0,1,2,3}));
        out.println(solve(0, 2,2,0,new int[]{2,2}));
    }
    static void test3()
    {
        int a[]=new int[51];
        Arrays.fill(a,0);
        a[0]=2;
        new FibonacciArray().dp(a);  
        a[1]=2;
        new FibonacciArray().dp(a);  
        a[0]=4;a[1]=4;a[2]=4;
        new FibonacciArray().dp(a);  
        a[0]=2;a[1]=3;a[2]=1;
        new FibonacciArray().dp(a);     
        for (int i=0; i<50; i++)
            a[i]=50;
        new FibonacciArray().dp(a);     //896236066
    }
        
    public static List<Integer> riL(int N) { // read int array list
        List<Integer> L=new ArrayList<>();
        for (int i=0; i<N; i++)
            L.add(sc.nextInt());
        return L;
    }
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);
    static void judge()
    {
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) { 
            int n=sc.nextInt();// array size
            new FibonacciArray().dp(ria(n));    
        }        
    }
    public static void main(String[] args)
    {    
        judge();
    }
}
