package longContests.june17;


import static java.lang.System.out;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/* Find s set of n int that a+b=c does not exist for any numbers in the set
 * simple trick, add any odd int to set
*/
// GOODSET
class GoodSet {
    Set<Integer> sum=new HashSet<>();
    GoodSet()
    {
        this(sc.nextInt());
        
    }
    GoodSet(int n)
    {
        for (int i=0; i<n; i++) {
            out.print(2*i+1);
            out.print(" ");
        }
        out.println();
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt(); // 1 ≤ T, n ≤ 100
        while (T-->0)
            new GoodSet();
    }
}
