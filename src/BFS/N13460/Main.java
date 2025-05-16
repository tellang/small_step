package BFS.N13460;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N, M, MIN = Integer.MAX_VALUE;
	static int[][][][] visitedTurn; //R.y, R.x, B.y, B.x
	static char[][] map;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static Node GOAL, R, B;
	static int[] move = {-1, 0, 1, 0, -1};
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		visitedTurn = new int[N][M][N][M];
		map = new char[N][M];
		for (int n = 0; n < N; n++) {
			char[] arr = br.readLine().toCharArray();
			for (int m = 0; m < M; m++) {
				map[n][m] = arr[m];
				if (map[n][m] == 'O') {
					GOAL = new Node(n, m);
				} else if (map[n][m] == 'R') {
					R = new Node(n, m);
				} else if (map[n][m] == 'B') {
					B = new Node(n, m);
				}
			}
		}

		ArrayDeque<NodeSet> q = new ArrayDeque<>();
		q.offer(new NodeSet(R, B, 0));
		while (!q.isEmpty()) {
			boolean hasGoal = false;
			NodeSet ns = q.poll();
			Node r = ns.r;
			Node b = ns.b;
			int idx = ns.idx;

			if (visitedTurn[r.y][r.x][b.y][b.x] == 0 || visitedTurn[r.y][r.x][b.y][b.x] > idx){
				visitedTurn[r.y][r.x][b.y][b.x] = idx;
			} else continue;
			if (b.equals(GOAL)) {
				continue;
			}
			if (r.equals(GOAL)) {
				MIN = Math.min(MIN, idx);
				continue;
			}
			if (idx >= 10)
				continue;

			for (int i = 0; i < 4; i++) {
				r = ns.r;
				b = ns.b;
				Node nr = r;
				Node nb = b;
				int ni = idx+1;
				while (true){
					nr = new Node(r.y + move[i], r.x + move[i+1]);
					nb = new Node(b.y + move[i], b.x + move[i+1]);

					if (isWall(nr)) {
						nr = r;
					}
					if (isWall(nb)) {
						nb = b;
					}
					if (nr.equals(r) && nb.equals(b)) {
						break;
					}
					if (!hasGoal && nr.equals(nb)) {
						break;
					}
					r = nr;
					b = nb;
					if (b.equals(GOAL)) {
						r = ns.r;
						b = ns.b;
						hasGoal = false;
						break;
					}
					if (r.equals(GOAL)) {
						hasGoal = true;
					}
				}
				if (hasGoal) {
					MIN = Math.min(MIN, ni);
					break;
				}
				if (ns.r.equals(r) && ns.b.equals(b)) {
					continue;
				}
				q.offer(new NodeSet(r, b, ni));
			}
		}
		if (MIN == Integer.MAX_VALUE) {
			MIN = -1;
		}
		System.out.println(MIN);
	}

	private static boolean isValid(Node n) {
		return isValid(n.y, n.x);
	}

	private static boolean moveable(Node n) {
		return map[n.y][n.x] == '.';
	}

	private static boolean isWall(Node n) {
		return map[n.y][n.x] == '#';
	}

	private static boolean isValid(int y, int x) {
		return y >= 0 && y < N && x >= 0 && x < M;
	}

	private static void printMap(char[][] map) {
		StringBuilder sb = new StringBuilder();
		for (char[] chars : map) {
			sb.append(chars).append('\n');
		}
		System.out.println(sb);
	}
}
class NodeSet {
	Node r, b;
	int idx;

	public NodeSet(Node r, Node b, int idx) {
		this.r = r;
		this.b = b;
		this.idx = idx;
	}
}
class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}

	public boolean equals(Node obj) {
		return this.y == obj.y && this.x == obj.x;
	}
}
