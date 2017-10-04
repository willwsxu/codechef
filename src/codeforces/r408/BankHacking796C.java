package codeforces.r408;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// There are n banks, numbered from 1 to n. There are also n - 1 wires connecting the banks. 
// All banks are initially online. Each bank i has initial strength ai.
// Bank i and j are neighboring if and only if there exists a wire directly connecting them.
// Bank i and j are semi-neighboring if and only if there exists an ONLINE bank k such that i and k are neighboring and k and j are neighboring.
// When a bank is hacked, it becomes offline, and other banks that are neighboring or semi-neighboring to it have their strengths increased by 1.
// From above, we can conclude that each bank strength increase at most 2
// Pick a bank to start, then repeat one that must be neighboring to some offline bank and strength<=hacker
// find the minimum strength of hacker's computer strength
public class BankHacking796C {
    
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        int N = scan.nextInt();  // 1 ≤ n ≤ 3·10^5
        int k = scan.nextInt();  // swaps
        List<Long> A = new ArrayList<>(300000); // strength - 10^9 ≤ ai ≤ 10^9
        for (int i=0; i<N; i++)
            A.add(scan.nextLong());
        for (int i=0; i<N-1; i++) {
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            String[] conn = line.split(" ");
            int c1 = Integer.parseInt(conn[0]);
            int c2 = Integer.parseInt(conn[1]);
            
        }
    }
}
