package Queue.N1766;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/*
A -> B
 */
public class Main {

	static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M, MAX = 1_000_000;
	static StringTokenizer st;
	static int[] counter;
	static List<Integer>[] map;
	static List<Integer> heap;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		counter = new int[N + 1];
		counter[0] = -1;
		map = new ArrayList[N + 1];
		heap = new ArrayList<>();
		for (int i = 1; i <= N; i++) {
			map[i] = new ArrayList<>();
		}
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int a = parseInt(st.nextToken());
			int b = parseInt(st.nextToken());
			map[a].add(b);
			counter[b]++;
		}

		for (int n = 1; n <= N; n++) {
			heap.add(n);
		}
		heap.sort(getIntegerComparator());
		while (!heap.isEmpty()) {
			int solved = heap.get(0);
			counter[solved] = MAX;
			sb.append(solved).append(' ');
			for (int i : map[solved]) {
				if (counter[i] == MAX)
					continue;
				counter[i]--;
			}
			heap.remove(0);
			heap.sort(getIntegerComparator());
		}
		System.out.println(sb);
	}

	private static Comparator<Integer> getIntegerComparator() {
		return (o1, o2) ->
		{
			if (counter[o1] == counter[o2]) {
				return o1 - o2;
			}
			return counter[o1] - counter[o2];
		};
	}
}
