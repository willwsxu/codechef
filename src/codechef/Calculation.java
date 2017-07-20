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
    
    // return first position whose value>=k
    public static int lowerBound(int[] a, long k) {
        int n = a.length;
        if (a[n-1] < k)
            return n;
        int l = -1, r = n - 1;
        while (r - l > 1) {
            int mid = (l + r) >> 1;
            if (a[mid] >= k) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return r;
    }   
    
    // return position who value>k
    public static int upperBound(int[] a, int start, long k) {
        int n = a.length;
        if (a[n-1] <= k)
            return n;
        int l = start, r = n - 1;
        while (r - l > 1) {
            int mid = (l + r) >> 1;
            if (a[mid] > k) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return r;
    }  
    
    // not efficient when there are many elements of same value, TLE for this project
    static int lowerBound2(int[] L, int k) {
        int p3=Arrays.binarySearch(L, k);
        if (p3<0) {
            p3 = -(p3+1);
        }
        while (p3>0) {
            if (L[p3-1]==k)
                p3--;
            else
                break;
        }
        return p3;
    }    
    
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
    
    public static long safe_multi(long p, long m)
    {
        if (m==0)
            return 0;
        if (Long.MAX_VALUE/m<p)
            return Long.MAX_VALUE;
        return p*m;
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
    static void testFindCurr(int[] a, int k)
    {
        int c1=lowerBound(a, k);
        int c2=lowerBound2(a, k);
        out.println("find k="+k+":"+c1+"=="+c2);        
    }
    static void test4()
    {
        int[] a=new int[]{21, 5, 8,8,8, 10};   
        Arrays.sort(a);
        out.println(Arrays.toString(a));     
        testFindCurr(a, 22);
        testFindCurr(a, 21);
        testFindCurr(a, 20);
        testFindCurr(a, 8);
        testFindCurr(a, 6);
        testFindCurr(a, 5);
        testFindCurr(a, 1);
    }
}
