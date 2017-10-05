package codeforces.r408;


import codechef.MyScanner;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

// There are n banks, numbered from 1 to n. There are also n - 1 wires connecting the banks. 
// All banks are initially online. Each bank i has initial strength ai.
// Bank i and j are neighboring if and only if there exists a wire directly connecting them.
// Bank i and j are semi-neighboring if and only if there exists an ONLINE bank k such that i and k are neighboring and k and j are neighboring.
// When a bank is hacked, it becomes offline, and other banks that are neighboring or semi-neighboring to it have their strengths increased by 1.
// From above, we can conclude that each bank strength increase at most 2
// Pick a bank to start, then repeat one that must be neighboring to some offline bank and strength<=hacker
// find the minimum strength of hacker's computer strength
public class BankHacking796C {
    
    int strength[];         //- 10^9 ≤ ai ≤ 10^9, 1 ≤ n ≤ 3·10^5
    List<List<Integer>> adj;//1 ≤ ui, vi ≤ n
    BankHacking796C()
    {
        strength = sc.ria(sc.ni());
        adj = new ArrayList<>(strength.length+1);
        for (int i=0; i<=strength.length; i++)
            adj.add(new ArrayList<>(10));
        for (int i=0; i<strength.length-1; i++) {
            int u = sc.ni();
            int v = sc.ni();
            adj.get(u).add(v);
            adj.get(v).add(u);
        } 
        out.println(adj);
    }
    static MyScanner sc = new MyScanner();
    public static void main(String[] args)
    {    
        new BankHacking796C();
    }
}
