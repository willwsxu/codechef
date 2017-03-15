/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codechef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

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
        //genTestCase(new int[]{100000,99999,99998}, 1, 9);
        genTestCase(new int[]{100000,99999,99998}, 1, 1);
    }
    
    static Scanner scan = new Scanner(System.in);
    static void fillArray(int [] a)
    {
        for (int i=0; i<a.length; i++) {
            a[i] = scan.nextInt();  // between 1 and 10^4
        }
    }
    static void fillArray(int [][] a, String file)
    {
        try (Scanner reader = new Scanner(
        new FileReader(
        new File(file)))) {
            for (int i=0; i<a.length; i++)
                for (int j=0; j<a[i].length; j++) {
                    a[i][j]=reader.nextInt();
                }
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
}
