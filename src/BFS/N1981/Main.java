package BFS.N1981;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {
	final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static ArrayDeque<Node> q = new ArrayDeque<>();
	static int N, MAX = 200;
	static int[][] map, visited;
	static int time = 1;
	static int[] mv = {-1, 0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		visited = new int[N][N];

		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine());
			for (int x = 0; x < N; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		int left = 0, right = 100, answer = 100;

		while (left <= right) {
			int mid = (left + right) >> 1;
			boolean find = false;

			for (int min = 0; min + mid <= MAX; min++) {
				int max = min + mid;

				if (!isUnderCondition(min, max, map[0][0]))
					continue;

				q.clear();
				q.offer(new Node(0, 0));
				visited[0][0] = time;

				while (!q.isEmpty()) {
					Node node = q.poll();

					if (node.y == N - 1 && node.x == N - 1) {
						find = true;
						break;
					}

					for (int i = 0; i < 4; i++) {
						int ny = node.y + mv[i];
						int nx = node.x + mv[i + 1];
						if (isValid(ny, nx) && visited[ny][nx] != time) {
							int val = map[ny][nx];
							if (!isUnderCondition(min, max, val))
								continue;

							visited[ny][nx] = time;
							q.offer(new Node(ny, nx));
						}
					}
				}
				time++;
				if (find)
					break;
			}

			if (find) {
				answer = mid;
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}

		System.out.println(answer);
	}

	private static boolean isUnderCondition(int min, int max, int value) {
		return value >= min && value <= max;
	}

	static boolean isValid(int y, int x) {
		return y >= 0 && y < N && x >= 0 && x < N;
	}
}

class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}