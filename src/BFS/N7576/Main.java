package BFS.N7576;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

//메모리 단축을 위한 선 방문처리

public class Main {

	static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M,
		RIPED = 1, BEFORE = 0,
		RIPED_COUNT = 0, FULL_RIPED_COUNT;
	static StringTokenizer st;
	static int[][] map;
	static int[] move = {-1, 0, 1, 0, -1};
	static ArrayDeque<Node> q = new ArrayDeque<>();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		M = parseInt(st.nextToken());
		N = parseInt(st.nextToken());
		FULL_RIPED_COUNT = N * M;
		map = new int[N][M];
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			for (int m = 0; m < M; m++) {
				map[n][m] = parseInt(st.nextToken());
				if (map[n][m] == RIPED) {
					q.offer(new Node(n, m));
					RIPED_COUNT++;
				} else if (map[n][m] == BEFORE) {
					map[n][m] = Integer.MAX_VALUE;
				} else {
					FULL_RIPED_COUNT--;
				}
			}
		}
		BEFORE = Integer.MAX_VALUE;
		while (!q.isEmpty()) {
			Node now = q.poll();

			for (int i = 0; i < 4; i++) {
				int ny = now.y + move[i];
				int nx = now.x + move[i + 1];
				int nd = map[now.y][now.x] + 1;
				if (isValid(ny, nx) && map[ny][nx] > nd) {
					q.offer(new Node(ny, nx));
					if (map[ny][nx] == BEFORE) {
						RIPED_COUNT++;
						map[ny][nx] = nd;
					}
				}
			}
		}
		int result = -1;
		if (RIPED_COUNT == FULL_RIPED_COUNT) {
			for (int[] arr : map) {
				for (int i : arr) {
					result = Math.max(result, i);
				}
			}
			result--;
		}
		System.out.println(result);
	}

	private static boolean isValid(int ny, int nx) {
		return ny >= 0 && ny < N && nx >= 0 && nx < M;
	}
}

class Node {

	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}
