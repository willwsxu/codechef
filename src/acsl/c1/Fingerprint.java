
package acsl.c1;

// 2010-2011 senior div, contest 1

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fingerprint {
    static final int[] whorlNo=new int[]{1,1,2,2,4,16,16,8,8,4};
    static final int[] fingerNo=new int[]{10,9,8,7,6,1,2,3,4,5};
    static final int[] bitEven=new int[]{10, 8, 6, 4, 2};
    static final int[] bitOdd=new int[]{9, 7, 5, 3, 1};
    
    
    void printList(List<Integer> ans)
    {
        if (ans.isEmpty()) {
            out.println("None");
            return;
        }
         for (int i=0; i<ans.size(); i++) {
             out.print(ans.get(i));
             if ( i<ans.size()-1)
                 out.print(",");
         }   
         out.println();
    }
    Fingerprint(int num, int denom)
    {
        List<Integer> ans=new ArrayList<>();
        for (int i=0; i<5; i++) {
            if ((num & 1<<i)>0)
                ans.add(bitEven[i]);
        }
        for (int i=0; i<5; i++) {
            if ((denom & 1<<i)>0)
                ans.add(bitOdd[i]);
        }
        printList(ans);
    }    
    public static void test()
    {
        new Fingerprint(19-1,3-1);
        new Fingerprint(0,0);
        new Fingerprint(1,0);
        new Fingerprint(0,1);
        new Fingerprint(24,12);
        new Fingerprint(20,26);
    }
    static Scanner sc=new Scanner(System.in).useDelimiter("\\s*[,\n]\\s*");
    public static void main(String[] args)
    {  
        int num=sc.nextInt();
        int denom=sc.nextInt();
        new Fingerprint(num-1, denom-1);
    }
}
