
package codechef;


public class Knowledgebase {
    
}

class State
{
    long ingre;
    long city;
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof State) {

        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(ingre*city);
    }
}
