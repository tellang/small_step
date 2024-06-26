package Math.N11778;

import static java.lang.Long.max;
import static java.lang.Long.min;
import static java.lang.Long.parseLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static long DIV = 1_000_000_007;
    static long N, M;
    static long[][] D_MTX = {{1, 1}, {1, 0}};
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseLong(st.nextToken());
        M = parseLong(st.nextToken());
        long big = max(N, M);
        long small = min(N, M);
        System.out.println(getFibo(getGCD(big, small)));
    }

    private static long getGCD(long big, long small) {
        while (small != 0) {
            long r = big % small;
            big = small;
            small = r;
        }
        return big;
    }

    private static long getFibo(long n) {
        long[][] mtx = recursion(n);
        return mtx[0][1] % DIV;
    }

    private static long[][] recursion(long n) {
        if (n == 1)
            return D_MTX;
        long[][] mtx = recursion(n / 2);
        if (n % 2 == 0) {
            return multiMtx(mtx, mtx);
        } else
            return multiMtx(multiMtx(mtx, mtx), D_MTX);
    }

    private static long[][] multiMtx(long[][] mtx0, long[][] mtx1) {
        long a, b, c, d;
        a = (mtx0[0][0] * mtx1[0][0]) % DIV + (mtx0[0][1] * mtx1[1][0]) % DIV;
        b = (mtx0[0][0] * mtx1[0][1]) % DIV + (mtx0[0][1] * mtx1[1][1]) % DIV;
        c = (mtx0[1][0] * mtx1[0][0]) % DIV + (mtx0[1][1] * mtx1[1][0]) % DIV;
        d = (mtx0[1][0] * mtx1[0][1]) % DIV + (mtx0[1][1] * mtx1[1][1]) % DIV;

        return new long[][]{
            {a, b},
            {c, d}
        };
    }
}
