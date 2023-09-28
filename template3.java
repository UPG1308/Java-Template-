import java.util.*;
import java.io.*;
public class Balabizo {
    static int mod = 998244353;
    static long[] fac;
    static void fac(){
        fac = new long[300005 +1];
        fac[0] = 1;
        for(int i = 1; i<= 300005; i++) fac[i] = (fac[i-1] * i) % mod;
    }
    static void solve() throws IOException {
        char[] c = sc.next().toCharArray();
        int n = c.length;
        long ans = 1;
        int res = 0;
        for(int i=0; i<n ;i++){
            int j = i , cnt = 0;
            while(j < n && c[j] == c[i]){
                j++;
                cnt++;
            }
            ans *= cnt;
            ans %= mod;
            i = j-1;
            res += cnt - 1;
        }
        ans *= fac[(int)res];
        ans %= mod;
        pw.println(res+" "+ans);
    }
 
 
    public static void main(String[] args) throws IOException {
//        sc = new Scanner("in.txt");
//        pw = new PrintWriter("out.txt");
        fac();
        int t = sc.nextInt();
        int cnt = 1;
        while(t-->0){
//            pw.print("Case #" + cnt++ + ": ");
            solve();
        }
        pw.flush();
    }
    static Scanner sc = new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(System.out);
    static class Scanner {
 
        StringTokenizer st;
        BufferedReader br;
 
        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }
 
        public Scanner(String file) throws IOException {
            br = new BufferedReader(new FileReader(file));
        }
 
        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }
 
        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
 
        public String readAllLines(BufferedReader reader) throws IOException {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            return content.toString();
        }
 
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
 
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
 
        public String nextLine() throws IOException {
            return br.readLine();
        }
 
        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }
 
        public long[] nextlongArray(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
 
        public Long[] nextLongArray(int n) throws IOException {
            Long[] a = new Long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
 
        public int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }
 
        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }
 
        public boolean ready() throws IOException {
            return br.ready();
        }
 
    }
 
}
