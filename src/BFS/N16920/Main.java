package BFS.N16920;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {

	final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M, PN;
	static char NULL = '.', WALL = '#';
	static char[][] map;
	static int[] S,
		move = {-1, 0, 1, 0, -1},
		COUNTER, TOTAL_TURN;
	static ArrayDeque<Node>[] q;
	static ArrayDeque<Integer> turnPlayer = new ArrayDeque<>();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		PN = parseInt(st.nextToken());
		map = new char[N][M];
		S = new int[PN + 1];
		COUNTER = new int[PN + 1];
		TOTAL_TURN = new int[PN + 1];
		q = new ArrayDeque[PN + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= PN; i++) {
			S[i] = parseInt(st.nextToken());
			q[i] = new ArrayDeque<>();
			turnPlayer.offer(i);
		}
		for (int n = 0; n < N; n++) {
			map[n] = br.readLine().toCharArray();
			for (int m = 0; m < M; m++) {
				int p = map[n][m] - '0';
				if (p >= 1 && p <= 9) {
					q[p].offer(new Node(n, m, 0));
					COUNTER[p]++;
				}
			}
		}
		while (!turnPlayer.isEmpty()) {
			int turn = turnPlayer.poll();
			TOTAL_TURN[turn]++;
			if (!q[turn].isEmpty()) {
				while (!q[turn].isEmpty() &&
					q[turn].peekFirst().d < TOTAL_TURN[turn] * S[turn]) { //d 값에 따른 우선순위가 필요 한가?->

					Node node = q[turn].poll();

					for (int i = 0; i < 4; i++) {
						int nx = node.x + move[i];
						int ny = node.y + move[i + 1];
						int nd = node.d + 1;
						if (isValid(ny, nx)) {
							map[ny][nx] = (char)('0' + turn);
							q[turn].offer(new Node(ny, nx, nd));
							COUNTER[turn]++;
						}
					}
				}
				turnPlayer.offer(turn);
			}
		}
		StringBuilder result = new StringBuilder();
		for (int i = 1; i <= PN; i++) {
			result.append(COUNTER[i]).append(' ');
		}
		System.out.println(result);
	}

	private static boolean isValid(int ny, int nx) {
		return ny >= 0 && ny < N && nx >= 0 && nx < M && map[ny][nx] == NULL;
	}

}

class Node {

	int y, x, d;

	public Node(int y, int x, int d) {
		this.y = y;
		this.x = x;
		this.d = d;
	}
}
