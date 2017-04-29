package CookOff0417;


import static java.lang.System.out;
import java.util.Scanner;


class ABRepeat {
    
    static void solve(String s, long r)
    {
        StringBuilder sb = new StringBuilder();
        long bb=0; // total b in the string
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i)=='a' || s.charAt(i)=='b') {
                sb.append(s.charAt(i));
                if (s.charAt(i)=='b')
                    bb++;
            }
        }
        long total=0;
        s = sb.toString();
        long b=0;
        for (int i=s.length()-1; i>=0; i--) {
            if (s.charAt(i)=='b')
                b++;
            else {
                total += b*r;
                total += bb*(r-1)*r/2;
            }
        }
        out.println(total);
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
        int TC = sc.nextInt();  // 1 ≤ T ≤ 10
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt();   // 1 ≤ N ≤ 10^5
            long K = sc.nextLong(); // 1 ≤ N * K ≤ 10^9
            String s = sc.next();
            solve(s, K);
        }
    }
}
