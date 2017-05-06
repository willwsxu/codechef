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

// Median of adjacent maximum numbers, MXMEDIAN, beginner
class AdjMaxMedian {
    
    // sorted array, swap to pair top half to bottom half
    static void swap(int[] a, int low, int hi)
    {
        int newLow=hi;
        for (; low<newLow; low +=2, hi++) {
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
        
        A=new int[]{0, 1, 3, 3, 3, 2, 2};
        solve(A);
        
        A=new int[]{0, 1, 2};
        solve(A);
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {        
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
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
