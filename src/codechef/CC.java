/*
 */
package codechef;

// connected component
public class CC
{
    private IGraph g;
    private int visId[];  // dual purpose, for component id and is visited
    private int id=0;
    public CC(IGraph g)
    {
        this.g=g;
        visId = new int[g.V()];
        for (int s = 0; s < g.V(); s++)
            // no need to visit nodes without any edge
            if (visId[s]==0 && !g.adj(s).isEmpty())
            {
                dfs(s, ++id);
            }
    }
    private void dfs(int v, int id) {
        //out.println("dfs v"+v+" id="+id);
        visId[v]=id;
        for (int w: g.adj(v))
            if (visId[w]==0)
                dfs(w, id);
    }
    public int numCompoments(int ID)  // components with id=ID
    {
        int count=0;
        for (int s = 0; s < g.V(); s++)
            if (visId[s]==ID)
                count++;
        //out.println("singleGraph "+id);
        return count;
    }   
    public int numCompoments()  // total disjoin components
    {
        int count=0;
        for (int s = 0; s < g.V(); s++)
            if (visId[s]==0)
                count++;
        //out.println("singleGraph "+id);
        return count+id;
    }
    public boolean connected(int v, int w)
    {
        return visId[v]==visId[w] && visId[v]!=0;
    }
}