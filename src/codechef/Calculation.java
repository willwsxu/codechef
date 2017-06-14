/*
 * common methods to pre calculate
 */
package codechef;

import static java.lang.System.out;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.stream.LongStream;

public class Calculation {
    
    // either x exist in a or not, return position i where ai>x
    static int upperBound(int a[], int from, int to, int x)
    {
        int i=Arrays.binarySearch(a, from, to, x);
        if (i<0)
            return -(i+1);
        while (i<to) {
            if ( a[i]==x)  // 
                i++;
            else
                break;
        }
        return i;
    }
    
    // swap(a, 2, N+1) swap second half of array as even
    // swap(a, 1, N+1) swap second half of array as odd
    public static void swap(int[] a, int low, int hi)
    {
        for (; low<hi; low +=2, hi++) {
            int temp=a[low];
            a[low]=a[hi];
            a[hi]=temp;
        }
    }
    public static long[] prefixSum(int a[])  // set first elem to 0
    {
        long s[]=new long[a.length+1];
        s[0]=0;
        for (int i=1; i<=a.length; i++)
            s[i] = s[i-1]+a[i-1];
        return s;
    }
    
    public static int[] reverse(int a[])  // sort int array reverse
    {
        return IntStream.of(a).boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(i->i).toArray();        
    }
    public static long[] reverse(long v[]){
        return LongStream.of(v).boxed()
                .sorted(Comparator.reverseOrder())
                .mapToLong(i->i).toArray();
    }
    
    static long aChooseb(int a, int b)
    {
        if (a<b)
            return 0; // error
        if (a-b<b)  // 5C3 = 5C2
            b=a-b;
        long p=1;
        int bb=b;
        for (int i=0; i<b; i++) {
            p *=(a-i);
            while (bb>1 && p%bb==0) {
                p /= bb--;
            }
        }
        while (bb>1 && p%bb==0) {
            p /= bb--;
        }
        return p;
    }
    static void testChoose()
    {
        out.println(aChooseb(5,1));
        out.println(aChooseb(5,2));
        out.println(aChooseb(5,3));
        out.println(aChooseb(5,4));
        out.println(aChooseb(5,5));
        out.println(aChooseb(30,15));
    }    
}
