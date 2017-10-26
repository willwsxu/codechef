package smackdown.qualifier2017.roundb;
/*
 * Brief Description: snake and mongoose line up to cast vote, each mongoose will eat at most one of its neighbor snakes
 * find out which animal would win
 * Strategy: Greedy
 * mongoose will eat snake to its left if it exist, otherwise it will eat snake to its right
 * Caveat: a snake can not be eaten twice
 *   1. use a stack to keep s and m, change m to M once it eats, pop out the eaten snake
 *   2. use array or list to store s and m, change s to S if snake is eaten
*/

import static java.lang.System.out;
import java.util.Scanner;
import java.util.Stack;

// SNELECT, EASY
class SnakeElection {
    
    static void greedy(String sm)
    {
        int m=0;
        int s=0;
        Stack<Character> stack = new Stack<>();
        for (int i=0; i<sm.length(); i++) {
            if (sm.charAt(i)=='s') {
                if (stack.isEmpty() || stack.peek() !='m') {
                    stack.add('s');
                    s++;
                } else {  // eaten by previous m
                    stack.pop();
                    stack.add('M');  // mark it as eaten
                }
            } else {
                m++;
                if (stack.isEmpty() || stack.peek()!='s') 
                    stack.add('m'); // ready to eat rigth snake
                else {
                    stack.pop();    // eat left snake
                    s--;
                    stack.add('M'); // mark it as eaten
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
        greedy("s");  
        greedy("m");
        greedy("ms");   
        greedy("sm");   
        greedy("mss");   
        greedy("msss"); 
        greedy("mmsss"); 
        greedy("mm");
        greedy("ssssmmsm");  // tie, special case, snake should not be eaten twice
    }
    static void autotest()
    {
        int T=sc.nextInt();     // 1 ≤ T ≤ 100
        for (int i=0; i<T; i++) {
            String s=sc.next();
            greedy(s);
        }
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        autotest();
    }
}
