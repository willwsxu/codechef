
package codechef;

// simple IO reader

import java.util.Scanner;

public class IOR {
    
    private static Scanner sc = new Scanner(System.in);    
        
    public static int ni()
    {
        return sc.nextInt();
    }
    public static long nl()
    {
        return sc.nextLong();
    }
    public static String ns()
    {
        return sc.next();
    }
    
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    public static int[] ria1(int N) { // read int array, from 1
        int L[]=new int[N];
        for (int i=1; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    public static int[][] fillMatrix(int n, int m)
    {
        int a[][]=new int[n][m];
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                a[i][j]=sc.nextInt();
            }
        return a;
    }
    
    public static void fileScanner(String f)
    {
        sc = ContestHelper.getFileScanner(f);
    }
}