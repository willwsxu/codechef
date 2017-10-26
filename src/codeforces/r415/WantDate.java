package codeforces.r415;


import codechef.Calculation;
import codechef.IOR;
import codechef.Power2;
import static java.lang.System.out;

/*
 * N points on a line, coordinate 1 ≤ xi ≤ 10^9, 1 ≤ n ≤ 3·10^5
 * a is none empty subset of A - all points
 * F(a) = max distance of any two points in a
 * Find sum of F(a) of all possible subsets
 */
// e.g N=3, X=1 3 4
// dist 1-3: subsets 1 3, 1 4, 1 3 4  
// dist 3-4: subsets      1 4, 1 3 4, 3 4
// IDEA: only calc distanc eof neibouring points after sorting, multiply how many times it below to a subset
//contest r415, 809A
public class WantDate {
    
    static final int  MOD=1000000007;    // 10^9 + 7

    WantDate(int x[])
    {
        x = Calculation.reverse(x);
        //out.println(Arrays.toString(x));
        long total=0;
        for (int i=0; i<x.length-1; i++) {
            long dist=x[i]-x[i+1];
            dist = (dist * (Power2.get(i+1)-1))%MOD;
            dist = (dist * (Power2.get(x.length-i-1)-1))%MOD;
            total = (total+dist)%MOD;
        }
        out.println(total);        
    }
    static void test()
    {
        new WantDate(new int[]{4, 7}); // 3
        new WantDate(new int[]{400000000,100000000,300000000});  //900000000
        new WantDate(new int[]{400000000,100000000,300000000,1});       // 999999979
        new WantDate(new int[]{1, 2, 3, 4, 5}); // 66
        new WantDate(new int[]{100000000, 200000000, 300000000, 400000000, 500000000}); //599999958
    }

    public static void main(String[] args)
    {      
        new WantDate(IOR.ria(IOR.ni()));
    }
}
