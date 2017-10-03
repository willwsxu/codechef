package codeforces.r408;


import codechef.IntPair;
import codechef.MyScanner;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// N cups on position 1 to N
// m holes in any of the position
// swap two cups k times, with the bone, but does not move over other positions
// if bone fall through at any moment, it stays there
// bone is at position 1 initially. report position of bone after swap.
// Need fast IO to pass test without TLE
// IDEA: need to track position of the bone is under a cup during swap
//       check if there hole when bone landed in new position
//       swap can be ignored if there is no bone under
public class FindBone796B {
    
    static MyScanner sc = new MyScanner();
    int position[];
    FindBone796B(Set<Integer> holes, IntPair[] m, int N )
    {
        position=new int[N+1];
        for (int h : holes)
            position[h]=1;  // mark hole as value 1
        solve(m, N);
    }
    boolean isHole(int pos)
    {
        return position[pos]==1;//holes.contains(pos);
    }
    void solve(IntPair[] m, int N)
    {
        if ( isHole(1) ) {
            out.println(1);
            return;
        }
        int last=1;
        for (int i=0; i<m.length; i++) {
            int other=0;
            if (m[i].int1()==last)  {
                other = m[i].int2();
            } else if (m[i].int2()==last)  {
                other = m[i].int1();
            }
            else // no bones
                continue;
            last = other;
            if (isHole(other)) {
                out.println(other);
                return;
            }
        }
        out.println(last);
    }
    FindBone796B()
    {
        int N = sc.ni();  // 2 ≤ n ≤ 10^6, 1 ≤ m ≤ n, 1 ≤ k ≤ 3·10^5
        int m = sc.ni();  // hole
        int k = sc.ni();  // swaps
        position=new int[N+1];
        for (int i=0; i<m; i++)
            position[sc.ni()]=1;
        IntPair[] move=new IntPair[k];
        for (int i=0; i<k; i++) {
            int c1 = sc.ni();
            int c2 = sc.ni();
            move[i]=new IntPair(c1,c2);
        }
        solve( move, N);
    }
    public static void test()
    {
        Set<Integer> holes = new HashSet<>(Arrays.asList(new Integer[]{19, 28, 39, 82, 99, 929384, 8298, 892849, 202020, 777777, 123123}));
        IntPair[] m=new IntPair[9];
        m[0]=new IntPair(19,28);
        m[1]=new IntPair(28,39);
        m[2]=new IntPair(1, 123124);
        m[3]=new IntPair(39,28);
        m[4]=new IntPair(28,99);
        m[5]=new IntPair(99,8298);
        m[6]=new IntPair(123124,123122);
        m[7]=new IntPair(2300,3200);
        m[8]=new IntPair(8298,1000000);
        new FindBone796B(holes, m, 1000000);
    }
    public static void main(String[] args)
    {        
        new FindBone796B();
    }
}
/*
5 1 5
2
2 3
3 4
2 1
4 5
*/