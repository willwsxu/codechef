
import static java.lang.System.out;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class LongSandwich {
    
    static long recurse(long N, long count, long K)
    {
        out.println("N "+N+" count "+count+" K="+K);
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
    static long small(int N, int K, int M)  // dynamic programming, for small case
    {
        long count = (N+K-1)/K;  // ceiling of multiples
        return recurse(N, count, K);        
    }
    static void test()
    {
        out.println(small(7, 3, 500));
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
            
        }*/
    }
}
