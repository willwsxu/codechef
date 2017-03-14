
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Matrix
{
    static final int SIZE=4;
    int[][] mat;
    int     candles=0;
    // count of each submatrix size, subMatrixCount[0]= how many submatrix with 1 element 
    int     subMatrixCount[]=new int[SIZE*SIZE];
    Matrix(int N, Scanner scan) {
        mat = new int[SIZE][SIZE];
        scanValue(scan);
    }
    
    void print()
    {
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat.length; j++) {
                out.print(mat[i][j]);
            }
            out.println();
        }
        out.println("total "+candles);
    }
    void scanValue(Scanner scan)
    {
        for (int i=0; i<mat.length; i++) {
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            for (int j=0; j<mat.length; j++) {
                mat[i][j] = line.charAt(j)=='0'?0:1;
                candles += mat[i][j];
            }
        }
        //print();
        int marker=2;
        for (int i=0; i<mat.length; i++)
            for (int j=0; j<mat.length; j++) {
                if (mat[i][j]==1) {
                    int matrixSize = searchSubMatrix(i, j, marker++);
                    subMatrixCount[matrixSize-1]++;
                }
            }
        //print();
    }
    
    int searchSubMatrix(int r, int c, int marker)
    {
        class Pair
        {
            int r, c; 
            Pair(int r, int c) {
                this.r=r;
                this.c=c;
            }
        }
        ArrayList<Pair> V = new ArrayList<>();
        V.add(new Pair(r, c));
        int count=0;
        while (!V.isEmpty()) {
            Pair last = V.get(V.size()-1);
            V.remove(V.size()-1);
            if (mat[last.r][last.c]==1) { // always true
                count++;
                mat[last.r][last.c]=marker;
            }
            if (last.c>0 && mat[last.r][last.c-1]==1)
                V.add(new Pair(last.r, last.c-1));
            if (last.c<SIZE-1 && mat[last.r][last.c+1]==1)
                V.add(new Pair(last.r, last.c+1));
            if (last.r<SIZE-1 && mat[last.r+1][last.c]==1)
                V.add(new Pair(last.r+1, last.c));
        }
        return count;
    }
    int numSubMatrix()
    {
        switch(candles) {
            default:
            case 0:     return 0;
            case 1:     return 1;
            case 2:
                for (int i=0; i<4; i++)
                    for (int j=0; j<4; j++) {
                        if (mat[i][j]==1) {
                            if (j<3 && mat[i][j+1]==1)
                                return 1;
                            if (i<3 && mat[i+1][j]==1)
                                return 1;
                            return 2;
                        }
                    }
                break;
            case 3:
                return 3;
            case 4:
                int sub=0;
                for (int i=0; i<4; i++)
                    for (int j=0; j<4; j++) {
                        if (mat[i][j]==1) {
                            if (j<3 && mat[i][j+1]==1)
                                return 1;
                            if (i<3 && mat[i+1][j]==1)
                                return 1;
                            return 2;
                        }
                    }
                break;
        }
        return 3;
    }
}
class Mar17SubMatrix {
    static Scanner scan = new Scanner(System.in);
    
    Matrix[] matrix;
    Mar17SubMatrix(int cakes, int queries)
    {
        //out.println("cakes "+cakes+" queries "+queries);
        matrix = new Matrix[cakes];
        for (int i=0; i<cakes; i++) {
            matrix[i] = new Matrix(4, scan);
        }
        for (int i=0; i<queries; i++) {
            int type = scan.nextInt();
            if ( type==1) {
                int L = scan.nextInt();
                int R = scan.nextInt();
                int total=0;
                int subMatrix[] = new int[Matrix.SIZE*Matrix.SIZE];
                Arrays.fill(subMatrix, 0);
                for (int j=L-1; j<R; j++) {
                    for (int k=0; k<Matrix.SIZE*Matrix.SIZE; k++)
                        subMatrix[k] += matrix[j].subMatrixCount[k];
                    //total += matrix[j].numSubMatrix();
                }
                for (int k=0; k<Matrix.SIZE*Matrix.SIZE; k++) {
                    total += subMatrix[k];
                    //out.print(subMatrix[k]);
                }
                //out.println(" matrix count");
                if ( total==0)
                    out.println("Lotsy");
                else {
                    if (subMatrix[0]%2==1) {
                        out.println("Pishty");
                    } else {
                        if (subMatrix[1]%2==1)
                            out.println("Pishty");
                        else if (subMatrix[2]%2==1)
                            out.println("Pishty");
                        else
                            out.println("Lotsy");
                    }
                }
                /*
                if (total%2==0)
                    out.println("Lotsy");
                else
                    out.println("Pishty");
                */
            }
            else {
                int cake = scan.nextInt();
                matrix[cake-1].scanValue(scan);
            }
        }
    }
    public static void main(String[] args)
    {        
        int TC = scan.nextInt();  // 1 to 5
        for (int i=0; i<TC; i++) {
            int cakes = scan.nextInt();
            int queries = scan.nextInt();
            new Mar17SubMatrix(cakes, queries);
        }
    }
}
/*
1
3 4
0110
0000
0000
0001

0000
0000
0000
0000

1000
1000
0000
0000

1 1 1
1 2 2
1 3 3
1 1 3
2 2
0001
0010
0100
1000
1 1 3

Output:
Pishty
Lotsy
Pishty
Pishty
Pishty
*/