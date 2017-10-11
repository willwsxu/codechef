
import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// EASY, DP, bit mask, KNICOV Jun cook off
// find minmal knights needed to cover all m x n board
// IDEA
// 2 rows (N=2): easy to find packing pattern
// 3 rows: harder to find some tricky case for repeating pattern
class KnightCovering {
    int n,m;
    KnightCovering(int n, int m)
    {//1 ≤ N ≤ 3, 1 ≤ M ≤ 50
        this.n=n;   this.m=m;
        if (n==1)
            return;
    }
    int solve2r(int m)
    {
        if ( n!=2)
            return 0;
        int sixpack=m/6;
        m %=6;
        return sixpack*4 + 2*min(m, 2);   
    }
    int solve()
    {
        if ( m<n) {
            int temp=m;
            m=n;
            n=temp;
        }
        if (n<=1)
            return m;
        if (n==2)
            return solve2r(m);
        return solve3r(m);
    }
    int solve3r(int m)
    {
        int sixpack=m/6;
        m %=6;
        int ans = sixpack*4;
        if ( m==0)
            return ans;
        else if (m==1)
            return ans+2;
        else if (m==2 && sixpack>1)  // hard case to generate by hand
            return ans+3;
        else
            return ans+4;
    }
    
    static void test()
    {
        out.println(new KnightCovering(1, 1).solve()==1);
        out.println(new KnightCovering(1, 10).solve()==10);
        out.println(new KnightCovering(10, 1).solve()==10);
        out.println(new KnightCovering(2, 1).solve()==2);
        out.println(new KnightCovering(2, 3).solve()==4);
        out.println(new KnightCovering(2, 4).solve()==4);
        out.println(new KnightCovering(2, 5).solve()==4);
        out.println(new KnightCovering(2, 6).solve()==4);
        out.println(new KnightCovering(2, 7).solve()==6);
        out.println(new KnightCovering(2, 8).solve()==8);
        out.println(new KnightCovering(2, 9).solve()==8);
        out.println(new KnightCovering(3, 3).solve()==4);
        out.println(new KnightCovering(3, 2).solve()==4);
        out.println(new KnightCovering(3, 8).solve()==8);
        out.println(new KnightCovering(3, 14).solve()==11);
        out.println();
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {    
        judge();
    }
    static void judge()
    {
        int T = sc.nextInt();  // 1 ≤ T ≤ 150
        while (T-->0)
            out.println(new KnightCovering(sc.nextInt(), sc.nextInt()).solve());
    }
}
