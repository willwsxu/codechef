package longContests.aug17;


import static java.lang.System.out;
import java.util.Scanner;

// easy, limited cases
class Palindrome {
    static final int MAX_LETTERS=26;
    int letterCout1[]=new int[MAX_LETTERS];
    int letterCout2[]=new int[MAX_LETTERS];
    //int letterCout[]=new int[MAX_LETTERS];
    
    void count(int c[], String s )
    {
        for (int i=0; i<s.length(); i++)
            c[s.charAt(i)-'a']++;
    }
    Palindrome(String s, String t)
    {
        count(letterCout1, s);
        count(letterCout2, t);
        //for (int i=0; i<MAX_LETTERS; i++)
        //    letterCout[i] = letterCout1[i]+letterCout2[i];
    }
    Palindrome()
    {
        this(sc.next(),sc.next());
    }
    
    boolean inFristOnly(int []letters1, int[]letter2)
    {
        for (int j=0; j<MAX_LETTERS; j++) {
            if (letters1[j]==0)
                continue;
            if (letter2[j]!=0)
                continue;
            //out.println("letter in first not second "+j);
            return true;
        }
        return false;
    }
    boolean solve()
    {
        boolean bInBOnly=inFristOnly(letterCout2, letterCout1);
        for (int i=0; i<MAX_LETTERS; i++) {
            if (letterCout1[i]==0)
                continue;
            if (letterCout2[i]!=0)
                continue;
            // find a letter in A only
            if ( !bInBOnly ) // all letters in B exist in A, B lose
                return true;
            // B has letter that does not exist in A
            if (letterCout1[i]>1)
                return true;  // A can only if it has winning pair
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
