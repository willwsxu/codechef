package longContests.may17;

/* You are given an array A of size 2 * N consisting of positive integers, where N is an odd number. 
You can construct an array B from A as follows, B[i] = max(A[2 * i - 1], A[2 * i]), i.e. B array contains 
the maximum of adjacent pairs of array A. Assume that we use 1-based indexing throughout the problem.

You want to maximize the median of the array B. For achieving that, you are allowed to permute the 
entries of A. Find out the maximum median of corresponding B array that you can get. Also, find out 
any permutation for which this maximum is achieved.
*/

import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;
// Greedy, sort and take second half of A as B
// Median of adjacent maximum numbers, MXMEDIAN, easy
class AdjMaxMedian {
    
    // sorted array, recursive swap to pair top half to bottom half
    // 0,1,2,3,4,5,6,7,8,9,10 low=2, hi=6
    // 0,1,6,3,7,5,2,4,8,9,10 (swap 2 withm N+1, 4, N+2, etc.), compute next low and hi 6 and 8
    // 0,1,6,3,7,5,8,4,2,9,10 next lo hi 8, 9
    // 0,1,6,3,7,5,8,4,9,2,10
    static void swap(int[] a, int low, int hi)
    {
        int oldHi=hi; 
        for (; low<oldHi; low +=2, hi++) {  // don't pass oldHi
            int temp=a[low];
            a[low]=a[hi];
            a[hi]=temp;
        }
        if (low<hi)
            swap(a, low, hi);
    }
    
    static void solve(int A[])
    {        
        int N=A.length/2;
        Arrays.sort(A);
        // median is middle of second half (N to 2N)
        // N is odd, N+N/2+1 is the middle number (5), 0 1 2 3 4 5 6
        // N is even, N+N/2+1 is the midlle to rigtht(7), 0 1 2 3 4 5 6 7 8
        out.println(A[N+N/2+1]);
        swap(A, 2, N+1);
        StringBuilder sb=new StringBuilder();
        for (int i=1; i<A.length; i++) {
            sb.append(A[i]);
            if (i<A.length-1)
                sb.append(" ");
        }
        out.println(sb.toString());
    }
    static void test()
    {
        int A[]=new int[11];
        for (int i=0; i<A.length; i++)
            A[i]=i;
        swap(A, 2, 6);
        out.println(Arrays.toString(A));
        for (int i=0; i<A.length; i++)
            A[i]=i;
        codechef.Calculation.swap(A, 2, 6);
        out.println(Arrays.toString(A));
        for (int i=0; i<A.length; i++)
            A[i]=i;
        codechef.Calculation.swap(A, 1, 6);
        out.println(Arrays.toString(A));
        
        A=new int[]{0, 1, 3, 3, 3, 2, 2};
        solve(A);
        
        A=new int[]{0, 1, 2};
        solve(A);
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {        
        int TC = sc.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int N=sc.nextInt();  // 1 ≤ N ≤ 50000, N is odd
            int A[]=new int[2*N+1];// 1 ≤ Ai ≤ 2 * N
            for (int j=1; j<A.length; j++)  // 1 based
                A[j]=sc.nextInt();
            solve(A);
        }
    }
}
