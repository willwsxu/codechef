package codechef;

import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// Avoid priority Queue remove method call as it is slow
// super fast, 5x better than SortedList class
// Priority Queue stores K elements of array, when item becomes invalid, mark b[] false
public class ListBackedPQ
{    
    int A[];
    boolean []b;
    Comparator<IntPair> cmp;
    PriorityQueue<IntPair> pq;
    public ListBackedPQ(int a[], int k, boolean reverseOrder)
    {
        if (reverseOrder)
            cmp = (r1,r2)->r2.int2()-r1.int2();
        else
            cmp = (r1,r2)->r1.int2()-r2.int2();
        pq = new PriorityQueue<>(a.length, cmp);
        A=a;
        b=new boolean[a.length];
        // first k are valid
        for (int i=0; i<k; i++) {
            pq.add(new IntPair(i, A[i]));
            b[i]=true;
        }
    }
    public void remove(int ind)
    {
        b[ind]=false;
    }
    public void add(int ind)
    {
        b[ind]=true;
        pq.add(new IntPair(ind, A[ind]));
    }
    public int peek()
    {
        IntPair p=pq.peek();
        while (!b[p.int1()]) {
            pq.poll();  // discard items no longer valid
            p=pq.peek();
        }
        return p.int2();
    }
    public static void perfTest(int N)
    {
        Instant start = Instant.now();
        int A[]=new int[N];
        for (int i=0; i<N; i++) {
            A[i]=i%(N/3)+1;
        }    
        ListBackedPQ pq=new ListBackedPQ(A, N-2, true);
        Instant mid = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, mid));
        // when N=100000, 20 msec
        out.println("size "+N+" pq size "+pq.pq.size());
        
        int K=N-2;
        for (int i=0; i<N; i++) {
            pq.remove(i);
            pq.add((K+i)%N);
        }
        Instant end = Instant.now();   
        out.println("usec "+ChronoUnit.MICROS.between(mid, end));   
        // when N=100000, 12 msec
        out.println("size "+N+" pq size "+pq.pq.size());
    }
    public void printSumA()
    {
        out.println(peek()+" "+Arrays.toString(A));
    }
    public String toString()
    {
        return pq.peek()+" "+pq.toString();        
    }
}
