package Cookoff.June17;


import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.Scanner;

// simple
class FlipCrayon {
    static int flip(String cray)
    {
        int up=0, down=0;
        char last=0;
        for (int i=0; i<cray.length(); i++) {
            if (cray.charAt(i)==last)
                continue;
            last=cray.charAt(i);
            if (last=='U')
                up++;
            else
                down++;
        }
        return min(up,down);
    }
    public static void test()
    {
        out.println(flip("UUU"));
        out.println(flip("D"));
        out.println(flip("U"));
        out.println(flip("UUDDDUUU"));        
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T = sc.nextInt();  // 1 ≤ T ≤ 3000
        while (T-->0)
            out.println(flip(sc.next()));
    }
}
