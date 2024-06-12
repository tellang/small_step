package Math.N11443;
/*
짝수: N + 1
홀수: N
번째 - 1
 */

import static java.lang.Long.parseLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    static long N, DIV = 1_000_000_007;
    static long[][] D_MTX = new long[][]{{1, 1}, {1, 0}};
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        N = parseLong(br.readLine());
        if(N % 2 == 0)
            N++;
        long[][] mtx = recursion(N);
        System.out.println((mtx[0][1]%DIV)-1);
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
