/*
 * idea borrowed from SedgeWick
 */
package codechef;

import java.util.Arrays;

public class DSU //Union Find
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
}
