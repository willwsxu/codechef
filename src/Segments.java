// package LunchTime.apr2017;
/*
score of a segment (consecutive subsequence) as its sum of elements modulo P (not necessarily prime). 
Find the maximum score of a non-empty segment, and also find the number of segments with this maximum score
*/

import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// BEARSEG easy? (should be medium)
class Segments {
     
    static long[] prefixSum(long a[])  // set first elem to 0
    {
        long s[]=new long[a.length+1];
        s[0]=0;
        for (int i=1; i<=a.length; i++)
            s[i] = s[i-1]+a[i-1];
        return s;
    }
    long a[];
    long p;
    Segments(long A[], long P)
    {
        a=A; p=P;
    }
    void partialSumMax()  // 2 partialSumMax are comparable, O(n^2)
    {
        long s[] = prefixSum(a);
        long countMaxSum =0, maxSum = 0;
        for (int left=0; left <a.length; left++)
        {            
            for (int right=left; right <a.length; right++) {
                long sum=(s[right+1]-s[left])%p;
                if (sum>maxSum)
                {
                    maxSum=sum;
                    countMaxSum=1;
                }
                else if (sum==maxSum)
                    countMaxSum++;         
            }
        }
        out.println(maxSum+" "+countMaxSum);
    }
    static void partialSumMax(long A[], long P, long ans)// smarter than brute force
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
            //partialSumMax(A, P, m);
            new Segments(A, P).partialSumMax();
        }
    }
}
