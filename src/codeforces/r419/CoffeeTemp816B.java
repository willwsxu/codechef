package codeforces.r419;


import codechef.MyScanner;
import static java.lang.System.out;

// contest #419 div 2
// n recipes describe optimal temperature between l and r
// a temp is good if it is referenced by k recipes
// query how many good temperature between a and b
// IDEA use difference count, CNTl +=1, CNTr+1 -=1
// calculate count of temp after all recipes, find count of each temp
// calculate prefix sum of count>k
public class CoffeeTemp816B {
    static final int MAX_TEMP=200000;
    int t[]=new int[MAX_TEMP+5];
    CoffeeTemp816B()
    {
        //n, k (1 ≤ k ≤ n ≤ 200000), and q (1 ≤ q ≤ 200000)
        int n=sc.ni();
        int k=sc.ni();
        int q=sc.ni();
        for (int i=0; i<n; i++) {  //1 ≤ li ≤ ri ≤ 200000
            t[sc.ni()] += 1;
            t[sc.ni()+1] -= 1;
        }
        //out.println(Arrays.toString(t));
        for (int i=2; i<=MAX_TEMP+1; i++) { 
            t[i] += t[i-1];
        }
        //out.println(Arrays.toString(t));
        prefix = new long[MAX_TEMP+1];
        for (int i=1; i<=MAX_TEMP; i++) { 
            prefix[i] = prefix[i-1];
            if (t[i]>=k)
                prefix[i]++;
        }
        //out.println(Arrays.toString(prefix));
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<q; i++) {
            sb.append(query(sc.ni(), sc.ni()));
            sb.append("\n");
        }
        out.print(sb.toString());
    }
    long prefix[];
    long query(int a, int b)
    {
        return prefix[b]-prefix[a-1];
    }
    
    static MyScanner sc = new MyScanner();
    public static void main(String[] args)
    {
        new CoffeeTemp816B();
    }
}
