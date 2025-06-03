package Greedy.N2812;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
	static int N, K;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static char[] num;
	static ArrayDeque<Character> dq = new ArrayDeque<>();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		num = br.readLine().toCharArray();

		int k = K;
		for (int i = 0; i < N; i++) {
			while (!dq.isEmpty() && k > 0 && dq.peekLast() < num[i]) {
				dq.removeLast();
				k--;
			}
			dq.addLast(num[i]);
		}

		while (k > 0) {
			dq.removeLast();
			k--;
		}

		StringBuilder sb = new StringBuilder();
		for (char c : dq) {
			sb.append(c);
		}

		System.out.println(sb);
	}
}