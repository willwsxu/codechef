package codeforces.r471;


import codechef.IOR;
import java.util.Arrays;

// adorable string def, a string of 2 distinct symbols
// find out if a string can be split into 2 adorable string
public class BeautifulString {
    boolean beauti(String s)
    {
        if (s.length()<4)
            return false;
        int count[]=new int[26];
        Arrays.fill(count, 0);
        for (int i=0; i<s.length(); i++) {
            count[s.charAt(i)-'a']++;
        }
        int letters=0;
        int single=0;
        for (int i=0; i<count.length; i++) {
            if (count[i]>0)
                letters++;
            if (count[i]==1)
                single++;
        }
        if (letters>4)
            return false;
        if (letters>=3)  // 3 types of letter
            return true;
        if (letters==1)
            return false;
        return single==0;  // X YYY won't work
    }
    
    public static void main(String[] args)
    {      
        boolean ans=new BeautifulString().beauti(IOR.ns());
        System.out.println(ans?"Yes":"No");
    }
}