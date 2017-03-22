
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class CookOffMar17Meetup {
    
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
        
        Graph(int N, int M, int k)
        {
            nodes=N;
            this.k = k;
            match = new int[N];
            Arrays.fill(match, -1); 
            for (int i=0; i<N; i++)
            {
                adjList.add(new ArrayList<>());
            }
            city = readLine();
            mapCity(map, city);
            fillAdjacentNodes(adjList, map, M);
        }
        
        public int queryClique(Graph other)
        {
            for (int i=0; i<k; i++) {
                if (adjList.get(i).size()<nodes/2 && match[i]<0) {
                    int m = other.map.get(query("A", city[i]));
                    match[i]=m;
                    other.match[m]=i;
                    if ( m<other.k )
                    {
                        out.println("C Yes");
                        return -1;
                    }
                    return i;  // trim graph
                }
            }
            return -2;    // do nothing      
        }
        public int queryIndependent(Graph other)
        {
            for (int i=0; i<k; i++) {
                if (adjList.get(i).size()>=nodes/2 && match[i]<0) {
                    int m = other.map.get(query("B", city[i]));
                    match[i]=m;
                    other.match[m]=i;
                    if ( m<other.k )
                    {
                        out.println("C Yes");
                        return -1;
                    }
                    return i;  // trim graph
                }
            }
            return -2;    // do nothing                  
        }
                
        void trimNoneNeighbors(int node)
        {
            List<Integer> current = adjList.get(node);
            for (int i=0; i<adjList.size(); i++) {
                if ( i==node)
                    continue;
                if ( adjList.get(i).isEmpty() )
                    continue;
                if ( !current.contains(i))
                    adjList.get(i).clear();
                else {
                    for (int j=0; j<adjList.get(i).size(); j++) {
                        if ( !current.contains(adjList.get(i).get(j)))
                            adjList.get(i).remove(j);
                    }                    
                }
            }
            nodes = current.size();
        }
        void trimNeighbors(int node)
        {
            List<Integer> current = adjList.get(node);
            nodes -= current.size();
            for (int i=0; i<adjList.size(); i++) {
                if ( i==node)
                    continue;
                if ( adjList.get(i).isEmpty() )
                    continue;
                if ( current.contains(i))
                    adjList.get(i).clear();
                else {
                    for (int j=0; j<adjList.get(i).size(); j++) {
                        if ( current.contains(adjList.get(i).get(j)))
                            adjList.get(i).remove(j);
                    }                    
                }
            }   
            current.clear();
        }
        void print()
        {
            out.println("City: "+Arrays.toString(city));
            for (int i=0; i<city.length; i++) {
                if ( adjList.get(i).isEmpty() )
                    continue;
                out.println("Node "+(i)+": "+adjList.get(i));
            }
        }
    }
    Graph   ga, gb;
    CookOffMar17Meetup()
    {
        int N = scan.nextInt();  // cities 1 and 1000
        int M = scan.nextInt();  // bi-directional roads
        int kA = scan.nextInt(); // # of city Alice visits, clique   
        int kB = scan.nextInt(); // # of city Bob visits, independent set 
        ga = new Graph(N, M, kA);
        gb = new Graph(N, M, kB);
        //ga.print();
        //gb.print();
        int node = ga.queryClique(gb);
        while (node>=0) {
            ga.trimNoneNeighbors(node);
            gb.trimNoneNeighbors(node);
            node = ga.queryClique(gb);
        }
        //ga.print();
        //gb.print();
        if (node==-1)
            return;
        boolean complete=true;
        for (int i=0; i<kA; i++) {
            if (ga.match[i]<0)
                complete = false;
        }
        if (complete) {
            out.println("C No");   
            return;
        }
        node = gb.queryIndependent(ga);
        while (node>=0) {
            ga.trimNeighbors(node);
            gb.trimNeighbors(node);
            node = gb.queryIndependent(ga);
        }
        //ga.print();
        //gb.print();
        if (node==-1)
            return;
        out.println("C No");   
    }
    
    public static void main(String[] args)
    {             
        new CookOffMar17Meetup();
    }
}
