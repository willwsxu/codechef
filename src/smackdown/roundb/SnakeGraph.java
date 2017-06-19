package smackdown.roundb;
/*
 * Open Letter to vinaykandy2k16 at codechef who stole my code for Smackdown 2017
 * I am not going to lecture you about honesty. If someone wants to cheat, please
 * don't be so lazy to just copy and paste. Make some efforts to make the code look like yours.
 * Your laziness dragged me down and caused my disqualification in Smackdown 2017 contest.
 */

import codechef.DSU;
import codechef.GraphSimple;
import static java.lang.System.out;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

// only process edge if both nodes has degree>d

// SNGraph, Medium, DSU (Disjoint Set Union)
// https://discuss.codechef.com/questions/99946/sngraph-editorial
class SnakeGraph {
    GraphSimple g;
    SnakeGraph()
    {
        int n=sc.nextInt(); // 1 ≤ n ≤ 10^5
        int m=sc.nextInt();//0 ≤ m ≤ min(n * (n - 1) / 2, 2 * 10^5)
        g=new GraphSimple(n);
        for (int j=0; j<m; j++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            g.addEdge(u-1, v-1);
        }
    }
    void solve()
    {
        // sort node by # of edges
        PriorityQueue<Map.Entry<Integer, Integer>> nodes = new PriorityQueue<>(100000, (e1,e2)->e2.getValue()-e1.getValue());
        
        for (int s = 0; s < g.V(); s++) {
            // no need to visit nodes without any edge
            if (!g.adj(s).isEmpty())
            {
                nodes.add(new SimpleEntry<>(s,g.adj(s).size()));
            }
        }
        DSU cc=new DSU(g.V());
        Stack<Integer> ans=new Stack<>();// store result in reverse order in stack
        // process node when degree is larger than d
        for (int d=g.V()-1; d>=0; d--) {  // reverse order of degree so we can add edge instead of delete
            ans.push(cc.numCompoments()-1);// need componenets-1 edges to connect all
            while (!nodes.isEmpty()) {
                Map.Entry<Integer, Integer> e=nodes.peek();
                if (e.getValue()<d)
                    break;
                for (int w: g.adj(e.getKey())) {
                    if (g.adj(w).size()<d)  // both nodes degree must >d
                        continue;
                    cc.union(w, e.getKey());
                }
                nodes.poll();
            }
        } 
        StringBuilder sb = new StringBuilder();    
        while (!ans.isEmpty()){
            sb.append(ans.pop());
            sb.append(" ");
        }
        out.println(sb.toString());
    }
       
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt();     // 1 ≤ T ≤ 3
        while (T-->0)
            new SnakeGraph().solve();
    } 
}
/*
1
5 4
1 2
2 3
2 4
4 5

0 3 4 4 4 
*/