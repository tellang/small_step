package Greedy.N13904;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Pair> list = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			list.add(new Pair(parseInt(st.nextToken()), parseInt(st.nextToken())));
		}
		list.sort((o1, o2) ->
		{
			if (o1.w == o2.w) {
				return o1.d - o2.d;
			}
			return o2.w - o1.w;
		});
		int d = 0;
		int w = 0;
		boolean canAdd = true;
		List<Double> pq = new ArrayList<>();

		for (Pair pair : list) {
			canAdd = true;
			int insert = Collections.binarySearch(pq, ((double)pair.d + 0.5));
			insert = ((insert + 1) * (-1));
			if (pair.d > insert && pair.d > d) {
				for (int i = insert; i < pq.size(); i++) {
					if (pq.get(i) <= i + 1) {
						d = i;
						canAdd = false;
						break;
					}
				}
				if (canAdd) {
					w += pair.w;
					pq.add((double)pair.d);
					pq.sort(Comparator.naturalOrder());
				}
			}
		}
		System.out.println(w);
	}
}

class Pair {
	int d, w;

	public Pair(int d, int w) {
		this.d = d;
		this.w = w;
	}
}
