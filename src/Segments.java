// package LunchTime.apr2017;
/*
score of a segment (consecutive subsequence) as its sum of elements modulo P (not necessarily prime). 
Find the maximum score of a non-empty segment, and also find the number of segments with this maximum score
Optimization technique using Modulo property & partial sum:
calculate partail sum from left to right, use TreeMap to keep a sorted list of modulo values
We only need to find if there is any sub segments which has higher modulo, sub segments modulo can be calculated as
sub = (Si-Sj+P)%P, Si is the current partial sum, Sj is all previous values stored in tree map, 0<=j<i
sub can only be higher if Sj>Si, furthermore, max sub would be the least of Sj (which is greater than Si)

**TreeMap higherEntry is nicely suited for this purpose
*/

import static java.lang.System.out;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// BEARSEG easy? (or medium as it requires special optimization technique)
class Segments {
     
    static long[] prefixSum(int a[])  // set first elem to 0
    {
        long s[]=new long[a.length+1];
        s[0]=0;
        for (int i=1; i<=a.length; i++)
            s[i] = s[i-1]+a[i-1];
        return s;
    }
    int a[];
    int p;
    Segments(int A[], int P)
    {
        a=A; p=P;
    }
    void quick()
    {
        int ps=0;
        long countMaxSum =0, maxSum = 0;
        TreeMap<Integer, Integer> segments= new TreeMap<>();
        segments.put(0,1);// add default modulo 0, used for longest segemnt i
        for (int i=0; i<a.length; i++)
        {
            ps=(ps+a[i])%p;
            Map.Entry<Integer, Integer> h=segments.higherEntry(ps);
            if (h==null)
                h=segments.firstEntry();  // entry with modulo 0, ps-0=ps;
            // calculate max modulo of any subsegments
            int newMax = (ps-h.getKey()+p)%p;  // don't change ps
            if (maxSum<newMax) {
                maxSum = newMax;
                countMaxSum=h.getValue();
            } else if (newMax==maxSum) {
                countMaxSum += h.getValue();  // sub segemnts from i to h
            }
            segments.compute(ps, (k, v)->v==null?1:v+1);
        }
        //out.println("segments "+segments);
        out.println(maxSum+" "+countMaxSum);        
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
    static void test1(int a[], int p)
    {
        new Segments(a, p).partialSumMax();
        new Segments(a, p).quick();        
    }
    static void test()
    {
        test1(new int[]{1,2}, 3);
        test1(new int[]{2, 4, 3}, 5);
        test1(new int[]{1, 3, 5}, 100);
        test1(new int[]{1, 2, 3, 4}, 3);       
        test1(new int[]{2, 2, 4, 8}, 2);    
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        int TC = sc.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt(); // 1 ≤ N ≤ 10^5
            int P = sc.nextInt(); // 1 ≤ P ≤ 10^9
            int A[] = new int[N]; // 0 ≤ Ai ≤ 10^9
            long m=0;
            for (int j=0; j<N; j++) {
                A[j] = sc.nextInt();
                A[j] %= P;
                if ( m<A[j])
                    m=A[j];
            }
            //partialSumMax(A, P, m);
            new Segments(A, P).quick();
        }
    }
}
