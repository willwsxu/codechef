package codechef;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;

public class ContestHelper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //genTestCase(new int[]{100000,99999,99998}, 1, 9);
        genTestCase(new int[]{99999,99998,99997}, 1, 10000);
        //genTestCase(new int[]{99,98,97}, 1, 8);
    }
       
    public static int ni(Scanner sc)
    {
        return sc.nextInt();
    }
    
    public static int[] ria(int N, Scanner sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner scan = new Scanner(System.in);
    static void fillArray(int [] a)
    {
        for (int i=0; i<a.length; i++) {
            a[i] = scan.nextInt();  // between 1 and 10^4
        }
    }
    static void fillMatrix(int [][] a, Scanner reader)
    {
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                a[i][j]=reader.nextInt();
            }
    }
    public static Scanner getFileScanner(String file)
    {
        try {
            return new Scanner( new FileReader( new File(file)));
        }
        catch (IOException e)
        {
        }
        return new Scanner(System.in);
    }
    static void fillArray(int [][] a, String file)
    {
        try (Scanner reader = new Scanner(
        new FileReader(
        new File(file)))) {
            fillMatrix(a, reader);
        }
        catch (IOException e)
        {
        }
    }
    
    static void genTestCase(int []linesize, int low, int high)
    {
        try (PrintWriter writer = 
                new PrintWriter(
                new FileWriter(
                new File("test.txt"))); ) 
        {
            Random rand = new Random();
            for (int i=0; i<linesize.length; i++) {
                for (int j=0; j<linesize[i]; j++) {
                    writer.print(rand.nextInt(high-low+1)+1);
                    writer.print(' ');
                }
                writer.println();
            }
        }
        catch (IOException e)
        {
            
        }        
    }
    
    static PrintStream console = System.out; // ssave the console
    public static void redirect()
    {
            System.setOut(console);        
    }
    public static void redirect(String f)
    {        
        try
        {
            PrintStream ps = new PrintStream(
            new FileOutputStream(
            new File(f)));
            System.setOut(ps);
        } catch (FileNotFoundException e)
        {
            System.out.println("exception bad outfile "+f);
        }
    }
    
    public static void writeFile(int []line, int start, int end, boolean append)
    {
        try (PrintWriter writer = 
                new PrintWriter(
                new FileWriter(
                new File("test.out.txt"), append)); ) 
        {
            if (end>line.length)
                end = line.length;
            for (int i=start; i<end; i++) {
                writer.print(line[i]);
                writer.print(' ');
            }
            writer.println();
            writer.println();
        }
        catch (IOException e)
        {
            
        }        
    }
    static void perfTest()
    {
        Instant start = Instant.now();
        
        Instant end = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, end));   
    }
}
