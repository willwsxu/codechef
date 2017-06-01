
import static java.lang.System.out;
import java.util.Scanner;
import java.util.Stack;


class SnakeElection {
    
    static void solve(String sm)
    {
        int m=0;
        int s=0;
        Stack<Character> stack = new Stack<>();
        for (int i=0; i<sm.length(); i++) {
            if (sm.charAt(i)=='s') {
                if (stack.isEmpty() || stack.peek() !='m') {
                    stack.add('s');
                    s++;
                } else {
                    stack.pop();
                    stack.add('M');
                }
            } else {
                m++;
                if (stack.isEmpty() || stack.peek()!='s') 
                    stack.add('m');
                else {
                    stack.pop();
                    s--;
                    stack.add('M'); // already eat
                } 
            }
        }
        if (m>s)
            out.println("mongooses");
        else if (m<s)
            out.println("snakes");
        else
            out.println("tie");
    }
    static void manualtest()
    {
        solve("s");  
        solve("m");
        solve("ms");   
        solve("sm");   
        solve("mss");   
        solve("msss"); 
        solve("mmsss"); 
        solve("mm");
        solve("ssssmmsm");  // tie, special case
    }
    static void autotest()
    {
        int T=sc.nextInt();     // 1 ≤ T ≤ 100
        for (int i=0; i<T; i++) {
            String s=sc.next();
            solve(s);
        }
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        autotest();
    }
}
