package longContests.july17;

// Wrong answer here. See python code
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Random;
import java.util.StringTokenizer;


class ChefSign {
    
    static int count(String s)
    {
        int minv=1;
        int maxv=1;
        int val=1;
        for (int i=0; i<s.length(); i++) {
            switch (s.charAt(i)) {
                case '<':   val++;  break;
                case '>':   val--;  break;
            }
            if ( val>maxv)
                maxv=val;
            if (val<minv)
                minv=val;
        }
        out.println("min "+minv+" max "+maxv);
        print(s, minv<1?2-minv:1, maxv-minv+1);
        return maxv-minv+1;
    }
    static int count2(String s)
    {
        int count1=0;
        int count2=0;
        for (int i=0; i<s.length(); i++) {
            switch (s.charAt(i)) {
                case '<':   count1++;  break;
                case '>':   count2++;  break;
            }
        }
        if (count1==count2) {
            if (count1==0)
                return 1;
            else
                return 2;
        }
        int diff=count1-count2;
        return diff>0?diff+1:-diff+1;
    }
    static void print(String s, int start, int P)
    {
        StringBuilder sb=new StringBuilder();
        sb.append(start);
        int minv=start;
        int maxv=start;
        for (int i=0; i<s.length(); i++) {
            sb.append(s.charAt(i));
            switch (s.charAt(i)) {
                case '<':   start++;  break;
                case '>':   start--;  break;
            }
            sb.append(start);  
            if ( start>maxv )
                maxv=start;
            if (start<minv)
                minv=start;
        } 
        //out.println(sb.toString());
        if (minv !=1)
            out.println("wrong min "+minv);
        if (maxv !=P)
            out.println("wrong max "+maxv);
    }
    
    static String tetscase(int count, boolean random, char sign)
    {        
        Random rand=new Random();
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<count; i++) {
            if (!random) {
                sb.append(sign);
                continue;
            }
            int w=rand.nextInt(3);
            switch(w){
                case 0:
                    sb.append('=');
                    break;
                case 1:
                    sb.append('<');
                    break;
                case 2:
                    sb.append('>');
                    break;
            }
        }
        return sb.toString();
    }
    static void genTestfile(String line, boolean append)
    {        
        try (PrintWriter writer = 
                new PrintWriter(
                new FileWriter(
                new File("test.out.txt"), append)); ) 
        {
            writer.println(line);
        }
        catch (IOException e)
        {
            out.println("genTestfile exception");
        }        
    }
    static void test()
    {
        out.println(count(tetscase(100000, false, '=')));
        out.println(count(tetscase(100000, false, '<')));
        out.println(count(tetscase(100000, false, '>')));
        out.println(count(tetscase(100000, true, '?')));
        out.println(count(">>>>>>>>>>>>>>>>><<<<<<<><><><<><><>>><<>><<<>><><><><><<><><><><><><><>>>>>"));
        out.println(count("===>>>>>>>>>>=>><<===<<><><><><><><><>>>>>==="));
        out.println(count("===>>>>>>>>>>=>><<===<<><><><><><><><>>>>>==="));
        out.println(count("="));
        out.println(count(">"));
        out.println(count("<"));
        out.println(count("<=<>>>"));
    }
    static void test2()
    {
        genTestfile(tetscase(100000, false, '='),false);
        genTestfile(tetscase(100000, false, '<'),true);
        genTestfile(tetscase(100000, false, '>'),true);
        genTestfile(tetscase(100000, true, '?'),true);
        genTestfile(tetscase(10000, true, '?'),true);
        genTestfile(tetscase(1000, true, '?'),true);
    }
    static void solve()
    {
        int T=sc.ni();
        while (T-->0)
            out.println(count(sc.next()));
    }
    static MyScanner sc = new MyScanner();
    public static void main(String[] args)
    {      
        //sc = new MyScanner("test.sign.txt");
        solve();
    }
}

class MyScanner {
    BufferedReader br;
    StringTokenizer st;

    MyScanner(String f)
    {
        try {
            br = new BufferedReader(new FileReader(new File(f)));
        } catch (IOException e)
        {
            out.println("MyScanner bad file "+f);
        }
    }
    public MyScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    String nextLine(){
        String str = "";
        try {
           str = br.readLine();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return str;
    }
    
    public int ni()
    {
        return nextInt();
    }     
}