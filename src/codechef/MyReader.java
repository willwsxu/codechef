
package codechef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

// Buffered Read class did not help Stable Market TLE issue (
class MyReader
{
    BufferedReader br;
    String line;
    List<String> items=new ArrayList<>();
    MyReader(String f)
    {
        try {
            br = new BufferedReader(new FileReader(new File(f)));
        } catch (IOException e)
        {
            out.println("MyReader bad file "+f);
        }
        readline();
    }
    MyReader()
    {
        br = new BufferedReader(new InputStreamReader(System.in));
        readline();            
    }
    void readline()
    {
        try {
            line = br.readLine();
            while (line.isEmpty())
                line = br.readLine();
            //out.println(line);
        }catch (IOException e)
        {
            out.println("MyReader read exception "+e);
        }
        String [] w = line.split("\\s+");
        for(String s:w) {
            if (!s.isEmpty())
                items.add(s);
        }
        //out.println(items);
    }
    int nextInt()
    {
        if (items.isEmpty())
            readline();
        try {
        int i=Integer.parseInt(items.get(0));
        items.remove(0);
        return i;
        } catch (NumberFormatException e) {
            out.println(items.toString()+e);
            return 0;
        }
    }
    long nextLong()
    {    
        if (items.isEmpty())
            readline();
        long i=Long.parseLong(items.get(0));
        items.remove(0);
        return i;
    }
}