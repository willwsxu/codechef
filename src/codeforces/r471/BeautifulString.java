package codeforces.r471;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// adorable string def, a string of 2 distinct symbols
// find out if a string can be split into 2 adorable string
public class BeautifulString {
    boolean beauti(String s)
    {
        if (s.length()<4)
            return false;
        int count[]=new int[26];
        Arrays.fill(count, 0);
        for (int i=0; i<s.length(); i++) {
            count[s.charAt(i)-'a']++;
        }
        int letters=0;
        int single=0;
        for (int i=0; i<count.length; i++) {
            if (count[i]>0)
                letters++;
            if (count[i]==1)
                single++;
        }
        if (letters>4)
            return false;
        if (letters>=3)  // 3 types of letter
            return true;
        if (letters==1)
            return false;
        return single==0;  // X YYY won't work
    }
    
    public static void main(String[] args)
    {      
        boolean ans=new BeautifulString().beauti(IORxx.ns());
        System.out.println(ans?"Yes":"No");
    }
}


class IORxx {
    
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
    
    public static List<Integer> riL(int N) { // read int array list
        List<Integer> L=new ArrayList<>();
        for (int i=0; i<N; i++)
            L.add(sc.nextInt());
        return L;
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

    public static void fileScanner(Scanner s)
    {
        sc = s;
    }
}