package DP.N2056;
/*
아무리 봐도 위상정렬인데
DP도 써야하네
 */


import static java.lang.Integer.*;
import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;



public class Main {
	static Queue<Order> orders = new PriorityQueue<>(Comparator.comparingInt(Main::getPreSize));
	static Set<Integer>[] preOrder;
	static HashMap<Integer, Integer> time = new HashMap<>();
	static int N;
	static int K;
	static int ORDER = 1;
	static int RESULT = 0;
	static int[] localMaxList;
	static BufferedReader br = new BufferedReader(new InputStreamReader(in));
	static StringTokenizer st;
	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());

		preOrder = new HashSet[N+1];
		for (int i = 0; i <= N; i++) {
			preOrder[i] = new HashSet<>();
		}
		localMaxList = new int[N+1];
		// orders.add(new Order(0, 0)); //dummy
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			HashSet<Integer> preset = new HashSet<>();
			int t = parseInt(st.nextToken());
			K = parseInt(st.nextToken());
			for (int k = 0; k < K; k++) {
				preset.add(parseInt(st.nextToken()));
			}
			time.put(ORDER, t);
			orders.add(new Order(t, ORDER));
			preOrder[ORDER] = preset;
			ORDER++;

		}


		while (!orders.isEmpty()) {
			Order poll = orders.poll();
			int localMax = 0;
			for (int p : preOrder[poll.n]) {
				localMax = max(localMax, localMaxList[p]);
			}
			for (int i = 0; i < preOrder.length; i++) {
				Set<Integer> p = preOrder[i];
				if (p.contains(poll.n)) {
					p.remove(poll.n);
					localMaxList[i] = Math.max(localMaxList[i], poll.t);
				}
			}

			time.put(poll.n, time.get(poll.n) + localMax);
			RESULT = Math.max(RESULT, time.get(poll.n));
		}

		out.println(RESULT);
	}
	static int getPreSize(Order order) {
		return preOrder[order.n].size();
	}
}
class Order {
	int t, n;

	public Order(int t, int n) {
		this.t = t;
		this.n = n;
	}
}
