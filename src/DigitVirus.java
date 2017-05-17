


import java.util.Scanner;


class DigitVirus {
    static void virus(String v)
    {
        char[] virus=v.toCharArray();
        int max=0;
        int maxPos[]=new int[v.length()];
        for (int i=0; i<v.length(); i++) {
            if (max<virus[i])
                max=virus[i];
        }
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        int TC = sc.nextInt();  // between 1 and 3
        for (int i=0; i<TC; i++) {
            String s = sc.next(); //1 ≤ N ≤ 150,000
        }
    }
}
