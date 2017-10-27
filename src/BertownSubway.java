
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;


public class BertownSubway {
    
    DSU dsu;
    
    BertownSubway(int p[])
    {
        dsu = new DSU(p.length);
        for (int i=0; i<p.length; i++) {
            dsu.union(i, p[i]-1);
        }
        List<Integer> sizes = dsu.componentSize();
        out.println(sizes);
        int total=0;
        if (sizes.size()==1)
            total =  sizes.get(0)*sizes.get(0);
        else {
            total = (sizes.get(0)+sizes.get(1))*(sizes.get(0)+sizes.get(1));
            for (int i=2; i<sizes.size(); i++)
                total += sizes.get(i)*sizes.get(i);
        }
        out.println(total);
    }
    public static  void test()
    {
        new BertownSubway(new int[]{2,1,3});
        new BertownSubway(new int[]{1,5,4,3,2});
        int p[]=new int[]{98,52,63,2,18,96,31,58,84,40,41,45,66,100,46,71,26,48,81,20,73,91,68,76,13,93,17,29,64,95,79,21,55,75,19,85,54,51,89,78,15,87,43,59,36,1,90,35,65,56,62,28,86,5,82,49,3,99,33,9,92,32,74,69,27,22,77,16,44,94,34,6,57,70,23,12,61,25,8,11,67,47,83,88,10,14,30,7,97,60,42,37,24,38,53,50,4,80,72,39};
        new BertownSubway(p);  //5416
    }
    public static  void judge()
    {
        int n=IORxx.ni();
        new BertownSubway(IORxx.ria(n));
    }
    public static void main(String[] args)
    {      
        test();
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
}

class DSU //Union Find
{
    private int sz[];  // dual purpose, for component id and is visited
    private int id[];
    private int comp;
    public DSU(int n)
    {
        sz = new int[n];
        id = new int[n];
        Arrays.fill(sz, 1);
        comp=n;
        for (int s = 0; s < n; s++)
            id[s]=s;
    }
    public int find(int p) {
        while (p!=id[p])
            p=id[p];
        return p;
    }
    public void union(int u, int v)
    {
        u=find(u);
        v=find(v);
        if (u==v)
            return;
        if (sz[u]<sz[v]) {  // add small component to larger one
            id[u]=v;
            sz[v] += sz[u];
        } else {
            id[v]=u;
            sz[u] += sz[v];            
        }
        comp--;
    }
    
    public int numCompoments()  // components with id=ID
    {
        return comp;
    }    
    public boolean connected(int v, int w)
    {
        return find(w)==find(v);
    }
    public List<Integer> componentSize()
    {
        HashSet<Integer> comp = new HashSet<>();
        for (int i=0; i<id.length; i++)
            comp.add(id[i]);
        List<Integer> sizes=new ArrayList<>(comp.size());
        for (int c: comp)
            sizes.add(sz[c]);
        Collections.sort(sizes, Collections.reverseOrder());
        return sizes;
    }
}
