
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class CookOffMar17Meetup {
    
    static Scanner scan = new Scanner(System.in);
    String[] readLine()
    {
        String str = scan.nextLine();
        if (str.isEmpty())
            str = scan.nextLine();
        return str.split(" ");
    }
    private void mapCity(Map<String, Integer> map, String[]city)
    {
        for (int i=0; i<city.length; i++)
            map.put(city[i], i);
    }
    private void fillAdjacentNodes(List<List<Integer>> adj, Map<String, Integer> map, int M)
    {
        for (int i=0; i<M; i++) {
            String[] road = readLine();
            if (road.length==2) {
                adj.get(map.get(road[0])).add(map.get(road[1]));  // add adjacent nodes
                adj.get(map.get(road[1])).add(map.get(road[0]));  // add adjacent nodes
            }
        }        
    }
    int queries=0;
    private String query(String side, String city) {
        queries++;
        out.println(side + " " +city);
        out.flush();
        String reply = scan.nextLine();
        if (reply.isEmpty())
            reply = scan.nextLine();
        return reply;
    }
    static final int MAX_NODE=1000;
    class Graph {
        List<List<Integer>> adjList = new ArrayList<>(MAX_NODE);
        Map<String, Integer> map = new HashMap<>(MAX_NODE);
        String city[];
        int match[]; // matched index of cities A->B 
        int nodes;
        int k;      // # cities to visit
        
        Graph(int N, int M)
        {
            nodes=N;
            match = new int[N];
            Arrays.fill(match, -1); 
            for (int i=0; i<N; i++)
            {
                adjList.add(new ArrayList<>());
            }
            readRoads(M);
        }
        private void readRoads(int M)
        {
            city = readLine();
            mapCity(map, city);
            fillAdjacentNodes(adjList, map, M);
        }
    }
    Graph   ga, gb;
    CookOffMar17Meetup()
    {
        int N = scan.nextInt();  // cities 1 and 1000
        int M = scan.nextInt();  // bi-directional roads
        int kA = scan.nextInt(); // # of city Alice visits, clique   
        int kB = scan.nextInt(); // # of city Bob visits, independent set 
        ga = new Graph(N, M);
        gb = new Graph(N, M);
        for (int i=0; i<kA; i++) {
            if (ga.adjList.get(i).size()<N/2 && ga.match[i]<0) {
                int m = gb.map.get(query("A", ga.city[i]));
                ga.match[i]=m;
                gb.match[m]=i;
                if ( m<kB )
                {
                    out.println("C Yes");
                    return;
                }
            }
        }
        boolean complete=true;
        for (int i=0; i<kA; i++) {
            if (ga.match[i]<0)
                complete = false;
        }
        if (complete) {
            out.println("C No");            
        }
    }
    
    public static void main(String[] args)
    {             
        new CookOffMar17Meetup();
    }
}
