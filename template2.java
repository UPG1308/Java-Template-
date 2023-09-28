import java.io.*;
import java.util.*;
 
public class Main {
    private static final FastIO io = new FastIO();
 
    public static void main(String[] args) {
        int t = io.nextInt();
        while (t-- != 0) solve();
        io.close();
    }
 
    private static final int MOD = 998244353;
 
    public static void solve() {
        char[] s = io.next().toCharArray();
        int n = s.length;
        List<Integer> list = new ArrayList<>();
        list.add(1);
        for (int i = 1; i < n; i++) {
            if (s[i] == s[i - 1]) {
                list.set(list.size() - 1, list.get(list.size() - 1) + 1);
            } else {
                list.add(1);
            }
        }
        if (list.size() == n) {
            io.println(0 + " " + 1);
            return;
        }
        long cnt = 0, sum = 1L;
        for (int x : list) {
            if (x == 1) continue;
            cnt += x - 1;
            sum = sum * x % MOD;
        }
        for (long i = cnt; i >= 1; i--) {
            sum = sum * i % MOD;
        }
        io.println(cnt + " " + sum);
    }
}
 
class FastIO extends PrintWriter {
    private BufferedReader br;
    private StringTokenizer st;
 
    public FastIO() {
        this(System.in, System.out);
    }
 
    public FastIO(InputStream in, OutputStream out) {
        super(out);
        br = new BufferedReader(new InputStreamReader(in));
    }
 
    public FastIO(String input, String output) throws FileNotFoundException {
        super(output);
        br = new BufferedReader(new FileReader(input));
    }
 
    public String next() {
        try {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public int nextInt() {
        return Integer.parseInt(next());
    }
 
    public double nextDouble() {
        return Double.parseDouble(next());
    }
 
    public long nextLong() {
        return Long.parseLong(next());
    }
}
