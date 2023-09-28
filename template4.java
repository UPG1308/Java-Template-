import java.io.*;
import java.util.*;
 
 
public class Main {
    void go() {
        n = nextInt();
        a = new int[n];
        for (int i = 1; i < a.length; ++i) a[i] = nextInt() - 1;
        a[0] = -1;
        d = new ArrayList[n];
        for (int i = 0; i < n; ++i) d[i] = new ArrayList<>();
        for (int i = 1; i < n; ++i) d[a[i]].add(i);
        if (d[0].size() == n - 1) {
            m = 1;
            e = new int[m];
            sl(1);
            for (int i = 0; i < n - 1; ++i) so(1 + " ");
            sl();
            while (!judge()) {
                sl(1);
            }
            return;
        }
        if (trygo2()) {
            return;
        }
        go3();
    }
 
    int[] a;
    int m;
    int[] e;
    List<Integer>[] d;
    int[] c;
    int n;
 
    void go3() {
        m = 3;
        e = new int[m];
        c = new int[n];
        c[0] = 0;
        for (int i = 1; i < n; ++i) c[i] = (c[a[i]] + 1) % m;
        sl(m);
        for (int i = 1; i < n; ++i) so((c[i] + 1) + " ");
        sl();
        while (!judge()) {
            if (e[0] == 0 && e[1] == 0) sl(3);
            else if (e[0] == 0 && e[2] == 0) sl(2);
            else if (e[1] == 0 && e[2] == 0) sl(1);
            else {
                for (int i = 0; i < 3; ++i) {
                    if (e[i] == 0) {
                        sl((i + 1) % m + 1);
                        break;
                    }
                }
            }
        }
    }
 
    boolean judge() {
        int r = nextInt();
        if (r == 1) return true;
        if (r == -1) return true;
        for (int i = 0; i < m; ++i) e[i] = nextInt();
        return false;
    }
 
    boolean trygo2() {
        c = new int[n];
        for (int i = n - 1; i >= 1; --i) {
            if (d[i].size() == 1) {
                int x = d[i].get(0);
                if (c[i] == 2 || c[x] == 1) return false;
                c[x] = 2;
                c[i] = 1;
            } else if (d[i].size() >= 1) {
                int[] g = new int[3];
                for (int x : d[i]) g[c[x]]++;
                if (g[1] > 0 && g[2] > 0) return false;
                if (g[1] > 0) {
                    for (int x : d[i]) c[x] = 1;
                    c[i] = 2;
                } else if (g[2] > 0) {
                    for (int x : d[i]) c[x] = 2;
                    c[i] = 1;
                }
            }
        }
        for (int i = 1; i < n; ++i) {
            if (c[i] == 0) {
                if (c[a[i]] != 0) c[i] = 3 - c[a[i]];
            }
        }
        for (int i = n - 1; i >= 1; --i) {
            if (c[i] != 0) continue;
            if (d[i].size() > 0) {
                int[] g = new int[3];
                for (int x : d[i]) g[c[x]]++;
                if (g[1] > 0 && g[2] > 0) return false;
                if (g[1] > 0) {
                    for (int x : d[i]) c[x] = 1;
                    c[i] = 2;
                } else if (g[2] > 0) {
                    for (int x : d[i]) c[x] = 2;
                    c[i] = 1;
                }
            }
        }
        for (int i = 1; i < n; ++i) {
            if (c[i] == 0) {
                if (c[a[i]] != 0) c[i] = 3 - c[a[i]];
                else c[i] = 1;
            }
        }
        m = 2;
        e = new int[m];
        sl(m);
        for (int i = 1; i < n; ++i) so((c[i]) + " ");
        sl();
        while (!judge()) {
            if (e[0] == 0) sl(2);
            else if (e[1] == 0) sl(1);
            else if (e[0] == 1 && e[1] == 1) {
                sl(1);
            } else if (e[0] == 1) {
                sl(1);
            } else {
                sl(2);
            }
        }
        return true;
    }
 
 
    boolean MULTI_CASE = false;
    boolean ALWAYS_FLUSH = true;
 
 
    /**
     * Input.
     */
    InputStream inStream;
    byte[] inBuff = new byte[1024];
    int inBuffCursor = 0, inBuffLen = 0;
 
    boolean isVisibleChar(int c) {
        return 33 <= c && c <= 126;
    }
 
    boolean hasNextByte() {
        if (inBuffCursor < inBuffLen) return true;
        inBuffCursor = 0;
        try {
            inBuffLen = inStream.read(inBuff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inBuffLen > 0;
    }
 
    boolean hasNext() {
        while (hasNextByte() && !isVisibleChar(inBuff[inBuffCursor])) inBuffCursor++;
        return hasNextByte();
    }
 
    int nextByte() {
        return hasNextByte() ? inBuff[inBuffCursor++] : -1;
    }
 
    String next() {
        if (!hasNext()) throw new RuntimeException("no next.");
        StringBuilder sb = new StringBuilder();
        int b = nextByte();
        while (isVisibleChar(b)) {
            sb.appendCodePoint(b);
            b = nextByte();
        }
        return sb.toString();
    }
 
    long nextLong() {
        if (!hasNext()) throw new RuntimeException("no next.");
        long result = 0;
        boolean negative = false;
        int b = nextByte();
        if (b < '0') {
            if (b == '-') negative = true;
            else if (b != '+') throw new RuntimeException("long number must start with +/-.");
            b = nextByte();
        }
        while (isVisibleChar(b)) {
            if (b < '0' || b > '9') throw new RuntimeException("wrong digit in long:" + (char) b);
            // TODO: may overflow here, even if it is a valid Long, eg.-(1<<63).
            result = result * 10 + (b - '0');
            b = nextByte();
        }
        return negative ? -result : result;
    }
 
    int nextInt() {
        long x = nextLong();
        if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE)
            throw new RuntimeException("int overflow:" + x);
        return (int) x;
    }
 
    double nextDouble() {
        return Double.parseDouble(next());
    }
 
    /**
     * Output.
     */
    PrintWriter printOut = new PrintWriter(System.out);
 
    void so(Object obj, boolean newLine) {
        if (newLine) printOut.println(obj);
        else printOut.print(obj);
        if (ALWAYS_FLUSH) printOut.flush();
    }
 
    void so(Object obj) {
        so(obj, false);
    }
 
    void sl(Object obj) {
        so(obj, true);
    }
 
    void sl() {
        sl("");
    }
 
    /**
     * Main.
     */
    void mainGo() {
        try {
            inStream = new FileInputStream("src/main.in");
            ALWAYS_FLUSH = true;
        } catch (Exception e) {
            inStream = System.in;
        }
        while (hasNext()) {
            if (MULTI_CASE) {
                int T = nextInt();
                if (T == 0) break;
                for (int i = 0; i < T; ++i) {
                    go();
                }
            } else {
                go();
            }
        }
        printOut.flush();
    }
 
    public static void main(String[] args) throws Exception {
        new Main().mainGo();
    }
}
