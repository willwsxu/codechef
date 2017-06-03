package LunchTime.may2017;


import static java.lang.Long.min;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

/*

 */

// POSTTREE, EASY (more like MEDIUM)
public class PostTree {
    
    long c[];
    int parent[];
    long w[];
    PostTree(int parent[], long w[])
    {
        c=new long[w.length];
        for (int i=1; i<w.length; i++)
            c[i]=w[i];
        this.parent=parent;
        this.w=w;
        cost(w.length-1);
        for (int i=1; i<w.length;i++)
            out.print(c[i]+" ");
        out.println();
    }
    void update(int p)
    {
        while (p>1) {
            int g=parent[p];
            if (c[g]>c[p])
                c[g]=c[p];
            else
                break;
        }
    }
    void cost(int n)
    {
        update(n);
        if (n>1)
            cost(n-1);
        //out.println("n="+n+" c "+c[n]);
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int N=sc.nextInt(); // 1 ≤ N ≤ 100,000
        int parent[]=new int[N+1];
        parent[1]=1;
        for (int i=2; i<=N; i++) { 
            parent[i]=sc.nextInt();
        }
        long w[]=new long[N+1];
        for (int i=1; i<=N; i++)
            w[i]=sc.nextLong();  //-1,000,000,000 ≤ Av ≤ 1,000,000,000
        new PostTree(parent, w);
    }
}
/*
8
1 1 1 1 5 8 6
1 2 3 4 5 15 70 10
*/
