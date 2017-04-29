package ChallengeApr17;


import static java.lang.System.out;
import java.util.Scanner;

//ROWSOLD easy / medium
// be careful of overflow, use long c1, not int
class Apr17BearRow1 {
    
    // move from left to right to prolong the game
    static long solve(String cells)
    {
        int pos=0;
        for (; pos< cells.length(); pos++)
            if (cells.charAt(pos)=='1')
                break;
        // skipped all leading 0, nothing left or just a single 1
        if (pos>=cells.length()-1)
            return 0;
        long c1=0; // count 1's
        int c0=0; // count 0
        long tim=0; // time to play game
        for (; pos< cells.length(); pos++)
        {
            if (cells.charAt(pos)=='1') {
                if (c0>0) {
                    tim += c1*(c0+1);
                    //out.println("c1="+c1+" c0="+c0+" tim= "+tim);
                    c0=0;
                }
                c1++; // accumulate 1's
            }
            else
                c0++;            
        }
        if (c0>0) {
            tim += c1*(c0+1);
            //out.println("c1="+c1+" c0="+c0+" tim= "+tim);
        }
        return tim;
    }
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        int TC = scan.nextInt();  // between 1 and 5
        for (int i=0; i<TC; i++) {
            String line1 = scan.nextLine();  // length 10^5
            if (line1.isEmpty())
                line1 = scan.nextLine();
            out.println(solve(line1));
        }
    }
}
