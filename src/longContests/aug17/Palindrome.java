package longContests.aug17;


import static java.lang.System.out;
import java.util.Scanner;

// easy, limited cases
// 
class Palindrome {
    static final int MAX_LETTERS=26;
    int letterCout1[]=new int[MAX_LETTERS];
    int letterCout2[]=new int[MAX_LETTERS];
    int letterCout[]=new int[MAX_LETTERS];
    
    void count(int c[], String s )
    {
        for (int i=0; i<s.length(); i++)
            c[s.charAt(i)-'a']++;
    }
    Palindrome(String s, String t)
    {
        count(letterCout1, s);
        count(letterCout2, t);
        for (int i=0; i<MAX_LETTERS; i++)
            letterCout[i] = letterCout1[i]+letterCout2[i];
    }
    Palindrome()
    {
        this(sc.next(),sc.next());
    }
    
    boolean solve()
    {
        for (int i=0; i<MAX_LETTERS; i++) {
            if (letterCout1[i]<2)
                continue;
            if (letterCout2[i]==0)
                return true;  // A win if A has a pair of letter that does not exist in B
        }
        // at this moment, A does not have a winning pair
        // next step search for a single letter in A, but not B
        for (int i=0; i<MAX_LETTERS; i++) {
            if (letterCout[i]!=1)
                continue;
            if (letterCout1[i]==0)
                continue;
            // find it
            // check if B has letter that does not exist in A
            for (int j=0; j<MAX_LETTERS; j++) {
                if (j==i)
                    continue;
                if (letterCout2[j]==0)
                    continue;
                if (letterCout1[j]!=0)
                    continue;
                //out.println("letter in B only "+j);
                return false;
            }
            //out.println("no letter in B only "+i);
            return true;
        }
        // all letter in A exist in B
        return false;  // A lose
    }
    static void test()
    {
        out.println(new Palindrome("ab","ab").solve()?"A":"B");
        out.println(new Palindrome("aba","cde").solve()?"A":"B");
        out.println(new Palindrome("ab","cd").solve()?"A":"B");
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) { 
            out.println(new Palindrome().solve()?"A":"B");
        }
    }
}
