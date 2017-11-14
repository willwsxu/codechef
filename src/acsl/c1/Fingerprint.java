
package acsl.c1;

// 2010-2011 senior div, contest 1

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Fingerprint {
    static final int[] whorlNo=new int[]{  1,1,2,2,4,16,16,8,8,4};
    static final int[] fingerNo=new int[]{10,9,8,7,6,1, 2, 3,4,5};
    static final int[] bitEven=new int[]{10, 8, 6, 4, 2};
    static final int[] bitOdd=new int[]{  9, 7, 5, 3, 1};
    
    
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
    void decode(List<Integer> ans, int num, int[]bitmap)
    {
        for (int i=0; i<5; i++) {
            if ((num & 1<<i)>0)
                ans.add(bitmap[i]);
        }        
    }
    
    int getFinger(int whorl, boolean even) {
        switch (whorl) {
            case 16:
                return even?2:1;
            case 8:
                return even?4:3;
            case 4:
                return even?6:5;
            case 2:
                return even?8:7;
            case 1:
                return even?10:9;
        }
        return 0;
    }
    // helper function to reduce code duplication
    int addFinger(List<Integer> ans, int num, int whorl, boolean even)
    {
        ans.add(getFinger(whorl, even));
        return num - whorl;        
    }
    void brute(List<Integer> ans, int num, boolean even)
    {
        while (num>0) {
            if (num>=16) {
                num = addFinger(ans, num, 16, even);
            } else if (num>=8) {
                num = addFinger(ans, num, 8, even);
            } else if (num>=4) {
                num = addFinger(ans, num, 4, even);
            } else if (num>=2) {
                num = addFinger(ans, num, 2, even);
            } else if (num>=1) {
                num = addFinger(ans, num, 1, even); 
            }
        }
    }
    Fingerprint(int num, int denom)
    {
        List<Integer> ans=new ArrayList<>();
        //decode(ans, num, bitEven);
        //decode(ans, denom, bitOdd);
        brute(ans, num, true);
        brute(ans, denom, false);
        printList(ans);
    }    
    public static void test()
    {
        new Fingerprint(19-1,3-1);  // 8, 2, 7
        new Fingerprint(0,0);       // None
        new Fingerprint(1,0);       // 10
        new Fingerprint(0,1);       // 9
        new Fingerprint(24,12);     // 4,2,5,3
        new Fingerprint(20,26);     // 6,2,7,3,1
        new Fingerprint(16,8);      // 2,3
        new Fingerprint(8,4);       // 4,5
    }
    static Scanner sc=new Scanner(System.in).useDelimiter("\\s*[,\n]\\s*");
    public static void main(String[] args)
    {  
        //test();
        new Fingerprint(sc.nextInt()-1, sc.nextInt()-1);
    }
}
