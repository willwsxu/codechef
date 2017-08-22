/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codechef;

import static java.lang.System.out;
import java.util.Arrays;


// node is 0 based
public class TreeBase extends GraphSimple
{    
    int r; // root
    int parent[]; // parent node, first is root
    long pw[];// weight of node to parent
    int L[]; // levels, L[r]=0, L[u]=L[p[u]]+1
    int E[];    // Euler tour
    int EL[];   // level per index
    int EH[];   // first occurrence
    
    public int getParent(int p)
    {
        return parent[p];
    }
    public long getWeight(int u){
        return pw[u];
    }
    public int getLevel(int u)
    {
        return L[u];
    }
    public TreeBase(int N)
    {
        super(N);
        r=0;
        parent=new int[N];
        L=new int[V()];
        pw=new long[V()];
        parent[0]=-1;
        pw[0]=0;
        L[0]=0;
        E = new int[2*N];
        EL = new int[2*N];
        EH = new int[N];
    }
    
    public void add(int p, int c, long w)
    {
        if (p>c) {
            add(c, p, w);
            return;
        }
        //out.println("add "+p+","+c);
        addEdge(p, c);
        parent[c]=p;
        pw[c]=w;
    }
    int idx=0;
    public void dfs(int v, int lvl) {
        //out.println("dfs "+v+" level "+lvl+" idx="+idx);
        EH[v]=idx;
        E[idx]=v;
        EL[idx++]=lvl;
        L[v]=lvl;
        for (int u: adj(v)) {
            if (u !=0 && u!=getParent(v)) {
                dfs(u, lvl+1);
                E[idx]=v;
                EL[idx++]=lvl;
            }
        }
    }

    void print()
    {
        out.println("Euler first: "+Arrays.toString(EH));
        out.println("Euler tour: "+Arrays.toString(E));
        out.println("Euler level: "+Arrays.toString(EL));
    }
}  
