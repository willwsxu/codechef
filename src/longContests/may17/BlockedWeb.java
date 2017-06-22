package longContests.may17;


/* Brief Description: Adding prefix filter to block certain web sites from list of N
 * website names are all lower case letters, each name is unique
 * Use Trie and match prefix of unblocked sites
 * if a blocked name has a complete match, filter scheme won't work as it will block the unblocked
*/
// WSITES01, easy
import codechef.Trie26;
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
            if (match==blocked.get(i).length()) {// blocked site match fully to approved
                possible=false;
                break;
            } else {
                ans.add(blocked.get(i).substring(0, match+1)); // filter string
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
