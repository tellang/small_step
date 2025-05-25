package DP.N11066;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int T, K;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static long[][] dp, w;
	static long[] p;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		T = parseInt(br.readLine());
		for (int t = 0; t < T; t++) {
			K = parseInt(br.readLine());
			dp = new long[K + 1][K + 1];
			w = new long[K + 1][K + 1];
			p = new long[K + 1];
			st = new StringTokenizer(br.readLine());
			for (int k = 1; k <= K; k++) {
				p[k] = parseInt(st.nextToken());
				dp[k][k] = p[k];
			}
			for (int len = 2; len <= K; len++) {
				for (int s = 1; s <= K - len + 1; s++) {
					int e = len + s - 1;
					dp[s][e] = Long.MAX_VALUE; //112 11 12
					w[s][e] = Long.MAX_VALUE;
					for (int k = s; k < e; k++) {
						dp[s][e] = Math.min(dp[s][e], dp[s][k] + dp[k + 1][e]);
						w[s][e] = Math.min(w[s][e], w[s][k] + w[k + 1][e] + dp[s][e]);
					}
				}
			}
			sb.append(w[1][K]).append("\n");
		}
		System.out.println(sb);
	}
}
