package LunchTime.June17;


import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.Scanner;

// simple math
class TwoNumbers {
    
    static int solve(int A, int B, int N)
    {
        if (N%2>0)
            A *=2;
        return max(A,B)/min(A,B);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T = sc.nextInt();  // 1 ≤ T ≤ 100
        while (T-->0)
            out.println(solve(sc.nextInt(), sc.nextInt(), sc.nextInt()));        
    }
}
