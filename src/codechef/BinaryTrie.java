package codechef;

import static java.lang.System.out;

// Cool persistent feature
public class BinaryTrie  // for int
{    
    private static int  R=2;
    private static int  w=31; // depth for int, 31 bits
    
    private Node root=new Node();
    private static class Node
    {
        String  name;
        int     val=0;
        private Node[] child = new Node[R];
    }
    public boolean add(int ix)
    {
        //out.println("trie add "+ix);
        Node u = root;
        boolean a=false;
        for (int i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[c]==null) {
                u.child[c]=new Node();
                a=true;
            }
            u=u.child[c];
        }
        u.val=ix;
        return a;
    }
    
    public BinaryTrie persistAdd(int ix)
    {
        //out.println("trie persistAdd "+ix);
        BinaryTrie bt=new BinaryTrie();
        bt.root.child[0]=root.child[0];
        bt.root.child[1]=root.child[1];
        
        Node u = bt.root;
        for (int i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            Node n=new Node();
            if (u.child[c]==null) {
                u.child[c]=n;
            } else {
                n.child[0]=u.child[c].child[0];
                n.child[1]=u.child[c].child[1];
                u.child[c]=n;                
            }
            u=u.child[c];
        }
        u.val=ix;
        return bt;
    }
    public boolean find(int ix)
    {        
        Node u = root;
        int i=0;
        for (i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[c] == null) break;
            u = u.child[c];
        }
        return i==w;
    }
    public int xorMin(int ix)
    {
        Node u = root;
        int i=0;
        for (i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[c] == null) 
                c=1-c;
            u = u.child[c];
            if (u==null)
                return 0;
        }
        return u.val;        
    }
    public int xorMax(int ix)
    {
        Node u = root;
        int i=0;
        for (i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[1-c] != null) 
                c=1-c;
            u = u.child[c];
            if (u==null)
                return 0;
        }
        return u.val;        
    }
    static void test()
    {
        BinaryTrie bt=new BinaryTrie();
        out.println(bt.find(1)==false);
        out.println(bt.add(1)==true);
        out.println(bt.find(1)==true);
        out.println(bt.add(5)==true);
        out.println(bt.add(5)==false);
        out.println(bt.find(5)==true);
        out.println(bt.add(6)==true);
        out.println(bt.add(11)==true);
        out.println(bt.add(20)==true);
        out.println(bt.add(22)==true);
        out.println(bt.add(26)==true);
        out.println(bt.xorMin(13)==11);
        out.println(bt.xorMax(13)==22);
        out.println(bt.add(14)==true);
        out.println(bt.add(18)==true);
        out.println(bt.xorMin(13)==14);
        out.println(bt.xorMax(13)==18);
    }
    static void test2()
    {
        out.println("persistent trie");
        BinaryTrie bt=new BinaryTrie();
        out.println(bt.add(1)==true);
        BinaryTrie bt2=bt.persistAdd(5);
        BinaryTrie bt3=bt2.persistAdd(6);
        BinaryTrie bt4=bt3.persistAdd(11);
        BinaryTrie bt5=bt4.persistAdd(20);
        BinaryTrie bt6=bt5.persistAdd(22);
        BinaryTrie bt7=bt6.persistAdd(26);
        out.println(bt.find(5)==false);
        out.println(bt2.find(6)==false);
        out.println(bt3.find(11)==false);
        out.println(bt4.find(20)==false);
        out.println(bt5.find(22)==false);
        out.println(bt6.find(26)==false);
        out.println(bt6.find(22)==true);
        out.println(bt6.find(20)==true);
        
        out.println(bt7.find(1)==true);
        out.println(bt7.find(5)==true);
        out.println(bt7.find(6)==true);
        out.println(bt7.find(11)==true);
        out.println(bt7.find(20)==true);
        out.println(bt7.find(22)==true);
        out.println(bt7.find(26)==true);
        out.println(bt7.xorMin(13)==11);
        out.println(bt7.xorMax(13)==22);
    }
    static void test3()
    {
        out.println("persistent trie");
        BinaryTrie bt=new BinaryTrie();
        out.println(bt.add(2)==true);
        BinaryTrie bt5=bt.persistAdd(3);
        BinaryTrie bt2=bt.persistAdd(4);
        BinaryTrie bt3=bt2.persistAdd(5);
        BinaryTrie bt4=bt2.persistAdd(1);
        BinaryTrie bt6=bt3.persistAdd(3);     
        out.println(bt4.xorMin(2));  
        out.println(bt4.xorMax(2));
    }
}