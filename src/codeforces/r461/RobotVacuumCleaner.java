package codeforces.r461;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/*
 * given a list of strings with s or h, find the number of maximum subsequence of sh
 * input: 1 ≤ n ≤ 10^5, total length does not exceed 10^5
 */

public class RobotVacuumCleaner {
    
    static long score(String s)
    {
        int countS=0;
        long total=0;  // use type long to avoid overflow
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i)=='s') {
                countS++;
            } else {
                total += countS;
            }
        }
        return total;
    }
    static long solve(ArrayList<String> sh) {
        // sort string by comparing score of two ways of concatenations
        // greedy method, if optimal solution for two strings are also optimal for all strings sorted in this order
        Comparator<String> cmp=(c1,c2)->{
            long sc1 = score(c1+c2);
            long sc2 = score(c2+c1);
            return sc1<sc2?-1:(sc1==sc2?0:1); // take care long to int overflow
        };
        Collections.sort(sh, cmp);
        Collections.reverse(sh);
        int countS=0;
        long total=0;
        for (String s : sh) {
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i)=='s') {
                    countS++;
                } else {
                    total += countS;
                }
            }
            //System.out.println(s+" score="+total);
        }
        return total;
    }
    static void test()
    {
        ArrayList<String> str=new ArrayList<>();
        str.add("ssh");
        str.add("hs");
        str.add("s");
        str.add("hhhs");
        System.out.println(solve(str));        
    }
    static void test2()
    {
        ArrayList<String> str=new ArrayList<>();
        str.add("h");
        str.add("s");
        str.add("hhh");
        str.add("h");
        str.add("ssssss");
        str.add("s");
        System.out.println(solve(str));        
    }
    static void judge()
    {
        ArrayList<String> str=new ArrayList<>();
        int n=sc.nextInt();
        while (n-->0) {
            str.add(sc.next());
        }
        System.out.println(solve(str));        
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        judge();
    }
}
