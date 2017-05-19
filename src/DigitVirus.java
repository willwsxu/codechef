/* Virus of strength 0 to 9 in a string e.g. "8761", a virus of higher strength
 * can infect neighbor of lower strength if the difference of strength is no less than
 * difference of distance, i.e. S[i] - S[j] >= |i - j|, infection takes a sec regardless
 * of distance. e.g. in 1 sec, "87700010010"->"88788888811"
 * output: seconeds needed to infect all virus into a single strength
 * Editorial idea: bfs
 * use queue to store all virus position, in each turn, output new strength in a new array
 * and save the virus which changed strength into a queue
 * additional performance boost, use a vis[] boolean to keep track of changes in each round
 *  it helps to add virus to queue just once.
*/


import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

// DIGVIRUS medium
class DigitVirus {
    
    // qa stores current virus position, initially all
    // qb store virus that changed strength, only these virus need to be processed again
    // ns store updated strength, only if strength increases
    // vis is used to ensure virus is added to qb only once
    static void bfs(String v)
    {
        Queue<Integer> qa=new ConcurrentLinkedQueue<>();
        Queue<Integer> qb=new ConcurrentLinkedQueue<>();
        char[] s=v.toCharArray();
        char[] ns=new char[s.length];
        for (int i=0; i<s.length; i++) {
            qa.add(i);
            ns[i]=s[i];  // initial state, edge test case
        }
        int vis[]=new int[s.length];
        Arrays.fill(vis, 0);  // an efficient to add only modified virus to qb once
        int count=0;
        while (true) {
            count++;
            while (!qa.isEmpty()) {
                int i=qa.poll();         
                // only need to check virus within distance 9
                for (int j=max(0, i-9); j<min(i+9, s.length); j++) {
                    if (s[i]-s[j]>=abs(i-j) && ns[j]<s[i]) {
                        ns[j]=s[i];
                        if ( vis[j] != count) {
                            qb.add(j);
                            vis[j] = count;
                        }
                    }
                }  
            }
            if (qb.isEmpty()) {
                count--;
                break;
            }
            qa.clear();
            while (!qb.isEmpty()) {
                int b=qb.poll();
                s[b]=ns[b];// update s
                qa.add(b);
            }
            //out.println(s);
            qb.clear();
        }
        out.println(count);
    }
    static void bruteforce(String v)
    {
        char[] s=v.toCharArray();
        char[] ns=new char[s.length];
        for (int i=0; i<s.length; i++) {
            ns[i]=s[i];
        }
        int ans=0;
        boolean change=false;
        do {
            for (int i=0; i<v.length(); i++) {
                // for each virus at i, check its +- 9 neighbors to see it it can mutate them
                for (int j=max(0, i-9); j<min(i+9, s.length); j++) {
                    if (s[i]-s[j]>=abs(i-j) && ns[j]<s[i]) {
                        ns[j]=s[i];
                        change=true;
                    }
                }
            }
            if ( change )
                ans++;
            else 
                break;
            //out.println(Arrays.toString(ns));
            change=false;        
            for (int i=0; i<v.length(); i++) {
                s[i]=ns[i];  // copy ns to s
                if ( ns[i]!=ns[0] )
                    change=true;  // still some difference
            }
        } while (change);
        out.println(ans);
    }
    static void test()
    {
        bruteforce("555755555");        // 3
        out.print("bfs ");        bfs("555755555"); 
        bruteforce("311110000000000");  // 6
        out.print("bfs ");        bfs("311110000000000"); 
        bruteforce("07788000744");      // 4
        out.print("bfs ");        bfs("07788000744"); 
        bruteforce("7777");
        out.print("bfs ");        bfs("7777"); 
        bruteforce("1");
        out.print("bfs ");        bfs("1"); 
        bruteforce("10");
        out.print("bfs ");        bfs("10"); 
        bfs("87700010010");
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        int TC = sc.nextInt();  // between 1 and 3
        for (int i=0; i<TC; i++) {
            String s = sc.next(); //1 ≤ N ≤ 150,000
            bfs(s);
        }
    }
}
