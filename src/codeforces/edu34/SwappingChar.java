/*
 * start with k identical string, swap exactly 2 char in each string, s1...sk
 * find a string that can be transformed to s1 to sk per rule above
 * string len=n. 1 ≤ k ≤ 2500, 2 ≤ n ≤ 5000, k · n ≤ 5000
 */
package codeforces.edu34;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class SwappingChar {
    
    TreeSet<String> unique=new TreeSet<>();
    SwappingChar(List<String> ss )
    {
        for(String s:ss)
            unique.add(s);
    }
    String solve()
    {
        if (unique.size()==1) {
            String ans = unique.first();  // swap last 2 char
            return ans.substring(0, ans.length()-2)+ans.charAt(ans.length()-1)+ans.charAt(ans.length()-2);
        }
        // compare 1st string to rest, validate same length, diff position is <=4, chars are same
        String[] u= unique.toArray(new String[unique.size()]);
        for (int i=1; i<u.length; i++) {
            if (u[0].length()!=u[i].length())  // string must be of same length
                return "-1";
            ArrayList<Character> diff1=new ArrayList<>();
            ArrayList<Character> diff2=new ArrayList<>();
            int count=0;
            for (int j=0; j<u[0].length(); j++) {
                if (u[0].charAt(j) !=u[i].charAt(j)) {
                    diff1.add(u[0].charAt(j));
                    diff2.add(u[i].charAt(j));
                    if (++count>4)  // difference char position should exceed 4
                        return "-2";
                }
            }
            Collections.sort(diff1);  // char that diffs between 2 string must be same
            Collections.sort(diff2);
            for (int k=0; k<diff1.size(); k++) {
                if (diff1.get(k)!=diff2.get(k))
                    return "-3";
            }
        }
        return "-5";
    }
    static void test()
    {
        ArrayList<String> in=new ArrayList<>();
        in.add("te");
        out.println(new SwappingChar(in).solve());
        in.add("ab");
        out.println(new SwappingChar(in).solve());
        in.set(1, "tetete");
        out.println(new SwappingChar(in).solve());
        in.set(0, "etetet");
        out.println(new SwappingChar(in).solve());
    }
    public static void main(String[] args)
    {      
        test();
    }
}
