
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
 * For each query you have to find the number of such x that L ≤ x ≤ R and there exist integer numbers a > 0, p > 1 such that x = a^p
 * 1 ≤ L ≤ R ≤ 10^18
 */


public class SadPower {
    static long MAX=1000000000000000000L;
    static long MAX_CUBE=1000000; // cubic power
    static List<Long> powers=new ArrayList<>();
    static {  // calculate all powers
        Set<Integer> squares = new HashSet<>();
        for (int i=2; i<=1000; i++)
            squares.add(i*i);
        Set<Long> powerSet = new HashSet<>();
        for (int base=2; base <=MAX_CUBE; base++)
        {
            if (squares.contains(base))  // remove squares
            {
                //System.out.println("skip square "+base);
                continue;
            }
            if (powerSet.contains(base))   // remove duplicates
                continue;
            long square = (long)base*base;  // need cast to avoid overflow
            long max=MAX/square;
            long p=base;
            while (p<=max) {
                p *= square;  // exclude perfect square
                //powers.add(p);
                powerSet.add(p);
            }
        }
        for (long v: powerSet)
            powers.add(v);
        //powers.add(1L);
        //System.out.println("total powers "+powers.size()+" set "+powerSet.size());
        Collections.sort(powers);
        //System.out.println(powers);
    }
    static long root(long val) {
        long L=0; 
        long R=val;
        if (R>1000000000)
            R=1000000000;
        while (L<=R) {
            long mid=(L+R)/2;
            long sq=mid*mid;
            if (sq==val)
                return mid;
            if (sq>val) {
                R=mid-1;
                sq=R*R;
                if (sq==val)
                    return R;
            }
            else {
                L=mid+1;
                sq=L*L;
                if (sq==val)
                    return L;
            }
        }
        if (L*L>val)
            return L-1;
        return L;
    }
    static long powers(long L, long R) {
        int low=Collections.binarySearch(powers, L);
        int high=Collections.binarySearch(powers, R);
        if (low<0)
            low = -low;
        if (high<0)
            high = (-high)-1;
        long ans = high-low+1;
        System.out.println("ans before square "+ans);
        long r1=root(L);
        ans += root(R)-r1;
        if (r1*r1==L)
            ans++;
        return ans;
    }
    public static void testSqrt()
    {
        System.out.println(SadPower.root(571));
        System.out.println(SadPower.root(137));
        System.out.println(SadPower.root(100)==10);
        System.out.println(SadPower.root(99)==9);
        System.out.println(SadPower.root(4)==2);
        System.out.println(SadPower.root(1)==1);        
    }
    public static void testPower()
    {
        System.out.println(SadPower.powers(1,1)==1);
        System.out.println(SadPower.powers(1,100)==13);
        System.out.println(SadPower.powers(1,4)==2);
        System.out.println(SadPower.powers(9,9)==1);
        System.out.println(SadPower.powers(5,7)==0);
        System.out.println(SadPower.powers(12,29)==3);
        System.out.println(SadPower.powers(137, 591)==17);
        System.out.println(SadPower.powers(1,1000000)==1111);        
    }
     
    static void judge()
    {
        int q=IORx.ni();
        while (q-->0) {
            long L=IORx.nl();
            long R=IORx.nl();
            System.out.println(SadPower.powers(L,R));     
        }        
    }
    public static void main(String[] args)
    {
        System.out.println(SadPower.root(789126942561822236L));
        System.out.println(SadPower.powers(789126942561822236L,978550298073181934L));      
    }
}


class IORx {
    
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