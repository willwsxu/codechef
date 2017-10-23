package Cookoff.June17;


import static java.lang.System.out;
import java.util.Scanner;

// easy, CENTREE, Jun cook off
// A node is centeroid if on removing it, resulting components has size at most n/2
// Center is defined in traditional sense: Let f(x) be the greatest distance from u to any other vertex of the tree. 
//  A vertex u is a center if f(u) ≤ f(v) for any other vertex v. 
// Beauty will be equal to the maximum number of nodes between center and a centeroid 
// Given N and B, construct such a tree. Print YES or NO depending if it is possible
// IDEA: construct nodes to create a center of 2B+1 nodes, straight line
//       treat node 1 as centeroid, and rest of nodes to it
// YES if n>=4B
// special case N=2, B is 1
class Centeroid {
    Centeroid(int n, int b)
    { // 2 ≤ n ≤ 100, 0 ≤ b ≤ n-1
        if (n==2) {
            if (b==1) {
                out.println("YES");
                out.println(1+" "+2);
            }
            else
                out.println("NO");
            return;
        }
        if (n<4*b){
            out.println("NO");
            return;
        }
        out.println("YES");
        int center=2*b;  // build a center with 2b edges
        for (int i=1; i<=center; i++)
            out.println(i+" "+(i+1));
        for (int i=center+2; i<=n; i++)  // centeroid around node 1
            out.println(1+" "+(i));
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T = sc.nextInt();  // 1 ≤ T ≤ 10^4
        while (T-->0)
            new Centeroid(sc.nextInt(), sc.nextInt());
    }
}
