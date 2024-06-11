package Math.N11444;

import static java.lang.Long.parseLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final static long DIV = 1_000_000_007;
    static long N;
    static long[][] mtx;

    public static void main(String[] args) throws IOException {
        N = parseLong(br.readLine());
        mtx = new long[2][2];
        mtx = recursion(N);
        System.out.println(mtx[1][0]%DIV);
    }

    private static long[][] recursion(long N) {
        if (N == 1) {
            return new long[][]{{1, 1}, {1, 0}};
        }
        long[][] buff = recursion(N / 2);
        if (N % 2 == 0) {
            return matrixCal(buff, buff);
        }
        return matrixCal(matrixCal(buff, buff), recursion(1));
    }

    private static long[][] matrixCal(long[][] mtx0, long[][] mtx1) {
        return new long[][]
            {
                {
                    ((mtx0[0][0] * mtx1[0][0]) % DIV + (mtx0[0][1] * mtx1[1][0]) % DIV),
                    ((mtx0[1][0] * mtx1[0][0]) % DIV + (mtx0[1][1] * mtx1[1][0]) % DIV)
                },
                {
                    ((mtx0[0][0] * mtx1[0][1]) % DIV + (mtx0[0][1] * mtx1[1][1]) % DIV),
                    ((mtx0[1][0] * mtx1[0][1]) % DIV + (mtx0[1][1] * mtx1[1][1]) % DIV)
                }
            };
    }
}