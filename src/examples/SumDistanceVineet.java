import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.StringTokenizer;
 
public class SumDistanceVineet {
 
	public static void main(String[] args) throws Exception {
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("test4.txt"));
		String str = br.readLine();
		int T = Integer.parseInt(str);
		for(int i = 0 ; i < T ; i++) {
			str = br.readLine();
			int N = Integer.parseInt(str);
			int [] A = new int[N-1];
			int [] B = new int[N-2];
			int [] C = new int[N-3];
			str = br.readLine();
			StringTokenizer st = new StringTokenizer(str," ");
			for(int j = 0 ; j < N-1 ; j++) {
				A[j] = Integer.parseInt(st.nextToken());
			}
			str = br.readLine();
			st = new StringTokenizer(str," ");
			for(int j = 0 ; j < N-2 ; j++) {
				B[j] = Integer.parseInt(st.nextToken());
			}
			str = br.readLine();
			st = new StringTokenizer(str," ");
			for(int j = 0 ; j < N-3 ; j++) {
				C[j] = Integer.parseInt(st.nextToken());
			}
			long [] dist = new long[N];
			Arrays.fill(dist, Long.MAX_VALUE);
			dist[0] = 0;
			for(int j = 0 ; j < N ; j++) {
				if(j+1<N) {
					dist[j+1] = Math.min(dist[j+1], A[j]+dist[j]);
				}
				if(j+2<N) {
					dist[j+2] = Math.min(dist[j+2], B[j]+dist[j]);
				}
				if(j+3<N) {
					dist[j+3] = Math.min(dist[j+3], C[j]+dist[j]);
				}
			}
			long [] suffDist = new long[N];
			suffDist[N-1] = dist[N-1];
			for(int j = N-2 ; j >= 0 ; j--) {
				suffDist[j] = suffDist[j+1] + dist[j];
			}
			long ans = suffDist[0];
			long [] newDist = new long[N];
			Arrays.fill(newDist, Integer.MAX_VALUE);
			long tempSum = 0;
			int id = -1;
			long diff = 0;
			long nd = 0;
			long diffOne = 0;
			long diffTwo = 0;
			long diffThree = 0;
			long tim = 0;
			
			for(int j = 1 ; j < N-1 ; j++) {
				// calculate till pattern does not follow
				
				newDist[j] = 0;
				tempSum = 0;
				id = -1;
				diff = 0;
				for(int k = j  ; k < N ; k++) {
					tempSum += newDist[k];
					if(k+1<N) {
						//newDist[k+1] = Math.min(newDist[k+1], A[k]+newDist[k]);
						nd = A[k]+newDist[k];
						if(nd<newDist[k+1]) {
							newDist[k+1] = nd;
						}
					}
					if(k+2<N) {
						//newDist[k+2] = Math.min(newDist[k+2], B[k]+newDist[k]);
						nd = B[k]+newDist[k];
						if(nd<newDist[k+2]) {
							newDist[k+2] = nd;
						}
					}
					if(k+3<N) {
						//newDist[k+3] = Math.min(newDist[k+3], C[k]+newDist[k]);
						nd = C[k]+newDist[k];
						if(nd<newDist[k+3]) {
							newDist[k+3] = nd;
						}
					}
					
					if(k>j+3) {
						diffOne = newDist[k] - dist[k];
						diffTwo = newDist[k-1] - dist[k-1];
						diffThree = newDist[k-2] - dist[k-2];
						if((diffOne==diffTwo)&&(diffTwo==diffThree)) {
							diff = diffOne;
							id = k+1;
							break;
						}
					}
					
				}
				
				if(id!=-1) {
					if(id<N) {
						//System.out.println("entered special case ");
						tempSum += suffDist[id] + (N-id) * diff;
						
					}
					Arrays.fill(newDist, j,Math.min(id+5, N),Integer.MAX_VALUE);
				} else {
					tim++;
					Arrays.fill(newDist,j,N, Integer.MAX_VALUE);
				}
				ans += tempSum;
				//System.out.println("ans = " + ans);
			}
			
			System.out.println(ans);
		}
		}
 
}
 