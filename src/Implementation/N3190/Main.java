package Implementation.N3190;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static int N, K, L, APPLE = 'A', WALL = 'W', NULL = 0, WORM = 'O', WAY_IDX = 0, TIME = 0;
	static int[][] map;
	static Deque<Node> dq = new ArrayDeque<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[] mv = {0 ,1, 0, -1, 0};//ㅏ ㅜ ㅓ ㅗ
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		map = new int[N+2][N+2];
		for (int i = 0; i < N+2; i++) {
			map[i][0] = WALL;
			map[i][N+1] = WALL;
			map[0][i] = WALL;
			map[N+1][i] = WALL;
		}
		K = Integer.parseInt(br.readLine());
		for (int k = 0; k < K; k++) {
			st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			map[y][x] = APPLE;
		}
		L = Integer.parseInt(br.readLine());
		dq.offerFirst(new Node(1, 1));
		map[1][1] = WORM;
		for (int l = 0; l < L; l++) {
			st = new StringTokenizer(br.readLine());
			int nextChangeTime = Integer.parseInt(st.nextToken());
			char nextWay = st.nextToken().charAt(0);
			while (TIME < nextChangeTime) {
				TIME++;
				int yOffset = mv[WAY_IDX];
				int xOffset = mv[WAY_IDX+1];
				Node head = dq.peekFirst();
				Node nextHead = new Node(head.y + yOffset, head.x + xOffset);
				dq.offerFirst(nextHead);
				if (map[nextHead.y][nextHead.x] == NULL) {
					Node tail = dq.pollLast();
					map[tail.y][tail.x] = NULL;
				} else if (map[nextHead.y][nextHead.x] >= WORM) {
					System.out.println(TIME);
					System.exit(0);
				}
				map[nextHead.y][nextHead.x] = WORM;
			}
			changeWayIdx(nextWay);
		}
		while (true) {
			TIME++;
			int yOffset = mv[WAY_IDX];
			int xOffset = mv[WAY_IDX+1];
			Node head = dq.peekFirst();
			Node nextHead = new Node(head.y + yOffset, head.x + xOffset);
			dq.offerFirst(nextHead);
			if (map[nextHead.y][nextHead.x] == NULL) {
				Node tail = dq.pollLast();
				map[tail.y][tail.x] = NULL;
			} else if (map[nextHead.y][nextHead.x] >= WORM) {

				System.out.println(TIME);
				System.exit(0);
			}
			map[nextHead.y][nextHead.x] = WORM;
		}
	}
	private static int spinWayIdx(char c){
		return c == 'L' ? -1 : 1;
	}
	static void changeWayIdx(char c){
		int wayIdx = spinWayIdx(c);
		WAY_IDX += wayIdx;
		if (WAY_IDX < 0){
			WAY_IDX = 3;
		} else if (WAY_IDX > 3){
			WAY_IDX = 0;
		}
	}
}
class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}
