/*
 * idea borrowed from SedgeWick
 */
package codechef;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
    public int getSize(int u)
    {
        return sz[find(u)];
    }
    public List<Integer> componentSize()
    {
        HashSet<Integer> comp = new HashSet<>();
        for (int i=0; i<id.length; i++)
            comp.add(find(i));
        List<Integer> sizes=new ArrayList<>(comp.size());
        for (int c: comp)
            sizes.add(sz[c]);
        Collections.sort(sizes, Collections.reverseOrder());
        return sizes;
    }
    public Map<Integer, List<Integer>> components(){
        Map<Integer, List<Integer>> ret = new HashMap<>();
        for (int i=0; i<id.length; i++) {
            final int c=i;
            ret.compute(find(i), (k,v)->{
                if (v==null)
                    v = new ArrayList<>();
                v.add(c);
                return v;
                    });
        }
        return ret;
    }
    public void printComponents()
    {
        out.println("Components #: "+comp);
        Map<Integer, List<Integer>> cp=components();
        for (Map.Entry<Integer, List<Integer>> c: cp.entrySet()) {
            if (c.getValue().isEmpty())
                continue;
            out.println("id "+c.getKey()+":"+c.getValue().toString());            
        }
    }
}
