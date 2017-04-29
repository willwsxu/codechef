package ChallengeMar17;



import static java.lang.System.out;
import java.util.Scanner;

// BANDMATR easy
// find the minimal bandwidth of matrix, you can move numbers as many time as needed
// Algo: move 1 to fill 
class Mar17BandwidthMatrix {
    static Scanner scan = new Scanner(System.in);
    
    static int fillMatrix(int [][]m, int N)
    {
        int ones=0;
        for (int i=0; i<N;i++) {
            for (int j=0; j<N; j++) {
                m[i][j]=scan.nextInt();
                if ( m[i][j]>0)
                    ones++;
            }
        }
        return ones;
    }
    
    public static void main(String[] args)
    {
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 and 500
            int [][] matrix = new int[N][N];
            int ones = fillMatrix(matrix, N);
            int bandwidth=0;
            if (ones>N)
            {
                bandwidth++;
                ones -= N;
            }
            
            while (--N>1) {
                if (ones > 2*N) {
                    bandwidth++;
                    ones -= 2*N;
                }
                else
                    break;
            }
            out.println(bandwidth);
        }        
    }    
}
