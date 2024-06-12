package Math.N2086;

import static java.lang.Long.parseLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {
    static long A, B, DIV = 1_000_000_000;
    static long[][] D_MTX = new long[][]{{1, 1}, {1, 0}};
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        A = parseLong(st.nextToken());
        B = parseLong(st.nextToken());
        long big = 0, small = 0;
        if (B > 0)
            big = getFiboSum(B);
        if (A > 1)
            small = getFiboSum(A-1);

        long result = (big - small) % DIV;
        if (result < 0)
            result = DIV+result;
        System.out.println(result);
    }

    private static long getFiboSum(long n) {
        long[][] mtx = recursion(n);
        return getEvenSum(n, mtx) + getOddSum(n, mtx);
    }

    private static long getOddSum(long n, long[][] mtx) {
        int idx = 1;
        if(n % 2 == 0)
            idx = 0;
        return (mtx[0][idx]%DIV)-1;
    }

    private static long getEvenSum(long n, long[][] mtx) {
        int idx = 1;
        if(n % 2 != 0)
            idx = 0;
        return mtx[0][idx]%DIV;
    }

    private static long[][] recursion(long N) {
        if (N == 1)
            return D_MTX;
        long[][] mtx = recursion(N / 2);
        if(N % 2 == 0)
            return multiMtx(mtx, mtx);
        else
            return multiMtx(multiMtx(mtx, mtx), D_MTX);
    }

    private static long[][] multiMtx(long[][] mtx0, long[][] mtx1) {
        long a, b, c, d;
        a = (mtx0[0][0] * mtx1[0][0])%DIV + (mtx0[0][1] * mtx1[1][0])%DIV;
        b = (mtx0[0][0] * mtx1[1][0])%DIV + (mtx0[0][1] * mtx1[1][1])%DIV;
        c = (mtx0[1][0] * mtx1[0][0])%DIV + (mtx0[1][1] * mtx1[1][0])%DIV;
        d = (mtx0[1][0] * mtx1[1][0])%DIV + (mtx0[1][1] * mtx1[1][1])%DIV;

        return new long[][]
            {
                {a, b},
                {c, d}
            };
    }
}

