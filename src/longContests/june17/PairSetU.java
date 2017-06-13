package longContests.june17;


import static java.lang.System.out;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// Pairwise Set Union
class PairSetU {
    PairSetU()
    {
        int n=sc.nextInt(); //1 ≤ N, K ≤ 2500
        int k=sc.nextInt(); 
        int sets[][]=new int[n][];
        for (int i=0; i<n; i++) {
            int len=sc.nextInt(); // 1 ≤ leni ≤ K
            sets[i]=ria(len, sc);
        }
        solve(n, k, sets);
    }
    PairSetU(int n, int K, int[][]sets)
    {
        solve(n, K, sets);
    }
    void solve(int n, int K, int[][]sets)
    {
        int count=0;
        if ( n==1) {
            count=sets[0].length==K?1:0;
            out.println(count);
            return;
        }
        for (int i=0; i<n-1; i++) {
            boolean base[]=new boolean[K+1];
            for (int j=0; j<sets[i].length; j++)
                base[sets[i][j]]=true;
            for (int j=i+1; j<n; j++) {
                Set<Integer> pair=new HashSet<>();
                for (int m=0; m<sets[j].length; m++) {
                    if (base[sets[j][m]])
                        continue;
                    pair.add(sets[j][m]);
                }
                if (pair.size()+sets[i].length==K)
                    count++;
            }
        }
        out.println(count);
    }
    static void test()
    {
        new PairSetU(2, 2, new int[][]{{1},{1}});
        new PairSetU(3, 2, new int[][]{{1,2},{1,2},{1,2}});
        new PairSetU(3, 4, new int[][]{{1,2,3},{1,2,3,4},{2,3,4}});
        new PairSetU(1, 2, new int[][]{{1,2}});
        new PairSetU(1, 3, new int[][]{{1,2}});
        new PairSetU(3, 2, new int[][]{{1},{2},{1}});
    }
    public static int[] ria(int N, Scanner sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {   
        //test();
        int T=sc.nextInt();  // 1 ≤ T ≤ 10
        while (T-->0)
            new PairSetU();
    }
}
