package DP.N26607;
/*
B = K - A
Sig Str + Sig Spd = K*X
Sig Spd = Sig Str + Sig Spd - Sig Str
	= K*X - Sig Str
 */

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, K, X, MAX;
	static boolean[][] dp; // k, Sig Str
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		K = parseInt(st.nextToken());
		X = parseInt(st.nextToken());

		dp = new boolean[K + 1][X * K + 1];
		dp[0][0] = true;
		for (int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());
			int str = parseInt(st.nextToken());
			int spd = parseInt(st.nextToken());

			for (int k = K; k >= 1; k--) {
				for (int strSum = X * K; strSum >= str; strSum--) {
					dp[k][strSum] = dp[k][strSum] || dp[k - 1][strSum - str];
					if (dp[K][strSum]) {
						MAX = Math.max(MAX, getStatus(strSum));
					}
				}
			}
		}

		System.out.println(MAX);
	}

	private static int getStatus(int strSum) {
		return strSum * (K * X - strSum);
	}
}
