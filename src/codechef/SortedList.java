/*

 */
package codechef;

import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


// ArrayList is 25x faster than linkedlist for sorted list
// ArrayList is also faster than int[] impl, up to 3 times
public class SortedList
{
    private List<Long> ls;
    Comparator<Long> cmp;
    public SortedList(boolean reverseOrder, int capacity)
    {
        if (reverseOrder)
            cmp = Comparator.reverseOrder();
        else
            cmp = Comparator.naturalOrder();
        ls = new ArrayList<>(capacity);
    }
    public boolean add(long e)
    {
        int i=Collections.binarySearch(ls, e, cmp);
        if (i<0)
            i=-(i+1);
        ls.add(i, e);
        return true;
    }
    public long peek()
    {
        return ls.get(0);
    }
    
    public boolean remove(long e)
    {
        int i=Collections.binarySearch(ls, e, cmp);
        if (i>=0) {
            ls.remove(i);
            return true;
        }
        return false;
    }
    public boolean removeAll(int count) {
        while (count-->0) {
            ls.remove(0);
        }
        return true;
    }
    public long get(int i) {
        return ls.get(i);
    }
    public int size() {
        return ls.size();
    }
    public static void test()
    {
        SortedList sll = new SortedList(true, 100000);
        sll.add(10);
        sll.add(20);
        sll.add(30);
        sll.add(5);
        sll.add(15);
        System.out.println(sll);
        System.out.println(sll.peek());
        sll.remove(new Integer(30));
        System.out.println(sll.peek());
        sll.add(4);
        sll.remove(0);
        System.out.println(sll);
    }
    public static void perfTest(int N)
    {
        Instant start = Instant.now();
        SortedList sll = new SortedList(true, 100000);
        for (int i=0; i<N; i++) {
            sll.add(i%(N/3)+1);
        }        
        Instant mid = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, mid));
        // when N=100000, LinkedList 11 sec, ArrayList .45 sec, PriorityQueue 15msec
        
        for (int i=0; i<N; i++) {
            int a=i%(N/7)+1;
            int d=i%(N/3)+1;
            sll.add(a);
            sll.remove(d);
        }
        Instant end = Instant.now();   
        out.println("usec "+ChronoUnit.MICROS.between(mid, end));   
        // when N=100000, LinkedList 83 sec, ArrayList 1.3 sec, PriorityQueue 3.6 sec
    }
}

