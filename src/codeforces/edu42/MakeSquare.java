package codeforces.edu42;


import codechef.IOR;

/*
 * edu42 C: remove digits to make it a perfect square
 */

public class MakeSquare {
    
    int match(String s1, String s2)
    {
        int[] matchIndex=new int[s2.length()];
        int from=s1.length()-1;
        for (int i=s2.length()-1; i>=0; i--) {
            if (i<s2.length()-1)
                from = matchIndex[i+1]-1;
            matchIndex[i]=s1.lastIndexOf(s2.charAt(i), from);
            if (matchIndex[i]<0)
                return -1;
        }
        int ans=s1.length()-s2.length();
        /*for (int i=0; i<matchIndex[0]; i++)
            if (s1.charAt(i)=='0')
                ans--;*/ //include any leading 0
        return ans;
    }
    int solve(int N)
    {
        int ans=10;
        String nstr=Integer.toString(N);
        for (int i=1; i<=N; i++) {
            int sq=i*i;
            if (sq>N)
                break;
            int r=match(nstr, Integer.toString(sq));
            if (r>=0) {
                //System.out.println(nstr+" match "+sq+" ans="+r);
                ans=Integer.min(ans, r);
            }
        }
        return ans==10?-1:ans;
    }
    static void test()
    {
        System.out.println(new MakeSquare().solve(8314));
        System.out.println(new MakeSquare().solve(625));
        System.out.println(new MakeSquare().solve(333));    
        System.out.println(new MakeSquare().solve(101));    
    }
    public static void main(String[] args)
    {
        System.out.println(new MakeSquare().solve(IOR.ni()));
    }
}
