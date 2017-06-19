package Cookoff.June17;


import static java.lang.System.out;
import java.util.Scanner;

// tricky
class Snackup {
    static void rounds(int n)//2 ≤ n ≤ 100
    {
        StringBuilder sb=new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for (int i=0; i<n; i++) {
            sb.append(n);
            sb.append("\n");
            for (int j=0; j<n; j++) {
                sb.append(j+1);// judge
                sb.append(" ");
                sb.append((j+i)%n+1);
                sb.append(" ");
                sb.append((j+i+1)%n+1);
                sb.append("\n");
            }
        }
        out.print(sb.toString());
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T = sc.nextInt();  // 1 ≤ T ≤ 100
        while (T-->0)
            rounds(sc.nextInt());
    }
}
