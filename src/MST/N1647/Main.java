package MST.N1647;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/*
MST 후 가장 높은 가치의 노드를 제거
 */
public class Main {
	static int N, M, MAX = 0;
	static List<Node> map;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());

		map = new ArrayList<>();
		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			int x = parseInt(st.nextToken());
			int y = parseInt(st.nextToken());
			int v = parseInt(st.nextToken());
			map.add(new Node(y, x, v));
		}

		Collections.sort(map);
		Union union = new Union(N);
		long totalValue = 0;
		for (Node node : map) {
			if (union.union(node.x, node.y)) {
				totalValue += node.value;
				MAX = Math.max(MAX, node.value);
			}
		}
		System.out.println(totalValue - MAX);
	}
}

class Node implements Comparable<Node> {
	int y, x, value;

	public Node(int y, int x, int value) {
		this.y = y;
		this.x = x;
		this.value = value;
	}

	@Override
	public int compareTo(Node o) {
		return (this.value - o.value);
	}
}

class Union {
	static int[] parent, rank;

	public Union(int N) {
		parent = new int[N + 1];
		rank = new int[N + 1];
		for (int i = 1; i < N + 1; i++) {
			parent[i] = i;
			rank[i] = 1;
		}
	}

	int findRoot(int x) {
		if (parent[x] != x) {
			parent[x] = findRoot(parent[x]);
		}
		return parent[x];
	}

	boolean union(int x, int y) {
		int rootX = findRoot(x);
		int rootY = findRoot(y);
		if (rootX == rootY) {
			return false; // 이미 같은 집합에 속해 있음
		}
		// 랭크를 비교하여 작은 트리를 큰 트리에 연결
		if (rank[rootX] < rank[rootY]) {
			parent[rootX] = rootY;
		} else if (rank[rootX] > rank[rootY]) {
			parent[rootY] = rootX;
		} else {
			parent[rootY] = rootX;
			rank[rootX]++;
		}
		return true; // 합병 성공
	}
}
