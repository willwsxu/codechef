package smackdown.qualifier2017;


import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

/*
 */

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
    
    static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) {
            int N=sc.nextInt();
            int h[]=ria(N);
            System.out.println(check(h)?"yes":"no");
        }
    }
}
