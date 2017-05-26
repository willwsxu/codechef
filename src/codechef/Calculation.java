/*
 * common methods to pre calculate
 */
package codechef;

public class Calculation {
    
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
}
