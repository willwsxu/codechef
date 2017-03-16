import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
 
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
 
 
//3.3 seconds 
class CC_2017_Mar_SumDis {
 
	static void log(int[] X){
		int L=X.length;
		for (int i=0;i<L;i++){
			logWln(X[i]+" ");
		}
		log("");
	}
 
 
	static void log(long[] X){
		int L=X.length;
		for (int i=0;i<L;i++){
			logWln(X[i]+" ");
		}
		log("");
	}
	static void log(Object[] X){
		int L=X.length;
		for (int i=0;i<L;i++){
			logWln(X[i]+" ");
		}
		log("");
	}
 
 
	static void log(Object o){
		logWln(o+"\n");
	}
 
	static void logWln(Object o){
		System.err.print(o);
		//	outputWln(o);
 
	}
	static void info(Object o){
		System.out.println(o);
		//output(o);
	}
 
	static void output(Object o){
 
		outputWln(""+o+"\n");
 
	}
 
	static void outputWln(Object o){
		// System.out.print(o);
 
		try {
			out.write(""+ o);
		} catch (Exception e) {
 
		}
	}
 
	static int base=10000;
	static int shift=15;
	static int msk=(1<<shift)-1;
	
	static int convert(int u,int v){
		u+=base;
		v+=base;
		// u between 0 and 2*base
		// v between 0 and 2*base
		//log("u:"+u+" v:"+v);
		int x=(u<<shift)+v;
	//log(x);
		return x;
	}
	
	static int[] convert(int x){
		int u=(x>>shift)-base;
		int v=(x&msk)-base;
		return new int[]{u,v};
	}
	
	static void check(){
		log("checking");
		for (int u=-10000;u<=10000;u++)
			for (int v=-10000;v<=10000;v++){
				int x=convert(u,v);
				int[] tmp=convert(x);
				if (u!=tmp[0] || v!=tmp[1]){
					log("Error");
					return;
				}
			}
		log("done");
		
		
	}
	
	static class Item implements Comparable<Item>{
		
		int v;
		int origin;
		
		public int compareTo(Item X){
			return v-X.v;
		}
 
		public Item(int v, int origin) {
			this.v = v;
			this.origin = origin;
		}
		
		
		
	}
	
	
	static class Composite {
		int num;
		long cumul;
	
		public Composite(int num, long cumul) {
			this.num = num;
			this.cumul=cumul;
		}
		
		public String toString(){
			return "#:"+num+" cumul:"+cumul;
		}
		
	}
	
	static int dst(int x,int m){
		if (x==m-1)
			return a[m-1];
		if (x==m-2)
			return Math.min(a[m-2]+a[m-1],b[m-2]);
			ArrayList<Integer> opt=new ArrayList<Integer>();
			
			opt.add(c[m-3]); // 0-->3
			opt.add(a[m-3]+a[m-2]+a[m-1]); // 0->1-->2-->3
			opt.add(a[m-3]+b[m-2]); // 0-->1-->3
			opt.add(b[m-3]+a[m-1]);// 0-->2-->3
			Collections.sort(opt);
			return opt.get(0);
		
	}
	
	static long solveBourrin(){
		
		int[] dist=new int[n];
		long sum=0;
		for (int u=0;u<n;u++){
			dist[u]=0;
			for (int v=u+1;v<n;v++){
				dist[v]=Integer.MAX_VALUE;
				int min=Math.max(u, v-3);
				for (int w=min;w<v;w++){
						dist[v]=Math.min(dist[v],dist[w]+dst(w,v));
				}
				sum+=dist[v];
			}
			
		}
		//output(sum);
		return sum;
	}
	
	
	static int[] dist;
	
