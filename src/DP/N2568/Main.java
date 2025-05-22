package DP.N2568;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N, MAX;
	static Pair[] pairs;
	static List<Integer> lis = new ArrayList<>();
	static List<Integer> dp = new ArrayList<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static HashMap<Integer, Integer> map = new HashMap<>();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());
		pairs = new Pair[N];
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			pairs[n] = new Pair(parseInt(st.nextToken()), parseInt(st.nextToken()));
			map.put(pairs[n].a, pairs[n].b);
		}
		Arrays.sort(pairs, Comparator.comparingInt(o -> o.b));
		for (Pair pair : pairs) {
			int a = pair.a;
			int i = Collections.binarySearch(lis, a);
			if (i < 0)
				i = -i - 1;
			if (i == lis.size())
				lis.add(a);
			else
				lis.set(i, a);
			dp.add(i + 1);
			MAX = Math.max(MAX, i + 1);
		}
		int lisSize = MAX;
		StringBuilder sb = new StringBuilder();
		List<Integer> lis = new ArrayList<>();
		for (int i = dp.size() - 1; i >= 0; i--) {
			if (dp.get(i) == lisSize) {
				lisSize--;
			} else
				lis.add(pairs[i].a);
		}
		lis.sort(Integer::compareTo);
		sb.append(N - MAX).append('\n');
		for (int n : lis) {
			sb.append(n).append('\n');
		}
		System.out.println(sb);
	}
}

class Pair {
	int a, b;

	public Pair(int a, int b) {
		this.a = a;
		this.b = b;
	}
}