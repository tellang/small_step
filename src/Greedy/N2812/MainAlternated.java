package Greedy.N2812;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
이게 왜 안될까
 */

public class MainAlternated {
	static int N, K;
	static int[] idx;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static char[] num;

	public static void main(String[] args) throws IOException {
		while (true) {
			String result = result();
			System.out.println(result);
			try {

				System.out.println(br.readLine());
				System.out.println(br.readLine());
			} catch (IOException e) {
				System.exit(0);
			}

		}
	}

	private static String result() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		num = new char[N + 1];
		char[] buff = br.readLine().toCharArray();
		for (int n = 0; n < N; n++) {
			num[n] = buff[n];
		}
		num[N] = '9' + 1;
		idx = new int[N + 2];
		for (int n = 0; n <= N; n++) {
			idx[n] = n;
		}

		int start = 0;
		int k = 0;
		int l = findRoot(start);
		int r = findRoot(l + 1);
		while (k < K) {
			if (num[l] < num[r]) {
				num[l] = '@';
				MainAlternated.idx[l] = r;
				k++;
				l = l - 1;
				if (l >= 0) {
					l = findRoot(l);
					continue;
				}
			}
			l = r;
			r = findRoot(r + 1);
		}
		StringBuilder sb = new StringBuilder();
		for (int n = findRoot(0); n < N; n = findRoot(n + 1)) {
			sb.append(num[n]);
		}

		return sb.toString();
		// System.out.println(Arrays.toString(idx));
		// System.out.println(Arrays.toString(num));
	}

	// public static int findRoot(int n) {
	// 	if (idx[n] == n)
	// 		return n;
	// 	return idx[n] = findRoot(idx[n]);
	// }
	public static int findRoot(int n) {
		while (idx[n] != n) {
			idx[n] = idx[idx[n]];
			n = idx[n];
		}
		return n;
	}
}
