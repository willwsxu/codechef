/*
 * start with k identicl string, swap exactly 2 char in each string, s1...sk
 * find a string that can be transformed to s1 to sk per rule above
 * string len=n. 1 ≤ k ≤ 2500, 2 ≤ n ≤ 5000, k · n ≤ 5000
 */
package codeforces.edu34;

import static java.lang.System.out;
import java.util.ArrayList;
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
            String ans = unique.first();  // sawp last 2 char
            return ans.substring(0, ans.length()-2)+ans.charAt(ans.length()-1)+ans.charAt(ans.length()-2);
        }
        return "-1";
    }
    static void test()
    {
        ArrayList<String> in=new ArrayList<>();
        in.add("te");
        out.println(new SwappingChar(in).solve());
    }
    public static void main(String[] args)
    {      
        test();
    }
}
