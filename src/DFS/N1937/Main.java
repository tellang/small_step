/*
DP[y][x] : (y, x) 에서 갈 수 있는 노드 갯수
 */


package DFS.N1937;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, MAX = 0;
    static int[][] map, dp;
    static int[] move = {-1, 0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        N = parseInt(br.readLine());
        map = new int[N][N];
        dp = new int[N][N];
        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < N; x++) {
                map[y][x] = parseInt(st.nextToken());
            }
        }
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (dp[y][x] == 0)
                    MAX = max(dfs(y, x), MAX);
            }
        }
//        printMap(dp);
        System.out.println(MAX);
    }

    private static int dfs(int y, int x) {
        if (dp[y][x] != 0)
            return dp[y][x];
        dp[y][x]++;
//        printMap(dp);
        for (int i = 0; i < 4; i++) {
            int ny = y + move[i];
            int nx = x + move[i + 1];

            if (isValid(ny, nx) && isIncreasing(y, x, ny, nx)) {
                dp[y][x] = max(dp[y][x], dfs(ny, nx) + 1);
            }
        }
        return dp[y][x]; //localMax
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    static boolean isIncreasing(int y, int x, int ny, int nx) {
        return map[y][x] < map[ny][nx];
    }

    static void printMap(int[][] map) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                sb.append(map[y][x]).append(' ');
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
