
package codechef;


public class Edge
{
    private final int v; // one vertex
    private final int w; // the other vertex
    private final long weight; // edge weight
    public Edge(int v, int w, long weight)
    {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    public long weight()
    { return weight; }
    public int either()
    { return v; }
    public int other(int vertex)
    {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent edge");
    }
    // direct edge
    public int from()
    { return v; }
    public int to()
    { return w; }
}
