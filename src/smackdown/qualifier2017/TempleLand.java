package smackdown.qualifier2017;


import codechef.ScannerEx;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Brief Description: N integers of height in a good pattern
 * From 1 go up to a unique center in increment of 1, then go down to 1
 */
// very easy
class TempleLand {
    
    static boolean check(int h[])
    {
        //out.println(Arrays.toString(h));
        if (h.length%2==0) {
            //out.println("even");
            return false;
        }
        for (int i=0; i<h.length/2; i++)
        {
            if (h[i+1]-h[i]!=1) {
                //out.println("up");                
                return false;
            }
        }
        for (int i=h.length/2; i<h.length-1; i++) {
            if (h[i]-h[i+1]!=1) {
                //out.println("down "+i+"="+(h[i]-h[i+1]));   
                return false;
            }
        }
        return h[0]==1;
    }
    
    static ScannerEx sc = new ScannerEx();
    public static void main(String[] args)
    {      
        int T=sc.ni(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) {
            int N=sc.ni();
            int h[]=sc.ria(N);
            System.out.println(check(h)?"yes":"no");
        }
    }
}
