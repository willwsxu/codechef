package CookOff0417;


import static java.lang.System.out;
import java.util.Scanner;

/*
 * XOR sum of all segments of an integer sequence
 * Medium, segment tree
 */


class XorSum {
    
    static void solve(int A[])
    {
        int xor=0;
        int N=A.length;
        for (int i=0; i<N; i++) {
            int count = (i+1) * (N-i);
            if (count %2>0)
                xor ^= A[i];
        }
        out.println(xor);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
        int N = sc.nextInt();  // 1 ≤ N ≤ 300,000
        int A[] = new int[N];
        for (int i=0; i<N; i++) {
            A[i] = sc.nextInt();   // 0 ≤ ai ≤ 50
        }
        solve(A);
    }
}
