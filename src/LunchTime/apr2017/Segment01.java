package LunchTime.apr2017;


import static java.lang.System.out;
import java.util.Scanner;

class Segment01 {
    static boolean segemnt(String s)
    {        
        boolean one=false;
        boolean end=false;
        for (int j=0; j<s.length(); j++) {
            if (s.charAt(j)=='1') {
                if (one && end)
                    return false;
                else if (!one)
                    one=true;
            } else {
                if (one)
                    end=true;
            }
        }
        return one;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        int TC = sc.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            String s = sc.next();
            out.println(segemnt(s)?"YES":"NO");
        }
    }
}
