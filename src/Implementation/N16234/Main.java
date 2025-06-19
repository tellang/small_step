package Implementation.N16234;
/*
변환후 같은 숫자라 오픈 처리가 안된 경우

그냥 국경별 로직을 분리하자

cq 매커니즘에 문제가 있다

플래그가 필요 없는데
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static int N, L, R, C_COUNT, C_TOTAL, DAY = 0;
	static int[][] valueMap;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static Deque<Node> dq, cq;
	static boolean[][] visited;
	static boolean isOpen = true;
	static int[] mv = {-1, 0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		valueMap = new int[N][N];
		int flag = 0;
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine());
			for (int x = 0; x < N; x++) {
				valueMap[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		while (isOpen) {
			isOpen = false;
			visited = new boolean[N][N];
			for (int yy = 0; yy < N; yy++) {
				for (int xx = 0; xx < N; xx++) {
					if (visited[yy][xx])
						continue;
					C_COUNT = 0;
					C_TOTAL = 0;
					dq = new ArrayDeque<>();
					cq = new ArrayDeque<>();
					dq.offerFirst(new Node(yy, xx));
					visited[yy][xx] = true;
					while (!dq.isEmpty()) {
						Node polled = dq.pollFirst();
						cq.offerFirst(polled);
						C_COUNT++;
						C_TOTAL += valueMap[polled.y][polled.x];

						for (int i = 0; i < 4; i++) {
							int y = polled.y + mv[i];
							int x = polled.x + mv[i + 1];

							if (isValid(y, x) &&
								!visited[y][x] &&
								couldBUnion(polled.y, polled.x, y, x)) {
								isOpen = true;
								visited[y][x] = true;
								dq.offerFirst(new Node(y, x));
							}
						}
					}
					int val = C_TOTAL / C_COUNT;
					while (!cq.isEmpty()) {
						Node polled = cq.pollFirst();
						valueMap[polled.y][polled.x] = val;
					}
				}
			}
			if (!isOpen) {
				System.out.println(DAY);
				return;
			}
			DAY++;

		}
	}

	private static boolean couldBUnion(int y, int x, int ny, int nx) {
		int diff = Math.abs(valueMap[ny][nx] - valueMap[y][x]);
		return diff >= L && diff <= R;
	}

	static boolean isValid(int y, int x) {
		return y >= 0 && y < N && x >= 0 && x < N;
	}
}

class Node {
	int y, x;

	Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}
