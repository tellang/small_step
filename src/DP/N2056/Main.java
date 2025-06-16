package DP.N2056;
/*
선행 작업의 번호는 현재 작업보다 작다!
10K x sigma(10k)
= 10K x 10k(10k-1)/2
=> 1000k/2 = 0.5M
 */

import static java.lang.Integer.*;
import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int K;
	static int MAX = 0;
	static int[] timeList;
	static BufferedReader br = new BufferedReader(new InputStreamReader(in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());
		timeList = new int[N + 1];
		for (int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());

			int t = parseInt(st.nextToken());
			K = parseInt(st.nextToken());
			timeList[n] = t;
			int localMax = 0;
			for (int k = 0; k < K; k++) {
				localMax = Math.max(localMax, timeList[Integer.parseInt(st.nextToken())]);
			}
			timeList[n] += localMax;
			MAX = Math.max(MAX, timeList[n]);
		}

		out.println(MAX);
	}

}
