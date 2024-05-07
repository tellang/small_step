package DP.N2096;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.BinaryOperator;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[][] maxDp, minDp;
    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        maxDp = new int[N][5];
        minDp = new int[N][5];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 3; j++) {
                maxDp[i][j] = Integer.parseInt(st.nextToken());
                minDp[i][j] = maxDp[i][j];
            }
            minDp[i][0] = Integer.MAX_VALUE;
            minDp[i][4] = Integer.MAX_VALUE;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= 3; j++) {
                maxDp[i][j] += tripleExp(i, j, maxDp, Math::max);
                minDp[i][j] += tripleExp(i, j, minDp, Math::min);
            }
        }
        System.out.println(
            new StringBuilder()
                .append(tripleExp(maxDp[N-1][1], maxDp[N-1][2], maxDp[N-1][3], Math::max))
                .append(' ')
                .append(tripleExp(minDp[N-1][1], minDp[N-1][2], minDp[N-1][3], Math::min)));
    }

    private static int tripleExp(int i, int j, int[][] map, BinaryOperator<Integer> op) {
        return tripleExp(map[i-1][j], map[i-1][j-1], map[i-1][j+1], op);
    }

    private static int tripleExp(int a, int b, int c, BinaryOperator<Integer> op) {
        return op.apply(a, op.apply(b, c));
    }
}
