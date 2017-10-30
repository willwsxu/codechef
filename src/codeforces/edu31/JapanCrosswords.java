package codeforces.edu31;


import codechef.IOR;
import static java.lang.System.out;

// 884B easy
// corssword 011010 is encoded as {2,1}, how many segments of 1s
// Given encoded int, and len of crossword
// Print YES if there exists exaclty one crossword with chosen length and encoding. Otherwise, print NO.
// count 1 from encoded, plus n-1 separator. YES if it is same as crossword length
public class JapanCrosswords {
    JapanCrosswords(int len, int encode[])
    {
        long sum=0;
        for (int e : encode)
            sum += e;
        if (encode.length>1)
            sum += (encode.length-1);
        out.println(sum!=len?"NO":"YES");
    }
    public static  void test()
    {
        new JapanCrosswords(4, new int[]{1,3});
        new JapanCrosswords(10, new int[]{3,3,2});
        new JapanCrosswords(10, new int[]{1,3});
    }
    public static  void judge()
    {
        int n=IOR.ni();
        new JapanCrosswords(IOR.ni(), IOR.ria(n));
    }
    public static void main(String[] args)
    {      
        judge();
    }
}

