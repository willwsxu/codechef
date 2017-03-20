import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
 
class IN {
	InputStream is;
	PrintWriter out;
	String INPUT = "";
	
	void solve()
	{
		for(int T = ni();T > 0;T--){
			int n = ni();
			long[] a = new long[n];
			for(int i = 0;i < n;i++){
				a[i] = nl();
			}
			long[][] dp = new long[1<<n-1][1<<n-1];
			dp[0][0] = 1;
			int mod = 1000000007;
			for(int d = 30;d >= 0;d--){
				int my = 0;
				for(int i = 0;i < n;i++){
					if(a[i]<<~d<0){
						my |= 1<<i;
					}
				}
				
				long[][] ndp = new long[1<<n-1][1<<n-1];
				for(int j = 0;j < 1<<n-1;j++){
					for(int y = 0;y < 1<<n-1;y++){
						inner:
						for(int ptn = 0;ptn < 1<<n;ptn++){
							for(int k = 0;k < n-1;k++){
								if(j<<~k>=0 && (ptn>>>k&3) == 1){
									continue inner;
								}
								if(y<<~k>=0 && ((my^ptn)>>>k&3) == 1){
									continue inner;
								}
							}
							int nj = j, ny = y;
							for(int k = 0;k < n-1;k++){
								if(j<<~k>=0 && (ptn>>>k&3) == 2){
									nj |= 1<<k;
								}
								if(y<<~k>=0 && ((my^ptn)>>>k&3) == 2){
									ny |= 1<<k;
								}
							}
							ndp[nj][ny] += dp[j][y];
							if(ndp[nj][ny] >= mod)ndp[nj][ny] -= mod;
						}
					}
				}
				dp = ndp;
			}
			long ret = 0;
			for(int i = 0;i < 1<<n-1;i++){
				for(int j = 0;j < 1<<n-1;j++){
					ret += dp[i][j];
				}
			}
			out.println(ret%mod);
		}
	}
	
	void run() throws Exception
	{
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);
		
		long s = System.currentTimeMillis();
		solve();
		out.flush();
		if(!INPUT.isEmpty())tr(System.currentTimeMillis()-s+"ms");
	}
	
	public static void main(String[] args) throws Exception { new IN().run(); }
	
	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;
	
	private int readByte()
	{
		if(lenbuf == -1)throw new InputMismatchException();
		if(ptrbuf >= lenbuf){
			ptrbuf = 0;
			try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
			if(lenbuf <= 0)return -1;
		}
		return inbuf[ptrbuf++];
	}
	
	private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
	private int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
	
	private double nd() { return Double.parseDouble(ns()); }
	private char nc() { return (char)skip(); }
	
	private String ns()
	{
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}
	
	private char[] ns(int n)
	{
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while(p < n && !(isSpaceChar(b))){
			buf[p++] = (char)b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}
	
	private char[][] nm(int n, int m)
	{
		char[][] map = new char[n][];
		for(int i = 0;i < n;i++)map[i] = ns(m);
		return map;
	}
	
	private int[] na(int n)
	{
		int[] a = new int[n];
		for(int i = 0;i < n;i++)a[i] = ni();
		return a;
	}
	
	private int ni()
	{
		int num = 0, b;
		boolean minus = false;
		while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
		if(b == '-'){
			minus = true;
			b = readByte();
		}
		
		while(true){
			if(b >= '0' && b <= '9'){
				num = num * 10 + (b - '0');
			}else{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
	
	private long nl()
	{
		long num = 0;
		int b;
		boolean minus = false;
		while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
		if(b == '-'){
			minus = true;
			b = readByte();
		}
		
		while(true){
			if(b >= '0' && b <= '9'){
				num = num * 10 + (b - '0');
			}else{
				return minus ? -num : num;
			}
			b = readByte();
		}
	}
	
	private void tr(Object... o) { if(INPUT.length() > 0)System.out.println(Arrays.deepToString(o)); }
}
 