
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
    List<List<Integer>> adjListA = new ArrayList<>(MAX_NODE);
    List<List<Integer>> adjListB = new ArrayList<>(MAX_NODE);
    Map<String, Integer> mapA = new HashMap<>(MAX_NODE);
    Map<String, Integer> mapB = new HashMap<>(MAX_NODE);
    String cityA[];
    String cityB[];
    int matchA[]; // matched index of cities A->B
    int matchB[]; // matched index of cities B->A
    CookOffMar17Meetup()
    {
        int N = scan.nextInt();  // cities 1 and 1000
        int M = scan.nextInt();  // bi-directional roads
        int kA = scan.nextInt(); // # of city Alice visits, clique   
        int kB = scan.nextInt(); // # of city Bob visits, independent set  
        matchA = new int[N];
        Arrays.fill(matchA, -1); 
        matchB = new int[N];
        Arrays.fill(matchB, -1); 
        for (int i=0; i<N; i++)
        {
            adjListA.add(new ArrayList<>());
            adjListB.add(new ArrayList<>());
        }
        cityA = readLine();
        mapCity(mapA, cityA);
        fillAdjacentNodes(adjListA, mapA, M);
        cityB = readLine();
        mapCity(mapB, cityB);
        fillAdjacentNodes(adjListB, mapB, M);
        for (int i=0; i<kA; i++) {
            if (adjListA.get(i).size()<N/2 && matchA[i]<0) {
                int m = mapB.get(query("A", cityA[i]));
                matchA[i]=m;
                matchB[m]=i;
                if ( m<kB )
                {
                    out.println("C Yes");
                    return;
                }
            }
        }
        boolean complete=true;
        for (int i=0; i<kA; i++) {
            if (matchA[i]<0)
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
