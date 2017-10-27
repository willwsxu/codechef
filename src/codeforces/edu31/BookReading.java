package codeforces.edu31;


import codechef.IOR;
import static java.lang.System.out;


public class BookReading {
    
    BookReading(int t, int[]days)
    {
        int count=0;
        for (int i=0; i<days.length; i++) {
            if (t<=0)
                break;
            count++;
            t -= (86400-days[i]);
        }
        out.println(count);
    }
    public static  void test()
    {
        new BookReading(2, new int[]{86400,86398});
        new BookReading(86400, new int[]{0,86400});
    }
    public static  void judge()
    {
        int n=IOR.ni();
        new BookReading(IOR.ni(), IOR.ria(n));
    }
    public static void main(String[] args)
    {      
        judge();
    }
}
