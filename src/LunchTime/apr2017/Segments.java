package LunchTime.apr2017;


import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


class Segments {
    
    static void maxRangeSum(long A[], long P)
    {
        out.println(Arrays.toString(A));
        int N=A.length;
        long sum=0;
        long ans=0;
        long count=0;
        for (int i=0; i<N; i++) {
            sum += A[i];
            sum = sum%P;
            if (sum>ans)
            {
                ans=sum;
                count=1;
            }
            else if (sum==ans)
                count++;
        }
        out.println(ans+" "+count);
    }
    
    static void maxSeg(long A[], long P, long ans)
    {
        int N=A.length;
        long count=0;
        for (int i=0; i<N;i++) {
            long sum=0;
            for (int j=i; j<N; j++) {
                sum += A[j];
                sum %= P;
                if (sum>ans)
                {
                    ans=sum;
                    count=1;
                }
                else if (sum==ans)
                    count++;                
            }
        }
        out.println(ans+" "+count);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        int TC = sc.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt(); // 1 ≤ N ≤ 10^5
            long P = sc.nextLong(); // 1 ≤ P ≤ 10^9
            long A[] = new long[N]; // 0 ≤ Ai ≤ 10^9
            long m=0;
            for (int j=0; j<N; j++) {
                A[j] = sc.nextLong();
                A[j] %= P;
                if ( m<A[j])
                    m=A[j];
            }
            maxSeg(A, P, m);
        }
    }
}
