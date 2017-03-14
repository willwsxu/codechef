/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codechef;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author wxu
 */
public class CodeChef {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        genTestCase(new int[]{10,9,8}, 1, 10);
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
}
