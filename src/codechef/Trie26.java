/*
 * Trie node with 26 child
 */
package codechef;

import static java.lang.System.out;


public class Trie26  // for 26 lower case letters
{
    private static int  R=26;
    private static char base='a';
    
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
        int n=k.charAt(d)-base;
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
        if (x.next[key.charAt(d)-base]==null)
            return d;
        return prefixMatch(x.next[key.charAt(d)-base], key, d+1);
    }
    
    public static void test()
    {
        Trie26 tr = new Trie26();
        tr.put("google");
        out.println(tr.prefixMatch("goo"));
        out.println(tr.prefixMatch("pi"));
    }
}
