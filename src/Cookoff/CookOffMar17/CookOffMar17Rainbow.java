package Cookoff.CookOffMar17;

import codechef.IOR;
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// EASY, graph, adjacency matrix
// Desc: N by N complete graph, Cij is color of edge from i to j, Cii=0
// A subset of nodes is called interesting if every node in the subset has at least 
// two edges of different colors, connecting it to vertices in the subset.
// determine the maximum possible size of an interesting subset of nodes.
// greedy strategy 1
// for each node (row), check edge colors to all other node, done if all have colors>1
//   else remove a node from graph if it is not nice, by change color of all edges to 0
// repeat
class CookOffMar17Rainbow {
    int [][]adjMat;
    void removeNode(int i)
    {
        Arrays.fill(adjMat[i], 0);
        for (int j=0; j<adjMat[i].length; j++) {
            adjMat[j][i]=0;
        }
    }
    boolean visit(int i) {
        Set<Integer> colors = new HashSet<>(adjMat[i].length);
        for (int j=0; j<adjMat[i].length; j++) {
            if (adjMat[i][j]>0 && !colors.contains(adjMat[i][j])) {
                colors.add(adjMat[i][j]);
            }
        }
        if ( colors.size()==1){
            removeNode(i);
            return false;
        }
        return colors.size()>1;
    }
    int greedy(int N)
    {
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
        return nice;
    }
    
    // borrow idea from editorial, speed is slower 54 msec vs 4msec with solve()
    int solve2(int N)
    {
        HashSet<Integer> all = IntStream.range(0, N).boxed().collect(Collectors.toCollection(HashSet::new));
        while (all.size()>1) {
            int bad=-1;
            for (int r: all) {
                Set<Integer> edges = new HashSet<>(all.size());
                for (int c: all) {
                    if ( r!=c)
                        edges.add(adjMat[r][c]);
                }
                if ( edges.size()<=1 ) {
                    bad = r;
                    break;
                }
            }
            if ( bad>=0 ) {
                all.remove(bad);
            } else
                break;
        }
        int nice = all.size();
        return nice>1?nice:0;
    }
    public CookOffMar17Rainbow(int N)
    {
        adjMat = IOR.fillMatrix(N, N);
        out.println(solve2(N));
    }
    public static void autoTest()
    {  
        int TC = IOR.ni();  // 1 to 100
        while (TC-->0) {
            new CookOffMar17Rainbow(IOR.ni()); // 1 to 50
        }        
    }
    public static void unit_test()
    {
        IOR.fileScanner("rainbow0317.txt");
        Instant start = Instant.now();
        autoTest();
        Instant end = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, end));               
    }
    public static void main(String[] args)
    {    
        unit_test();
    }
}
