package codeforces.edu33;

/*
 * edu round 33, C
 * Given n people, some are friends with each other will share any info
 * Vova wants to spread a rumor to all people. She can bribe someone to start the rumor
 * What is the minimal bribe she has to pay
 */


import codechef.GraphSimple;
import codechef.IOR;
import static java.lang.System.out;

public class Rumor {
    int bribe[];
    
    Rumor(int b[]) //1 ≤ n ≤ 10^5, 0 ≤ m ≤ 10^5
    {
        bribe=b;
        g=new GraphSimple(b.length+1);
    }
    void addEdges(int [][]e)
    {
        if (e!=null) {
            for (int r=0; r<e.length; r++) {
                g.addEdge(e[r][0], e[r][1]);
            }
        }
        dfsAll();
    }
    
    GraphSimple g;
    boolean visited[];
    void dfsAll()
    {
        visited = new boolean[g.V()];
        long total=0;  // use long type to avoid overflow
        int MAX_BRIBE=1000000000;
        for (int i=1; i<g.V(); i++) {  // vertex start from 1
            if (visited[i])
                continue;
            total += dfs(i, MAX_BRIBE);
        }
        out.println(total);
    }
    
    // dfs and find minimal bribe value
    int dfs(int u, int b)
    {
        visited[u]=true;
        if (b>bribe[u-1])  // bribe index start from 0
            b=bribe[u-1];
        for (int v: g.adj(u)) {
            if (!visited[v]) {
                int b2=dfs(v, b); 
                if (b2<b)
                    b=b2;
            }
        }
        return b;
    }
    static void test()
    {
        Rumor r=new Rumor(new int[]{2,5,3,4,8});
        r.g.addEdge(1, 4);
        r.g.addEdge(4, 5);
        r.dfsAll(); //10
        
        r=new Rumor(new int[]{1,2,3,4,5,6,7,8,9,10});
        r.dfsAll(); //55
        
        r=new Rumor(new int[]{1,6,2,7,3,8,4,9,5,10});
        r.g.addEdge(1, 2);
        r.g.addEdge(3, 4);
        r.g.addEdge(5, 6);
        r.g.addEdge(7, 8);
        r.g.addEdge(9, 10);
        r.dfsAll();
    }
    public static void main(String[] args)
    {      
        judge();
    }
    static void judge()
    {        
        int n=IOR.ni();  // 1 ≤ n ≤ 10^5, 0 ≤ m ≤ 10^5
        int m=IOR.ni();
        int c[]=IOR.ria(n); // bribe, 0 ≤ ci ≤ 10^9
        int edges[][]=IOR.fillMatrix(m, 2);
        new Rumor(c).addEdges(edges);
    }
}