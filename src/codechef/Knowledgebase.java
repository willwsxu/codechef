
package codechef;


public class Knowledgebase {
    
}

class pi  // integer pair
{
    int first;
    int second;
    pi(int f, int s)
    {
        first=f; second=s;
    }
}

class PL  // pair of long int
{
    long ingre;
    long city;
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof PL) {

        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(ingre*city);
    }
    @Override
    public String toString()
    {
        return ingre+":"+city;
    }
}
