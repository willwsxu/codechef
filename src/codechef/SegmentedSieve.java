
package codechef;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


// count prime factors, use it to computer divisors, no need to know which prime
public class SegmentedSieve
{
    static PrimeSieve sv = new PrimeSieve(1000005);
    List<Long>[] pfs;
    private long A, B;
    private boolean checkRange()
    {
        return A>=1 && B>=A && B-A<=1000005 && B <= 1000000000000L;
    }
    public SegmentedSieve(long A, long B) // 1 ≤ B ≤ 10^12, B-A<=10^5
    {
        this.A=A;
        this.B=B;
        if ( !checkRange() )
            out.println("Error invalid range");
        pfs = new List[(int)(B-A+1)];
        for (int i=0; i<pfs.length; i++) {
            pfs[i] = new ArrayList<>();
        }
    }
    
    long ceiling(long a, int b) // return number divisible by b and >=a
    {
        return (a+b-1)/b*b;
    }
    public void sieve()
    {
        for (int pf: sv.primes())
        {
            if (pf*pf>B)
                break;
            for (long x=ceiling(A, pf); x<=B; x+=pf)
            {
                pfs[(int)(x-A)].add((long)pf);
            }
        }
    }
    void print()
    {
        for (int i=0; i<pfs.length; i++)
            out.println(pfs[i]);
    }
    
    public void pfCount(long a, PriorityQueue<Integer> pq)
    {
        if (a<A || a>B) {
            out.println("Error pfCount invalid range");
        }
        int target = (int)(a-A);
        //out.println(pfs[target]);
        for (int i=0; i<pfs[target].size(); i++) {
            long f = pfs[target].get(i);
            int count=0;
            while (a>0 && a%f==0) {
                count++;
                a /= f;
            }
            if (count>0)
                pq.add(count);
            else
                out.println("Error no factor "+f);
        }
        if (a>1) {
            pq.add(1);
            pfs[target].add(a);
        }
        //out.println(a);
    }
    
    // number of divisors
    public static long numDiv(PriorityQueue<Integer> pq)
    {
        long div=1;
        for (long p: pq)
            div *= (p+1);     
        return div;
    }
    
    public static long DivisorTreeScore(PriorityQueue<Integer> pq)
    {        
        long numDiv=0;
        while(!pq.isEmpty()) {
            numDiv += numDiv(pq);
            int big=pq.poll();
            if (big>1)  // subtract 1 from the biggest, add back if not zero
                pq.add(big-1);
        }
        return numDiv;
    }
    public static final Comparator<Integer> cmp=Comparator.reverseOrder();
 
    public static void test(long A, long B)
    {
        SegmentedSieve ss = new SegmentedSieve(A,B);
        //out.println(sv.primes);
        ss.sieve();
        PriorityQueue<Integer> pq = new PriorityQueue<>(500, cmp);
        for (long i=A; i<=B; i++) {
            ss.pfCount(i, pq);
            out.println(i+":"+pq);
            pq.clear();
        }
        ss.print();
    }
}