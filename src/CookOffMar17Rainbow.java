
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class CookOffMar17Rainbow {
    static Scanner scan = new Scanner(System.in);
    
    static void fillMatrix(int [][] a, Scanner reader)
    {
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                a[i][j]=reader.nextInt();
            }
    }
    int [][]adjMat;
    void removeNode(int i)
    {
        Arrays.fill(adjMat[i], 0);
        for (int j=0; j<adjMat[i].length; j++) {
            adjMat[j][i]=0;
        }
    }
    boolean visit(int i) {
        Set<Integer> edges = new HashSet<>(adjMat[i].length);
        for (int j=0; j<adjMat[i].length; j++) {
            if (adjMat[i][j]>0) {
                if (edges.contains(adjMat[i][j])) {
                    //removeNode(i);
                    //return false;  not nice only if all edges are same
                } else
                    edges.add(adjMat[i][j]);
            }
        }
        if ( edges.size()==1){
            removeNode(i);
            return false;
        }
        return edges.size()>0;
    }
    public CookOffMar17Rainbow(int N)
    {
        adjMat = new int[N][N];
        fillMatrix(adjMat, scan);
        int nice=N;
        boolean []removed = new boolean[N];
        Arrays.fill(removed, false);
        boolean repeat=true;
        while (repeat) {
            repeat = false;
            for (int i=0; i<adjMat.length; i++) {
                if (removed[i])
                    continue;
                if ( !visit(i) ) {
                    nice--;
                    repeat=true;
                    removed[i]=true;
                }
            }
        }
        out.println(nice);
    }
    public static void autoTest()
    {  
        int TC = scan.nextInt();  // 1 to 100
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // 1 to 50
            new CookOffMar17Rainbow(N);
        }        
    }
    public static void main(String[] args)
    {      
        scan = codechef.CodeChef.getFileScanner("rainbow0317.txt");
        Instant start = Instant.now();
        autoTest();
        Instant end = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    }
}
