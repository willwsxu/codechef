package codeforces.edu34;
/*
 * start with k identical string, swap exactly 2 char in each string, s1...sk
 * find a string that can be transformed to s1 to sk per rule above
 * string len=n. 1 ≤ k ≤ 2500, 2 ≤ n ≤ 5000, k · n ≤ 5000
 */

import codechef.IOR;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SwappingChar {
    
    String[] unique;
    SwappingChar(List<String> ss )
    {
        TreeSet<String> u=new TreeSet<>();
        for(String s:ss)  // only store unique string
            u.add(s);
        unique = u.toArray(new String[u.size()]);
    }
    private String swap(String s, int pos1, int pos2)  // sway char at 2 pos
    {        
        StringBuilder sb=new StringBuilder();
        sb.append(s);
        sb.setCharAt(pos1, s.charAt(pos2));
        sb.setCharAt(pos2, s.charAt(pos1));
        return sb.toString();
    }
    String solve()
    {
        if (unique.length==1) {  // all string are same, swap any 2 char
            String ans = unique[0];  // swap last 2 char
            return ans.substring(0, ans.length()-2)+ans.charAt(ans.length()-1)+ans.charAt(ans.length()-2);
        }
        // compare 1st string to rest, validate same length, diff position is <=4, chars are same
        for (int i=1; i<unique.length; i++) {
            if (unique[0].length()!=unique[i].length())  // string must be of same length
                return "-1";
            ArrayList<Character> diff1=new ArrayList<>();
            ArrayList<Character> diff2=new ArrayList<>();
            int count=0;
            for (int j=0; j<unique[0].length(); j++) {
                if (unique[0].charAt(j) !=unique[i].charAt(j)) {
                    diff1.add(unique[0].charAt(j));
                    diff2.add(unique[i].charAt(j));
                    if (++count>4)  // difference char position should NOT exceed 4
                        return "-1";
                }
            }
            Collections.sort(diff1);  // char that diffs between 2 string must be same
            Collections.sort(diff2);
            for (int k=0; k<diff1.size(); k++) {
                if (!diff1.get(k).equals(diff2.get(k)))
                    return "-1";
            }
        }
        // find position of diff char of first 2 string
        ArrayList<Integer> diff1=new ArrayList<>();
        for (int j=0; j<unique[0].length(); j++) {
            if (unique[0].charAt(j) !=unique[1].charAt(j))
                diff1.add(j);
        }
        TreeSet<String> candidates=new TreeSet<>();
        for (int k=0; k<diff1.size(); k++) {
            //for (int j=k+1; j<diff1.size(); j++) {  not enough just to swap among diff pos
            for (int j=0; j<unique[0].length(); j++) {  // swap diff with any char in string
                candidates.add(swap(unique[0], j, diff1.get(k)));
                //candidates.add(swap(unique[0], diff1.get(j), diff1.get(k)));
            }
        }
        //out.println(Arrays.toString(u));
        //out.println(diff1);
        //out.println(candidates);
        boolean distinct=unique[0].length()<=26; // string letter not distinct if > 26
        if (distinct) {
            Set<Character> letters=new HashSet<>();
            for (int i=0; i< unique[0].length(); i++)
                letters.add(unique[0].charAt(i));
            distinct=unique[0].length()==letters.size(); // no letter same in a string <=26
        }
        for (String s: candidates) {  // test each answer candidate again all string
            int i=0;
            for (; i<unique.length; i++) {
                int count=0;
                for (int j=0; j<s.length(); j++) {
                    if (s.charAt(j) !=unique[i].charAt(j))
                        count++;  
                }
                if (count !=0 && count !=2)  // diff pos must be 0 or 2
                    break;    
                if (count==0 && distinct)
                    break;  // diff cannot be 0 if all letters are distinct
            }
            if (i==unique.length)  // found answer
                return s;
        }
        return "-1";
    }
    static void test()
    {
        ArrayList<String> in=new ArrayList<>();
        in.add("te");
        out.println(new SwappingChar(in).solve()); // et
        in.add("ab");
        out.println(new SwappingChar(in).solve()); // -1
        in.set(1, "tetete");
        out.println(new SwappingChar(in).solve()); // -1
        in.set(0, "etetet");
        out.println(new SwappingChar(in).solve()); // -1
        in.set(0, "abac");
        in.set(1, "caab");
        in.add("acba");
        out.println(new SwappingChar(in).solve()); // acab
        in.set(0, "kbbu");
        in.set(1, "kbub");
        in.set(2,"ubkb");
        out.println(new SwappingChar(in).solve()); // kbub
        in.set(0, "clo");
        in.set(1, "col");
        in.set(2, "lco");
        out.println(new SwappingChar(in).solve()); // -1
        in.set(0, "eellh");
        in.set(1, "ehlle");
        in.set(2, "helle");
        in.add("hlele");
        out.println(new SwappingChar(in).solve()); // helle
    }
    public static void main(String[] args)
    {      
        judge();
    }
    static void judge()
    {        
        int k=IOR.ni();  // 1 to2500 
        int n=IOR.ni(); // 2 to 5000
        ArrayList<String> s=new ArrayList<>(k);
        for (int i=0; i<k; i++) {
            s.add(IOR.ns());
        }
        out.println(new SwappingChar(s).solve());
    }
}