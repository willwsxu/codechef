package longContests.june17;


import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


class CloneMe {
    int A[];
    CloneMe()
    {
        int n=ni(); // 1 ≤ N, Q ≤ 10^5
        int q=ni();
        A=ria(n);//1 ≤ A[i] ≤ 10^5
        for (int i=0; i<q; i++)
        {
            out.println(brute(ni(), ni(), ni(),ni())?"YES":"NO");
        }
    }
    boolean brute(int a, int b, int c, int d)
    {
        int a1[]=Arrays.copyOfRange(A, a-1, b);
        int a2[]=Arrays.copyOfRange(A, c-1, d);
        Arrays.sort(a1);
        Arrays.sort(a2);
        int diff=0;
        for (int i=0; i<a1.length; i++) {
            if (a1[i]!=a2[i])
                diff++;
            if (diff>1)
                break;
        }
        return diff<=1;
    }
        
    public static int ni()
    {
        return sc.nextInt();
    }
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);  //too slow for large input
    //static MyScanner sc=new MyScanner();
    static void autotest()
    {
        int T=ni();  // 1 ≤ T ≤ 3
        while (T-->0)
            new CloneMe();        
    }
    public static void main(String[] args)
    {   
        autotest();
    }
}
