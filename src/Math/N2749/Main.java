package Math.N2749;

import static java.lang.Long.parseLong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final static int DIV = 1_000_000;
    static long N;
    static int[][] mtx;

    public static void main(String[] args) throws IOException {
        N = parseLong(br.readLine());
        mtx = new int[2][2];
        mtx = recursion(N);
        System.out.println(mtx[1][0]%DIV);
    }

    private static int[][] recursion(long N) {
        if (N == 1) {
            return new int[][]{{1, 1}, {1, 0}};
        }
        int[][] buff = recursion(N / 2);
        if (N % 2 == 0) {
            return matrixCal(buff, buff);
        }
        return matrixCal(matrixCal(buff, buff), recursion(1));
    }

    private static int[][] matrixCal(int[][] mtx0, int[][] mtx1) {
        int a = (int) (((long) mtx0[0][0] * mtx1[0][0]) % DIV + ((long) mtx0[0][1] * mtx1[1][0]) % DIV);
        int b = (int) (((long) mtx0[1][0] * mtx1[0][0]) % DIV + ((long) mtx0[1][1] * mtx1[1][0]) % DIV);
        int c = (int) (((long) mtx0[0][0] * mtx1[0][1]) % DIV + ((long) mtx0[0][1] * mtx1[1][1]) % DIV);
        int d = (int) (((long) mtx0[1][0] * mtx1[0][1]) % DIV + ((long) mtx0[1][1] * mtx1[1][1]) % DIV);
        return new int[][]
            {
                {a, b},
                {c, d}
            };
    }
}