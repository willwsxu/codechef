


import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


class DigitVirus {
    static void bruteforce(String v)
    {
        char[] s=v.toCharArray();
        char[] ns=new char[s.length];
        Arrays.fill(ns, '0');
        int ans=0;
        boolean change=false;
        do {
            change=false;
            for (int i=0; i<v.length(); i++) {
                // for each virus at i, check its +- 9 neighbors to see it it can mutate them
                for (int j=max(0, i-9); j<min(i+9, s.length); j++) {
                    if (s[i]-s[j]>=abs(i-j) && ns[j]<s[i]) {
                        ns[j]=s[i];
                        change=true;
                    }
                }
            }
            if ( change )
                ans++;
            //out.println(Arrays.toString(ns));
            for (int i=0; i<v.length(); i++)
                s[i]=ns[i];
        } while (change);
        out.println(ans);
    }
    static void test()
    {
        bruteforce("555755555");        // 3
        bruteforce("311110000000000");  // 6
        bruteforce("07788000744");      // 4
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        //test();
        int TC = sc.nextInt();  // between 1 and 3
        for (int i=0; i<TC; i++) {
            String s = sc.next(); //1 ≤ N ≤ 150,000
            bruteforce(s);
        }
    }
}
