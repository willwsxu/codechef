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
// Pick a bank to start, then repeat, each must be neighboring to some offline bank and strength<=hacker
// find the minimum strength of hacker's computer strength
// IDEA: find m=max(bank 1 to n), answer can only be one of m, m+1, or m+2
//       count # of banks with strength m, or m-1. other banks does impact answer
// Iterate each bank, find its neighbours with strength m or m-1. find if there are other banks with strength m or m-1 (use simple math as total m or m-1 is known).
//       if other bank has m, then ans=m+2, else if other bank has m-1, then ans=m+1, else if neighbor has m, ans=m+1, else ans=m
// final answer is min (ans from 1 to n)
public class BankHacking796C {
    
    int strength[];         //- 10^9 ≤ ai ≤ 10^9, 1 ≤ n ≤ 3·10^5
    List<List<Integer>> adj;//1 ≤ ui, vi ≤ n
    int MIN_VAL=-1000000000;  // 
    int mV=MIN_VAL;
    BankHacking796C()
    {
        strength = sc.ria(sc.ni());
        adj = new ArrayList<>(strength.length+1);
        for (int i=0; i<strength.length; i++)
            adj.add(new ArrayList<>(10));
        for (int i=0; i<strength.length-1; i++) {
            int u = sc.ni()-1;  // index from 0
            int v = sc.ni()-1;
            adj.get(u).add(v);
            adj.get(v).add(u);
        } 
        //out.println(adj);
        solve();
    }
    
    void solve()
    {
        for (int i=0; i<strength.length; i++)
            if (mV<strength[i])
                mV=strength[i];
        int answer=mV+2; // largest answer
        int cntMax=0;    // count of max
        int cntMax_1=0;  // count of max-1
        for (int i=0; i<strength.length; i++) {
            if (mV==strength[i])
                cntMax++;
            else if (mV-1==strength[i])
                cntMax_1++;
        }
        //out.println("mV="+mV+" cntMax="+cntMax+" cntMax1="+cntMax_1);
        for (int i=0; i<strength.length; i++) {
            int maxNb=0;
            int maxNb1=0;
            List<Integer> neb = adj.get(i);
            for (int j=0; j<neb.size(); j++) {
                if (strength[neb.get(j)]==mV)
                    maxNb++;
                else if (strength[neb.get(j)]==mV-1)
                    maxNb1++;
            }
            int maxOther = cntMax-maxNb;
            int maxOther1 = cntMax_1-maxNb1;
            if (mV==strength[i])
                maxOther--;
            else if (mV-1==strength[i])
                maxOther1--;
            int ansMax=mV;
            if ( maxOther>0)
                ansMax += 2;
            else if ( maxOther1>0 || maxNb>0)
                ansMax++;
            if (ansMax < answer)
                answer = ansMax;   //
            if (answer==mV)
                break;  // find the minmal, no need to further
        }
        out.println(answer);
    }
    
    static MyScanner sc = new MyScanner();
    public static void main(String[] args)
    {    
        new BankHacking796C();
    }
}

/* failed test case
10
-1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000 -1000000000
10 3
7 4
2 6
9 2
5 10
1 8
7 8
7 2
10 6
*/