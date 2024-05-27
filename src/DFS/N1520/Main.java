package DFS.N1520;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static int[][] map, dp;
    static boolean[][] isVisited;
    static int[] move = {-1, 0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        M = parseInt(st.nextToken());
        N = parseInt(st.nextToken());
        dp = new int[M][N];
        for (int m = 0; m < M; m++) {
            Arrays.fill(dp[m], -1);
        }
        isVisited = new boolean[M][N];
        map = new int[M][N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = parseInt(st.nextToken());
            }
        }

        System.out.println(dfs(0, 0));
        System.out.println(Arrays.deepToString(dp));
    }

    private static int dfs(int y, int x) {
        if (isFinish(y, x))
            return 1;
        if (dp[y][x] != -1)
            return dp[y][x];
        dp[y][x] = 0;
        for (int i = 0; i < 4; i++) {
            int ny = y + move[i];
            int nx = x + move[i+1];
            if (isValid(ny, nx) && isDecreasing(y, x, ny, nx))
                dp[y][x] += dfs(ny, nx);
        }
        return dp[y][x];
    }

    public static boolean isValid(int y, int x) {
        return y >= 0 && y < M && x >= 0 && x < N;
    }
    public static boolean isFinish(int y, int x) {
        return y==M-1 && x==N-1;
    }
    public static boolean isDecreasing(int y, int x, int ny, int nx) {
        return map[y][x]>map[ny][nx];
    }
}