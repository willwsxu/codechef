package codeforces.edu31;


import codechef.IOR;
import static java.lang.System.out;


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

