package codeforces.r408;


import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class FindBone796B {
    
    static Scanner scan = new Scanner(System.in);
    FindBone796B(Set<Integer> holes, IntPair[] m )
    {
        solve(holes, m);
    }
    void solve(Set<Integer> holes, IntPair[] m)
    {
        if ( holes.contains(1) ) {
            out.println(1);
            return;
        }
        int last=1;
        for (int i=0; i<m.length; i++) {
            int other=0;
            if (m[i].first==last)  {
                other = m[i].second;
            } else if (m[i].second==last)  {
                other = m[i].first;
            }
            else // no bones
                continue;
            last = other;
            if (holes.contains(other)) {
                out.println(other);
                return;
            }
        }
        out.println(last);
    }
    FindBone796B()
    {
        int N = scan.nextInt();  // 2 ≤ n ≤ 10^6, 1 ≤ m ≤ n, 1 ≤ k ≤ 3·10^5
        int m = scan.nextInt();  // hole
        int k = scan.nextInt();  // swaps
        Set<Integer> holes = new HashSet<>();
        for (int i=0; i<m; i++)
            holes.add(scan.nextInt());
        IntPair[] move=new IntPair[k];
        for (int i=0; i<k; i++) {
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            String[] swap = line.split(" ");
            int c1 = Integer.parseInt(swap[0]);
            int c2 = Integer.parseInt(swap[1]);
            move[i]=new IntPair(c1,c2);
        }
        solve(holes, move);
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
        new FindBone796B(holes, m);
    }
    public static void main(String[] args)
    {        
        //test();
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

class IntPair  // pair of int
{
    int first;
    int second;
    public IntPair(int f, int s)
    {
        first=f;
        second=s;
    }
    public int int1()
    {
        return first;
    }
    public int int2()
    {
        return second;
    }
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof IntPair) {
            IntPair other =(IntPair)s;
            return first==other.first && second==other.second;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(first*second);
    }
    @Override
    public String toString()
    {
        return first+":"+second;
    }
}