package longContests.may17;

/*
 * Brief Desc: Chef routine for a day, Cook, Eat,  Sleep
*/
import static java.lang.System.out;
import java.util.Scanner;

// CHEFROUT beginner
class ChefRoutine {
    
    // valid string pattern Cook Eat Sleep
    static String isValid(String N)
    {
        // state C->E, C->S, E->S
        char state = N.charAt(0);
        for (int i=1; i<N.length(); i++) {
            char next = N.charAt(i);
            switch (state) {
                case 'C':
                    break;
                case 'E':
                    if (next=='C')
                        return "no";
                    break;
                case 'S':
                    if (next!='S')
                        return "no";
                    break;
                default:
                    out.println("Error bad state"+state);
                    return "no";
            }
            state = next;
        }
        return "yes";
    }
    static void test()
    {
        out.println(isValid("C"));    // yes
        out.println(isValid("E"));    // yes
        out.println(isValid("S"));    // yes 
        out.println(isValid("CESE"));     // no

        out.println(isValid("CES"));    // yes
        out.println(isValid("CS"));     // yes
        out.println(isValid("CCC"));    // yes
        out.println(isValid("SC"));     // no
        out.println(isValid("ECCC"));   // no  
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {        
        int TC = sc.nextInt();  // between 1 and 20
        for (int i=0; i<TC; i++) {
            String N = sc.next();   // 1 ≤ len(N) ≤ 10^5
            out.println(isValid(N));
        }
    }
}
