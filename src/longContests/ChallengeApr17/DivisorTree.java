package longContests.ChallengeApr17;


import codechef.Divisors;
import codechef.SegmentedSieve;
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// Easy marked in Editorial, but it is in the Medium section of Practice
class DivisorTree {
    
    static int tree(Divisors div)
    {
        if (div.numDiv()<=2) {
            return div.numDiv();
        }
        
        List<Divisors> divs = div.computeNext();
        int max=3;
        for (int i=0; i<divs.size(); i++) {   
            int m = tree(divs.get(i));
            if (m>max)
                max = m;
        }
        //out.println(max+"+"+div.numDiv());
        return max+div.numDiv();
    }
    static void bruteForce(long A, long B)
    {
        int score=0;
        for (long i=A; i<=B; i++) {
            Divisors div = new Divisors(i);
            int s = tree(div);
            if (s>2)
                s--;
            score += s;
        }
        out.println(score);        
    }
    DivisorTree(long A, long B)
    {
        SegmentedSieve ss = new SegmentedSieve(A,B);
        ss.sieve();
        PriorityQueue<Integer> pq = new PriorityQueue<>(500, SegmentedSieve.cmp);
        long total=0;
        for (long i=A; i<=B; i++) {
            ss.pfCount(i, pq);
            total += ss.DivisorTreeScore(pq);
            pq.clear();
        }
        out.println(total);
    }
    static void perfTest()
    {
        long a=100000000000L;
        long b = a+100000;
        Instant start = Instant.now();
        SegmentedSieve.test(a, b);  // 84 msec
        Instant end = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, end));   
        Divisors.test(a, b);  // 5.7 sec
        Instant end2 = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(end, end2));   
        
    }
    static void validateSegmented()
    {
        long a=100000000000L;
        long b = a+100000;
        validateSegmented(1, 10);
        validateSegmented(a, b);
    }
    static void validateSegmented(long a, long b)
    {
        SegmentedSieve ss = new SegmentedSieve(a,b);
        ss.sieve();
        PriorityQueue<Integer> pq = new PriorityQueue<>(500, SegmentedSieve.cmp);
        for (long i=a; i<=b; i++) {
            ss.pfCount(i, pq);
            long d1 = ss.numDiv(pq);
            //out.println(i+":"+pq+" "+d1);
            pq.clear();
            Divisors div = new Divisors(i);
            long d2 = div.numDiv();
            //div.printFactors();
            if (d1 !=d2 ) {
                out.println(i+":"+d1+" "+d2);
            }
        }
        //ss.print();
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {        
        long A = sc.nextLong();  // between 1 and B
        long B = sc.nextLong();   // 1 ≤ B ≤ 10^12, B-A<=10^5
        new DivisorTree(A, B);
    }
}
