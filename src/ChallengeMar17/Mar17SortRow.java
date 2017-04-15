package ChallengeMar17;


import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


public class Mar17SortRow {
    static Scanner scan = new Scanner(System.in);
    
    int[][] grid;
    int[][]position;    
    
    void print(int[][]mat)
    {
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat.length; j++) {
                out.print(mat[i][j]);
                out.print(" ");
            }
            out.println();
        }
    }
    Mar17SortRow(int N)
    {
        grid = new int[N][N];
        position = new int[N][N];
        int []N2 = new int[N*N];
        for (int i=0; i<N; i++) { 
            Arrays.fill(N2, 0);
            for(int j=0; j<N; j++) {
                grid[i][j] = scan.nextInt();
                N2[grid[i][j]-1]=j+1;
            }
            int m=0;
            for (int k=0; k<N*N; k++){
                if (N2[k]>0)
                    position[i][m++]=N2[k]-1;
            }
        }
        print(grid);
        print(position);
    }
    
    public static void main(String[] args)
    {        
        int N = scan.nextInt();  // 1 to 300
        new Mar17SortRow(N);
    }
}
