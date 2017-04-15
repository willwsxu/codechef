package CookOffMar17;


import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;


class CookOffMar17Robot {
        
    public String isSafe(int rows, int cols, String moves)
    {
        int r=0, c=0;
        int minR=0, minC=0, maxR=0, maxC=0;
        for (int i=0; i<moves.length(); i++) {
            switch(moves.charAt(i)){
                case 'U':
                    r--;
                    break;
                case 'D':
                    r++;
                    break;
                case 'L':
                    c--;
                    break;
                case 'R':
                    c++;
                    break;
                default:
                    return "unsafe";
            }
            if ( minR>r)
                minR=r;
            if ( maxR<r)
                maxR=r;
            if ( minC>c)
                minC=c;
            if (maxC<c)
                maxC=c;
        }
        if (maxR-minR>=rows || maxC-minC>=cols)
            return "unsafe";
        return "safe";
    }
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 10^3
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 to 10
            int M = scan.nextInt();
            String moves = scan.nextLine();
            if (moves.isEmpty())
                moves = scan.nextLine();
            out.println(new CookOffMar17Robot().isSafe(N, M, moves));
        }
    }
    public static void main(String[] args)
    {
        //scan = codechef.CodeChef.getFileScanner("robot.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
