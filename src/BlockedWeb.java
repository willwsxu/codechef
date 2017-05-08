
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


class BlockedWeb {
    
    static void solve(Trie26 tr, List<String> blocked)
    {
        boolean possible=true;
        Set<String> ans=new TreeSet<>();  // store unique answers
        for (int i=0; i<blocked.size(); i++) {
            int match = tr.prefixMatch(blocked.get(i));
            if (match==blocked.get(i).length()) {
                possible=false;
                break;
            } else {
                ans.add(blocked.get(i).substring(0, match+1));
            }
        }
        if (possible) {
            out.println(ans.size());
            for (String s:ans)
                out.println(s);
        }
        else
            out.println(-1);            
    }
    
    static void test()
    {
        Trie26 tr = new Trie26();
        tr.put("google");
        tr.put("codechef");
        List<String> blocked = new ArrayList<>();
        blocked.add("codeforces");
        blocked.add("codefool");
        solve(tr, blocked);
        blocked.add("codechill");
        solve(tr, blocked);
        blocked.add("youtube");
        blocked.add("yoyo");
        solve(tr, blocked);
        tr.put("codechefxyz");
        blocked.add("codechefu");
        solve(tr, blocked);
        blocked.add("codechefx");
        solve(tr, blocked);
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();/*
        int N = sc.nextInt();  // 1 ≤ N ≤ 2 * 10^5
        Trie26 tr=new Trie26();
        List<String> blocked = new ArrayList<>();
        for (int i=0; i<N; i++) {
            String cmd = sc.next();
            String site = sc.next();
            if (cmd.charAt(0)=='+')
                tr.put(site);
            else if (cmd.charAt(0)=='-')
                blocked.add(site);
            else
                out.println("Error bad input, expect + or -");
        }
        solve(tr, blocked);
    }
}

class Trie26  // for 26 lower case letters
{
    private static int R=26;
    private Node root;
    private static class Node
    {
        String name;
        private Node[] next = new Node[R];
    }
    public void put(String key)
    {
        root = put(root, key, 0);
    }
    
    private Node put(Node x, String k, int d)
    {
        if (x==null)
            x= new Node();
        if ( d==k.length()) {
            x.name=k;
            return x;
        }
        int n=k.charAt(d)-'a';
        x.next[n]=put(x.next[n], k, d+1);
        return x;
    }
    
    public int prefixMatch(String key)
    {
        return prefixMatch(root, key, 0);
    }
    
    public int prefixMatch(Node x, String key, int d)
    {
        if (x==null)
            out.println("Error node null, d="+d);
        if (d==key.length())
            return d;
        if (x.next[key.charAt(d)-'a']==null)
            return d;
        return prefixMatch(x.next[key.charAt(d)-'a'], key, d+1);
    }
    
    static void test()
    {
        Trie26 tr = new Trie26();
        tr.put("google");
        out.println(tr.prefixMatch("goo"));
        out.println(tr.prefixMatch("pi"));
    }
}
