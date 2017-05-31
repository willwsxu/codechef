
import java.util.Scanner;

/*
 * Brief Desc: 
 */

public class SnakeCoup {
    
    // add horizontal fence if there are snakes sharing a sidein any column
    int fences(String[] h, int n)
    {
        int f=0;
        boolean hori=false;
        for (int i=0; i<n; i++)  {
            if (!hori) {
                if (h[0].charAt(i)=='*' && h[1].charAt(i)=='*') {
                    hori=true;
                    f++;
                    break;
                }                
            }
        }
        int prev=0; // compare to value is previous none empty
        for (int i=1; i<n; i++)  {
        
        }
        return 0;
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt();     // 1 ≤ T ≤ 10
        for (int i=0; i<T; i++) {
            int n=sc.nextInt(); // 1 ≤ n ≤ 10^5
            String[] h=new String[2];
            h[0]=sc.next();
            h[1]=sc.next();
        }
    }
}