	static long solveMass(){
		// first we compute shortest path between node 0 and nodes 1,2,3
		dist=new int[4];
		
		
		dist[1]=a[0];
		dist[2]=Math.min(a[0]+a[1],b[0]);
		
		ArrayList<Integer> opt=new ArrayList<Integer>();
		opt.add(dist[0]+c[0]);
		opt.add(dist[0]+a[0]+a[1]+a[2]);
		opt.add(dist[0]+b[0]+a[2]);
		opt.add(dist[0]+a[0]+b[1]);
		opt.add(dist[1]+b[1]);
		opt.add(dist[1]+a[1]+a[2]);
		opt.add(dist[2]+a[2]);
		
		
		
		
		
		Collections.sort(opt);
		dist[3]=opt.get(0);
		
		
		
		
		
		// we then compute diff 
		//int[] diff=new int[2];
		//diff[0]=dist[2]-dist[1];
		//diff[1]=dist[3]-dist[1];
		
		// how to update ?
		// too things to update : first node, and last node
		// updating for last node
		// imagine we have dist up to x-1
		/*
		int x=4;
		dist[x]=Math.min(dist[x-3]+c[x-3],dist[x-1]+a[x-1]);
		dist[x]=Math.min(dist[x],dist[x-2]+b[x-2]);
		dist[x]=Math.min(dist[x],dist[x-2]+a[x-2]+a[x-1]);
		
		*/
		// we have dist(x-3),dist(x-1),dist(x) we can rebaseline to have dist(x-3)=0
		
		// difference between dist should be <= 10000 in abs.
		
		
		
		// we will sum all distances up to current position m
		// we first manage the special cases
		long sum=0;
		// distances to 3
		sum+=dist[1]; // from 0 to 1
		sum+=dist[2]; // from 0 to 2
		sum+=dist[3]; // from 0 to 3
		//logWln("initial dist:");
		//log(dist);
		//log("Introduced "+0+" sum:"+sum);
		
		HashMap<Integer,Composite> hm=new HashMap<Integer,Composite>();
		int u=dist[2]-dist[1];
		int v=dist[3]-dist[1];
		int x=convert(u,v);
 
		Composite C=new Composite(1,(long)dist[1]);
		hm.put(x,C);
		
		// we start with m=4;
		// TODO: check the baseline & num stuff
		// TODO: add the new ones as well !!!
		int m;
		for (m=4;m<n;m++){
			//log("----------m:"+m+" hm.size():"+hm.size());
			HashMap<Integer,Composite> tmp=new HashMap<Integer,Composite>();
			// already existing elements
			for (Entry<Integer,Composite> e:hm.entrySet()){
				
				Integer X=e.getKey();
				Composite CC=e.getValue();
				
				
				int[] bob=convert(X);
				//logWln(CC+" // diff: ");
				//log(bob);
				int d0=0; // m-3
				int d1=bob[0]; // m-2
				int d2=bob[1]; // m-1
				// now we update
				
				ArrayList<Item> zoubabs=new ArrayList<Item>();
				zoubabs=new ArrayList<Item>();
				zoubabs.add(new Item(d0+c[m-3],0));
				zoubabs.add(new Item(d0+a[m-3]+a[m-2]+a[m-1],0));
				zoubabs.add(new Item(d0+b[m-3]+a[m-1],0));
				zoubabs.add(new Item(d0+a[m-3]+b[m-2],0));
				zoubabs.add(new Item(d1+b[m-2],1));
				zoubabs.add(new Item(d1+a[m-2]+a[m-1],1));
				zoubabs.add(new Item(d2+a[m-1],2));
				
				
				
				
				
				
				Collections.sort(zoubabs);
				Item best=zoubabs.get(0);
				
				int d3=best.v;
				long delta=d3*CC.num+CC.cumul;
				//log("Increasing cumulative sum by :"+delta);
				sum+=delta;
				d2-=d1;
				d3-=d1;
				int z=convert(d2,d3);
				Composite CX=tmp.get(z);
				if (CX==null){
					CX=new Composite(0,0);
					tmp.put(z,CX);
				}
				
				CX.cumul+=CC.cumul+d1*CC.num;
				CX.num+=CC.num;
				//log("new diff :"+d2+" "+d3);
			}
			// new element, namely m-3
			int d0=0;
			int d1=a[m-3];
			int d2=Math.min(a[m-3]+a[m-2],b[m-3]);
			
			
			opt=new ArrayList<Integer>();
			opt.add(d0+c[m-3]);
			opt.add(d0+a[m-3]+a[m-2]+a[m-1]);
			opt.add(d0+b[m-3]+a[m-1]);
			opt.add(d0+a[m-3]+b[m-2]);
			opt.add(d1+b[m-2]);
			opt.add(d1+a[m-2]+a[m-1]);
			opt.add(d2+a[m-1]);
			Collections.sort(opt);
			int d3=opt.get(0);
			// for new 
			// we do several things:
			// first add the 3 distances
			// then introduce 1 new point
			long delta=d1+d2+d3;
			sum+=delta;
			//log("introducing :"+(m-3)+" increasing cumul by "+delta+" with diff : "+(d2-d1)+" "+(d3-d1));
			
			int xx=convert(d2-d1,d3-d1);
			Composite CN=tmp.get(xx);
			if (CN==null) {
				CN=new Composite(0,0);
				tmp.put(xx,CN);
			}
			CN.num++;
			CN.cumul+=d1;
			hm=tmp;
			//log(hm);
		}
		
		// some points have not been taken into account
		// we must take them
		// not taken: m-2 and m-1
		//log("partial sum:"+sum);
		m=n-1;
		
		//log("finalizing "+(m-2));
		sum+=Math.min(a[m-1]+a[m-2],b[m-2]);
		sum+=a[m-2];
		//log("partial sum:"+sum);
		
		//log("finalizing "+(m-1));
		sum+=a[m-1];
		
		//log(sum);
		
		return sum;
		
		//output(sum);
		/*
		long good=0;
		for (u=0;u<n;u++) {
			long diff=0;
			for (v=u+1;v<n;v++)
				diff+=v-u;
			//log("contrib for u:"+u+" is :"+diff);
			good+=diff;
		}
		//log("good was :"+good);
		*/
	}
	
	
	static void test(){
		log("testing");
		int NMAX=100;
		int NTESTS=10000;
		Random r=new Random();
		int VMAX=2;
		for (int t=0;t<NTESTS;t++){
			n=r.nextInt(NMAX)+4;
			a=new int[n-1];
			b=new int[n-2];
			c=new int[n-3];
			for (int i=0;i<n-1;i++)
				//a[i]=1;
				a[i]=r.nextInt(VMAX)+1;
			for (int i=0;i<n-2;i++)
				//b[i]=2;
				b[i]=r.nextInt(VMAX)+1;
			for (int i=0;i<n-3;i++)
				c[i]=r.nextInt(VMAX)+1;
			long res1=solveBourrin();
			long res2=solveMass();
			if (res1!=res2){
				log("=========Error");
				log(n);
				log(a);
				log(b);
				log(c);
				log("------");
				log(res1);
				log(res2);
				return;
			}
		}
		log("done");
		
	}
	
	
	static int n;
	static int[] a,b,c;
 
