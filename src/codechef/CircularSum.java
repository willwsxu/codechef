
package codechef;

import static java.lang.System.out;
import java.util.Arrays;


// calculate sum of k elements of array size n
// double the array size, calculate prefix sum from right
// pos A2x[n] will be used as first sum, same as A2x[0], so it can be shifted left
// window frame is pos, pos+n-k, both inclusive
public class CircularSum
{
    int []A2x;  // sum array, double size of original
    int pos, n, k;
    public CircularSum(int A[], int k)
    {
        n=A.length;
        this.k=k;
        int n2=2*n;
        if (k>n)
            k=n;
        A2x=new int[n2];
        for (int i=0; i<n; i++) {
            A2x[i]=A[i];
            A2x[i+n]=A[i];
        }
        for (int i=n2-2; i>=0; i--) { // prefix sum from right
            A2x[i]+=A2x[i+1];
            if (i+k<n2)
                A2x[i] -= A[(i+k)%n];  // subtract last number fell out of window frame
        }
        pos=n; // start at n
    }
    public int[] getA2x()
    {
        return A2x;
    }
    
    public static void test()
    {
        CircularSum cir=new CircularSum(new int[]{1,0,1,0,1}, 1);
        out.println(Arrays.toString(cir.A2x));
        CircularSum cir2=new CircularSum(new int[]{1,0,1,0,1}, 3);
        out.println(Arrays.toString(cir2.A2x));
    }
    
    public void shiftR() // 2n-1 dropped off, n-1 is added
    {
        pos--;
        if (pos<0)
            pos=n-1;
    }
    public int getHead() {
        return pos;
    }
    public int getTail() {
        return pos+n-k;
    }
}
