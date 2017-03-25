
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


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
                if (adjList.get(i).size()*2<nodes && match[i]<0) {
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
                if (adjList.get(i).size()*2>=nodes && match[i]<0) {
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
        boolean complete()
        {
            boolean complete=true;
            for (int i=0; i<k; i++) {
                if (match[i]<0)
                    complete = false;
            }
            if (complete) {
                out.println("C No");  
            } 
            return complete;
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
        void solve(Graph ga, Graph gb) 
        {
            while (true) {
                int node = ga.queryClique(gb);
                while (node>=0) {
                    ga.trimNoneNeighbors(node);
                    gb.trimNoneNeighbors(ga.match[node]);
                    node = ga.queryClique(gb);
                }
                ga.print();
                gb.print();
                if (node==-1)
                    return;
                if (ga.complete()) {
                    return;
                }
                node = gb.queryIndependent(ga);
                boolean trim=false;
                while (node>=0) {
                    trim=true;
                    ga.trimNeighbors(gb.match[node]);
                    gb.trimNeighbors(node);
                    node = gb.queryIndependent(ga);
                }
                gb.print();
                ga.print();
                if (node==-1)
                    return;
                if (gb.complete()) {
                    return;
                }
                if ( !trim ) {
                    out.println("C No");
                    return;
                }
            }
        }
    }
    class Graph2{
        Map<String, HashSet<String>> adjList = new HashMap<>(MAX_NODE);
        Set<String> destination = new HashSet<>(MAX_NODE);
        Graph2(int N, int M, int k)
        {
            String []city = readLine();
            for (int i=0; i<N; i++) {
                if (i<k)
                    destination.add(city[i]);
                adjList.put(city[i], new HashSet<>());
            }
            for (int i=0; i<M; i++) {
                String[] road = readLine();
                if (road.length==2) {
                    adjList.get(road[0]).add(road[1]);  // add adjacent nodes
                    adjList.get(road[1]).add(road[0]);  // add adjacent nodes
                }
            }        
        }
        void reduce(String s, boolean bNeighbor)
        {
            // remove all nodes if it equals bNeighbor            
            adjList.keySet().removeIf(x->!x.equals(s)&&adjList.get(s).contains(x)==bNeighbor);
            // remove all linked nodes that is no longer part of the graph
            adjList.values().forEach(v->v.removeIf(x->!adjList.containsKey(x)));
        }
        
        public void answer(String resp, String debug) {
            out.flush();
            out.close();
            System.exit(0);
        }
        // true : done
        public boolean queryClique(Graph2 other)
        {
            int size = adjList.size();
            for (String s: adjList.keySet()) {
                if ( !destination.contains(s))
                    continue;
                if (adjList.get(s).size()*2<size) {
                    String answer = query("A", s);
                    if ( other.destination.contains(answer)) {
                        answer("Yes", "queryClique");
                        return true;
                    }
                    if ( size==1) {
                        answer("No", "queryClique");
                        return true;
                    }
                    reduce(s, false);
                    other.reduce(answer, false);
                    return false;
                }
            }
            return true;
        }
        public boolean queryIndependent(Graph2 other)
        {
            int size = adjList.size();
            for (String s: adjList.keySet()) {
                if ( !destination.contains(s))
                    continue;
                if (adjList.size()*2>=size) {
                    String answer = query("B", s);
                    if ( other.destination.contains(answer)) {
                        answer("Yes", "queryIndependent");
                        return true;
                    }
                    if ( size==1) {
                        answer("No", "queryIndependent");
                        return true;
                    }
                    reduce(s, true);
                    other.reduce(answer, true);
                    return false;
                }
            }
            return true;            
        }
        void solve(Graph2 ga, Graph2 gb)
        {
            while (true)
            {
                if (!ga.queryClique(gb))
                    continue;
                if (!gb.queryIndependent(ga))
                    continue;
                out.println("C No");
                break;
            }
        }
        void print()
        {
            out.println("dest: "+destination);
            adjList.forEach((k,v)->out.println(k+": "+v));
        }
    }
    Graph2   ga, gb;
    CookOffMar17Meetup()
    {
        int N = scan.nextInt();  // cities 1 and 1000
        int M = scan.nextInt();  // bi-directional roads
        int kA = scan.nextInt(); // # of city Alice visits, clique   
        int kB = scan.nextInt(); // # of city Bob visits, independent set 
        ga = new Graph2(N, M, kA);
        gb = new Graph2(N, M, kB);
        //ga.print();
        //gb.print();
        ga.solve(ga, gb);
    }
    
    public static void main(String[] args)
    {             
        new CookOffMar17Meetup();
    }
}
