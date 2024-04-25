package DP.N2225;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int MAX = 1_000_000_000;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] dp = new int[N+1][K+1];
        for (int n = 0; n < N+1; n++) {
            dp[n][1] = 1;
        }
        for (int k = 1; k <= K; k++) {
            dp[0][k] = 1;
        }

        for (int n = 1; n <= N; n++) {
            for (int k = 2; k <= K; k++) {
                dp[n][k] = (dp[n-1][k] + dp[n][k-1])%MAX;
            }
        }
        System.out.println(dp[N][K]);
    }

}
