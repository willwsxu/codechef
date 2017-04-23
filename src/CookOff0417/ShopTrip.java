package CookOff0417;


import static java.lang.Math.sqrt;
import static java.lang.System.out;
import java.util.Scanner;


class ShopTrip {
    
    double [][]dist;
    int N, K;
    int ingre[]; // binary
    int all;
    ShopTrip(long X[], long Y[], String ingre[], int K)
    {
        this.K = K;
        X[0]=Y[0]=0;
        N = X.length-1;
        dist = new double[N+1][N+1];
        this.ingre = new int[ingre.length];
        for (int i=0; i<N; i++) {
            for (int j=i+1; j<=N; j++) {
                dist[i][j]=sqrt((X[j]-X[i])*(X[j]-X[i])+(Y[j]-Y[i])*(Y[j]-Y[i]));
                dist[j][i]=dist[i][j];
                //out.println(dist[i][j]);
            }
            this.ingre[i] = Integer.parseInt(ingre[i], 2);
            //out.println(Integer.toBinaryString(this.ingre[i]));
        }
        for (int i=0; i<K; i++) {
            all |= (1<<i);
        }
        //out.println(Integer.toBinaryString(all));
        recurse(0, 0.0, 0);
        if (distance != Double.MAX_VALUE)
            out.println(distance);
        else
            out.println(-1);
    }
    double distance=Double.MAX_VALUE;
    void recurse(int start, double d, int ingre)
    {
        if (ingre == all) {
            d += dist[start][0];// go back
            if (d<distance)
                distance=d;
            return;
        }
        for(int i=1; i<=N; i++) {
            if (i==start)
                continue;
            if ( (this.ingre[i-1] | ingre) == ingre) // no new ingre
                continue;
            recurse(i, d+dist[start][i], this.ingre[i-1] | ingre);
        }
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        //sc = codechef.ContestHelper.getFileScanner("shoptrip-t.txt");
        int TC = sc.nextInt();  // 1 ≤ T ≤ 10
        for (int i=0; i<TC; i++) {
            int N = sc.nextInt();   // 1 ≤ N ≤ 36
            int K = sc.nextInt();  // 1 ≤ K ≤ 12
            long X[] = new long[N+1]; // -1000 ≤ xi, yi ≤ 1000
            long Y[] = new long[N+1];
            for (int j=1; j<=N; j++) {
                X[j] = sc.nextInt();
                Y[j] = sc.nextInt();
            }
            String ingr[] = new String[K];
            for (int j=0; j<N; j++) {
                ingr[j] = sc.next();
            }
            new ShopTrip(X, Y, ingr, K);
        }
    }
}
