
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

class LunchMar17Species {
    char [][]board;
    
    char bear(char [][]x, int i, int j)
    {
        switch(x[i][j]) {
            case '.':
            case '?':
                return 0;
            default:
                return x[i][j];
        }
    }     
    char validate(char[][]x, int i, int j)
    {
        char neighbor=0;
        if (i>0) {
            char b = bear(x, i-1, j);
            if (b>0) {
                if (neighbor>0)
                    return 0; // ? is between two different bears
                if (b=='G')
                    return 0;
                neighbor=b;
            }
        }
        if (i<x.length-1) {
            char b = bear(x, i+1, j);
            if (b>0) {
                if (neighbor>0)
                    return 0; // ? is between two different bears
                if (b=='G')
                    return 0;
                neighbor=b;
            }
        }
        if (j>0) {
            char b = bear(x, i, j-1);
            if (b>0) {
                if (neighbor>0)
                    return 0; // ? is between two different bears
                if (b=='G')
                    return 0;
                neighbor=b;
            }            
        }
        if ( j<x.length-1 ) {
            char b = bear(x, i, j+1);
            if (b>0) {
                if (neighbor>0)
                    return 0; // ? is between two different bears
                if (b=='G')
                    return 0;
                neighbor=b;
            }                        
        }
        return neighbor>0?neighbor:'?';
    }
    void print()
    {
        for(char[] x: board)
            out.println(Arrays.toString(x));
    }
    
    int calcM(char [][]board, int i, int j, int m)
    {
        if (board[i][j] =='?') {
            board[i][j]='X';
            if (m>2)
                m=2;
        }else if (board[i][j] =='X') {
            m=1;
        }
        return m;
    }
    int multiplier(char [][]board, int i, int j)
    {
        int N = board.length;
        int m=3;
        if ( i>0 )
            m = calcM(board, i-1, j, m);
        if ( i<N-1 )
            m = calcM(board, i+1, j, m);
        if ( j>0 )
            m = calcM(board, i, j-1, m);
        if ( j<N-1 )
            m = calcM(board, i, j+1, m);
        if (m != 3)
            board[i][j]='X';
        return m;
    }
    LunchMar17Species(String [] x)
    {
        int N = x.length;
        board = new char[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                board[i][j]=x[i].charAt(j);
            }
        }
        //print();
        boolean notdone=true;
        while(notdone) {
            notdone =false;
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    switch(board[i][j]) {
                        case '?':
                            char b = validate(board, i, j);
                            if (b==0) {
                                out.println("0");
                                //print();
                                return;
                            }
                            notdone = board[i][j]!=b;
                            board[i][j]=b;
                            break;
                    }                     
                }
            }
            //print();
        }
        long total=1;
        outerfor:
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                switch(board[i][j]) {
                    case '?':
                        total *= multiplier(board, i, j);
                        if (total >= MOD)
                            total -= MOD;
                        break;
                    case 'X':
                        break;
                    case 'G':
                        if (i>0 && board[i-1][j] != '.') {
                            total = 0;
                            break outerfor;
                        }
                        if (i<N-1 && board[i+1][j] != '.') {
                            total = 0;
                            break outerfor;
                        }
                        if (j>0 && board[i][j-1] != '.') {
                            total = 0;
                            break outerfor;
                        }
                        if (j<N-1 && board[i][j+1] != '.') {
                            total = 0;
                            break outerfor;
                        }
                        break;
                }
            }
        }
        out.println(total);
    }
    static final int MOD = 1000000007;
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 50
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 2 to 50
            String []board = new String[N];
            for (int j=0; j<N; j++) {
                board[j] = scan.nextLine();
                if (board[j].isEmpty())
                    board[j] = scan.nextLine();
            }
            new LunchMar17Species(board);
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
