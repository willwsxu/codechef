
import java.util.Scanner;


class CookOffMar17Cards {
    
    static Scanner scan = new Scanner(System.in);
    double score(int redCards, int blueCards, int redTokens, int blueTokens)
    {
        return 0.0;
    }
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int r = scan.nextInt();  // between 1 to 100000
            int b = scan.nextInt();
            int p = scan.nextInt();  // between 0 and r=b
        }
    }
    public static void main(String[] args)
    {
        scan = codechef.CodeChef.getFileScanner("cards0317.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
