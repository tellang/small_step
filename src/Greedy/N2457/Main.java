package Greedy.N2457;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Term> terms = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			int sm = Integer.parseInt(st.nextToken());
			int sd = Integer.parseInt(st.nextToken());
			int em = Integer.parseInt(st.nextToken());
			int ed = Integer.parseInt(st.nextToken());
			if (sm < 3) {
				sm = 3;
				sd = 1;
			}
			if (em == 12) {
				ed = 1;
			}
			terms.add(new Term(sm, sd, em, ed));
		}
		terms.sort((t0, t1) -> {
			if (t0.end == t1.end) {
				return t1.start - t0.start;
			}
			return t1.end - t0.end;
		});
		int result = 1;
		Term last = terms.get(0);
		if (last.end <= 1130) {
			terms.clear();
			result = 0;
		}
		for (int i = 1; i < terms.size(); i++) {

			Term poll = terms.get(i);
			if (poll.end >= last.end) {
				last.start = Math.min(poll.start, last.start);
			} else {
				if (poll.end < last.start) {
					result = -1;
					break;
				}
				if (poll.start < last.start) {
					result++;
					last.end = last.start;
					last.start = poll.start;
				}
			}
		}
		if (last.start != 301) {
			result = 0;
		}

		System.out.println(result);
	}
}

class Term {
	int start, end;

	Term(int sm, int sd, int em, int ed) {
		this.start = sm * 100 + sd;
		this.end = em * 100 + ed;
	}
}