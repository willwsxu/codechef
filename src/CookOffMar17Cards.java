
import codechef.ContestHelper;
import static java.lang.System.out;
import java.util.Scanner;

// Linearity of expectation
class CookOffMar17Cards {
    
    static Scanner scan = new Scanner(System.in);
    //=(p∗r+(r+b−p)∗b)/(r+b)
    static double score(int redCards, int blueCards, int redTokens, int blueTokens)
    {
        // cast to long type to avoid overflow of r*b
        double num = (long)redCards*redTokens+(long)blueTokens*blueCards;
        return num/(redCards+blueCards);
    }
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int r = scan.nextInt();  // between 1 to 100000
            int b = scan.nextInt();
            int p = scan.nextInt();  // between 0 and r+b
            out.printf("%.10f\n", score(r, b, p, r+b-p));
        }
    }
    public static void main(String[] args)
    {
        scan = ContestHelper.getFileScanner("cards0317.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
