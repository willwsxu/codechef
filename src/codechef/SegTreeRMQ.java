
package codechef;

import static java.lang.System.out;

public class SegTreeRMQ  // Range min/max query
{
    int st[], a[];
    int n;
    public SegTreeRMQ(int a[]) 
    {
        this.a=a;
        n=a.length;
        st=new int[4*n+1];
        build(1, 0, n-1);
    }
    private int left(int p)
    {
        return p<<1;
    }
    private int right(int p)
    {
        return (p<<1)+1;
    }
    private void build(int node, int first, int last)
    {
        //out.println("build node "+node+" L="+first+" R="+last);
        if (first==last)
            st[node]=first;
        else {
            build(left(node), first, (first+last)/2);
            //out.println("build left "+left(node));
            build(right(node), (first+last)/2+1, last);
            //out.println("build right "+right(node));
            int p1=st[left(node)];
            int p2=st[right(node)];
            //out.println("done build node "+node+" p1="+p1+" p2="+p2);
            st[node]=a[p1]>a[p2]?p1:p2; // pick max of p1 and p2
            //out.println("done build node "+node+" val="+st[node]+" p1="+p1+" p2="+p2);
        }
    }
    int rmq(int p, int L, int R, int i, int j)
    {
        if (i>R || j<L)
            return -1; // segment outside of query range
        if (L>=i && R<=j)
            return st[p]; // i L R j, inside range
        int p1=rmq(left(p),  L,        (L+R)/2, i, j);
        int p2=rmq(right(p), (L+R)/2+1, R,      i, j);
        if (p1<0)
            return p2;
        if (p2<0)
            return p1;
        return a[p1]>a[p2]?p1:p2;
    }
    
    public int rmq(int i, int j) {
        return rmq(1, 0, n-1, i, j);
    }
    public int rmqVal(int i, int j) {
        int ind=rmq(1, 0, n-1, i, j);
        if (ind<0)
            return Integer.MIN_VALUE;
        //out.println("rmq i="+i+" j="+j+" ind="+ind);
        return a[ind];
    }
    
    public static void test()
    {
        SegTreeRMQ st=new SegTreeRMQ(new int[]{3, 2, 2, 3, 3, 4, 3, 3, 2, 2, 3, 2, 2, 1});
        out.println(st.rmq(1, 4)==4);
        out.println(st.rmq(1, 1)==1);
        out.println(st.rmq(0, 5)==5);
        out.println(st.rmq(0, 9)==5);
        SegTreeRMQ st2=new SegTreeRMQ(new int[]{3, 2, 2, 3, 3, 4, 3, 3, 2, 2, 3, 2, 2, 1});
    }
}
