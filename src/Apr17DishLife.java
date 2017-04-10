
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

// Easy to medium
class Apr17DishLife {
    
    static List<int[]> ingredients(int N)
    {
        List<int[]> ingr = new ArrayList<>(N);
        for (int j=0; j<N; j++) {
            int c = scan.nextInt();
            int[] p = new int[c];
            for (int k=0; k<c; k++)
                p[k] = scan.nextInt();
            ingr.add(p);
        }
        Collections.sort(ingr, (c1,c2)->c2.length-c1.length);
        return ingr;
    }
    
    static void solve(List<int[]> ingr, int K)
    {
        int N=ingr.size();
        Set<Integer> all = new HashSet<>();
        int j=0;
        boolean extra=false;
        for (j=0; j<N; j++) {
            int[]p=ingr.get(j);
            int before = all.size();
            for (int k:p)
                all.add(k);
            if ( all.size()==before)
                extra=true;
            if (all.size()==K)
                break;
        }
        if (all.size()<K)
            out.println("sad");
        else if (extra || j<N-1)
            out.println("some");
        else
            out.println("all");        
    }
    
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        //scan = codechef.CodeChef.getFileScanner("dishLife0417.txt");
        int TC = scan.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();   // islands 1 ≤ N, K ≤ 10^5
            int K = scan.nextInt();     // ingredients
            List<int[]> ingr = ingredients(N);
            solve(ingr, K);
        }
    }
}
