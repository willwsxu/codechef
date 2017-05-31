
package codechef;


// copied from subArray
public class IntPair  // pair of int
{
    int first;
    int second;
    public IntPair(int f, int s)
    {
        first=f;
        second=s;
    }
    public int int1()
    {
        return first;
    }
    public int int2()
    {
        return second;
    }
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof IntPair) {
            IntPair other =(IntPair)s;
            return first==other.first && second==other.second;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(first*second);
    }
    @Override
    public String toString()
    {
        return first+":"+second;
    }
}

