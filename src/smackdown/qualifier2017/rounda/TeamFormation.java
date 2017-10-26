package smackdown.qualifier2017.rounda;


import static java.lang.System.out;
import java.util.Scanner;

/*
 * n people to form team of two, m teams already formed
 * can rest of people all form teams
 * just check if if n is even, and ignore all other info
 */
// very simple
class TeamFormation {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt(); // 1 ≤ T ≤ 100
        for (int i=0; i<T; i++) { 
        
            int n=sc.nextInt(); // 2 ≤ n ≤ 100
            int m=sc.nextInt(); // 1 ≤ m ≤ n / 2
            for (int j=0; j<m; j++) {
                int u=sc.nextInt();
                int v=sc.nextInt(); //1 ≤ ui, vi ≤ n
            }
            out.println(n%2==0?"yes":"no");
        }
    }
}
