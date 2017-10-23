
import static java.lang.System.out;
import java.util.Scanner;

/* cut long sandwich N len into pieces, len of each piece is 1<=len<=K
   Output minimal # of pieces, how many ways to cut mod M
 */
// SANDWICH, medium, May 2017 long challenge
// formular: n+n∗K−N−1Cn−1, n is ceil(N/K)
// Chinese remainder theorem
class LongSandwich {
    
    static long recurse(long N, long count, long K)
    {
        //out.println("N "+N+" count "+count+" K="+K);
        if ( N<=K)
            return 1;
        if ( count*K==N)
            return 1;
        long extra=count*K-N;  // 7/3=2
        if (extra<0)
            return 0;
        long ans=0;
        for (long i=K-extra; i<=K; i++)
            ans += recurse(N-i, count-1, K);
        return ans;
    }
    static void small(long N, long K, int M)  // dynamic programming, for small case
    {
        long count = (N+K-1)/K;  // ceiling of multiples
        long ways= recurse(N, count, K)%M;        
        out.println(count +" "+ways);
    }
    static void test()
    {
        small(7, 3, 500);
        small(10, 2, 1000);
        small(45, 11, 1000);
        //small(1000000, 11, 1000);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        test();/*
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) {
            long N=sc.nextLong(); // 1 ≤ N ≤ 10^18
            long K=sc.nextLong(); // 1 ≤ K ≤ 10^18
            int M=sc.nextInt();     // 2 ≤ M ≤ 10^6
            small(N, K, M);
        }*/
    }
}
