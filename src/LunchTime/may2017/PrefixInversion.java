package LunchTime.may2017;


import static java.lang.System.out;
import java.util.Scanner;

/*
 flip binary string from left only, till all 0
 find 1 from right, flip once, 
 keep going to left but now we only flip if it is 0
 repeat until last char
 */
// PREFINVS simple
class PrefixInversion {
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        String s=sc.next(); //01001001: 6
        int flips=0;
        int flip=1;
        for (int i=s.length()-1; i>=0; i--) {
            if (s.charAt(i)=='0'+flip) { // match flipper from right, start with '1'
                flip =1-flip;  // toggle flipper from '1' to '0'
                flips++;
            }// skip char if no flip is needed
        }
        out.println(flips);
    }
}