	// Global vars
	static BufferedWriter out;
	static InputReader reader;
 
 
	static void process() throws Exception {
		out = new BufferedWriter(new OutputStreamWriter(System.out));
		//reader=new InputReader(System.in);
		reader = new InputReader(new FileInputStream(new File("test5.txt")));
 
		boolean TST=false;
		if (TST){
			test();
		} else {
		
		int T=reader.readInt();
		for (int t=0;t<T;t++){
			n=reader.readInt();
			a=new int[n-1];
			b=new int[n-2];
			c=new int[n-3];
			
			for (int i=0;i<n-1;i++)
				a[i]=reader.readInt();
			for (int i=0;i<n-2;i++)
				b[i]=reader.readInt();
			for (int i=0;i<n-3;i++)
				c[i]=reader.readInt();
			
			//output(solveBourrin());
			output(solveMass());
		}
		}
 
		try {
			out.close();
		}
		catch (Exception e){}
 
 
	}
 
 
	
 
	public static void main(String[] args) throws Exception {
		process();
 
	}
 
	static final class InputReader {
		private final InputStream stream;
		private final byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
 
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
 
		private int read() throws IOException {
			if (curChar >= numChars) {
				curChar = 0;
				numChars = stream.read(buf);
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar++];
		}
 
 
		public final String readString() throws IOException {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res=new StringBuilder();
			do {
				res.append((char)c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}
 
		public final int readInt() throws IOException {
			int c = read();
			boolean neg=false;
			while (isSpaceChar(c)) {
				c = read();
			}
			char d=(char)c;
			//log("d:"+d);
			if (d=='-') {
				neg=true;
				c = read();
			}
			int res = 0;
			do {
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			//log("res:"+res);
			if (neg)
				return -res;
			return res;
 
		}
 
		public final long readLong() throws IOException {
			int c = read();
			boolean neg=false;
			while (isSpaceChar(c)) {
				c = read();
			}
			char d=(char)c;
			//log("d:"+d);
			if (d=='-') {
				neg=true;
				c = read();
			}
			long res = 0;
			do {
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			//log("res:"+res);
			if (neg)
				return -res;
			return res;
 
		}
 
 
 
 
		private boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	}
 
 
}