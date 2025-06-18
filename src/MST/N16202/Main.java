package MST.N16202;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
한번 구성에 실패한 MST 의 가지를 제거한다 해도 구성에 성공하지 않는다

가지 삭제후 재구성
 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M, K;
	static StringTokenizer st;
	static Node[] map;
	static int[] RESULT;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new Node[M + 1];
		RESULT = new int[K];

		for (int m = 1; m <= M; m++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			map[m] = new Node(a, b);
		}
		UnionFind uf;

		for (int k = 0; k < K; k++) {
			int localResult = 0;
			int edgeCount = 0;
			uf = new UnionFind(N);
			for (int i = k + 1; i <= M; i++) {
				if (uf.canUnion(i)) {
					localResult += i;
					edgeCount++;
				}
			}

			if (uf.isTree(edgeCount))
				RESULT[k] = localResult;
			else
				break;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < K; i++) {
			sb.append(RESULT[i]).append(' ');
		}
		System.out.println(sb);
	}
}

class Node {
	int x, y;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class UnionFind {
	int[] parent;
	int[] rank;

	public UnionFind(int n) {
		parent = new int[n + 1];
		rank = new int[n + 1];
		for (int i = 0; i < n + 1; i++) {
			parent[i] = i;
		}
	}

	public boolean isTree(int edgeCount) {
		int c = 0;
		if (edgeCount != Main.N - 1)
			return false;
		for (int i = 1; i <= Main.N; i++) {
			if (parent[i] == i)
				c++;
		}
		return c < 2;
	}

	public boolean canUnion(int i) {
		Node node = Main.map[i];
		int rootX = findRoot(node.x);
		int rootY = findRoot(node.y);

		if (rootX == rootY)
			return false;
		if (rank[rootX] > rank[rootY])
			parent[rootY] = rootX;
		else if (rank[rootX] < rank[rootY])
			parent[rootX] = rootY;
		else {
			parent[rootY] = rootX;
			rank[rootX]++;
		}

		return true;
	}

	public int findRoot(int i) {
		if (parent[i] == i)
			return i;
		return parent[i] = findRoot(parent[i]);
	}
}