package r415;


import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//contest r415, 809A
public class WantDate {
    
    static final int  MOD=1000000007;    // 10^9 + 7
    static long sum1(int x[], int i, int n) {
        long diff=x[i]-x[n];
        int power=n-i-1;
        //out.println("diff="+diff+" power="+power);
        if (power==0) {
            return diff%MOD;
        }
        long total=0;
        while (power>0) {
            int p=min(power, 30);
            power -= p;
            diff *= (1<<p);
            total = (total+diff%MOD)%MOD;
        }
        //out.println("new total="+total);
        return total;
    }
    static void sum(int x[])
    {
        x = sortIaR(x);
        int n=x.length-1;
        long total=0;
        for (int i=0; i<n; i++)
        {
            total =(total+sum1(x, i, n))%MOD;
            if (i==0)
                continue;
            total =(total+sum1(x, 0, i))%MOD;
        }
        out.println(total);
    }
    static void test()
    {
        sum(new int[]{400000000,100000000,300000000});
        sum(new int[]{400000000,100000000,300000000,1});
        sum(new int[]{1, 2, 3, 4, 5});
        sum(new int[]{100000000, 200000000, 300000000, 400000000, 500000000});
    }
        
    static int[] sortIaR(int a[])  // sort int array reverse
    {
        return IntStream.of(a).boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(i->i).toArray();        
    }
    static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();
        int N=sc.nextInt();     // 1 ≤ n ≤ 3·10^5
        int x[]=ria(N);         // 1 ≤ xi ≤ 10^9
        sum(x);
    }
}
