package Implementation.N2636;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/*
a. 전수조사 전체 방문 확인, 전역 방문변수 초기화 X
	1. 모든 가장자리에서 bfs	시작
	2. 닿은 치즈 다음 bfs 시작값으로 위치 저장 searchQ, deleteQ에 동일하게 저장
		a. 닿은 치즈 세주기, 별도의 변수로 저장
	3. 모든 치즈 전수조사 bfs 시작
		a. 추가로 발견된 치즈, 별도의 변수로 저장, 모든 치즈 합 저장
		b. 추가로 발견되 치즈가 없을 경우, 닿은 치즈 StringBuilder 에 모든 치즈 합을 추가 Time++ 출력 하고 종료
	5. deleteQ로 cheese[][] 지우기 해당위치 방문처리 제거(다음 시간 재방문)
	6. Time++
 */
public class Main {
	static int Y, X, TOTAL_CHEESE, TANGO_CHEESE, TIME, Q_IDX = 0;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static boolean[][] visited, isCheese;
	static Queue<Node>[] searchQ;
	static StringBuilder result = new StringBuilder();
	static int[] mv = {-1, 0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		Y = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		visited = new boolean[Y][X];
		isCheese = new boolean[Y][X];
		searchQ = new ArrayDeque[2];
		for (int i = 0; i < 2; i++) {
			searchQ[i] = new ArrayDeque<>();
		}
		for (int y = 0; y < Y; y++) {
			searchQ[Q_IDX].offer(new Node(y, 0));
			searchQ[Q_IDX].offer(new Node(y, X - 1));
			visited[y][0] = true;
			visited[y][X - 1] = true;
			st = new StringTokenizer(br.readLine());
			for (int x = 0; x < X; x++) {
				if (Integer.parseInt(st.nextToken()) == 1) {
					isCheese[y][x] = true;
					TOTAL_CHEESE++;
				}
			}
		}
		for (int x = 0; x < X; x++) {
			searchQ[Q_IDX].offer(new Node(0, x));
			searchQ[Q_IDX].offer(new Node(Y - 1, x));
			visited[0][x] = true;
			visited[Y - 1][x] = true;
		}
		while (TOTAL_CHEESE > 0) {
			int nextQIdx = Q_IDX ^ 1;
			while (!searchQ[Q_IDX].isEmpty()) {
				Node poll = searchQ[Q_IDX].poll();

				for (int i = 0; i < 4; i++) {
					int y = poll.y + mv[i];
					int x = poll.x + mv[i + 1];

					if (valid(y, x) && !visited[y][x]) {
						if (isCheese[y][x]) {
							TANGO_CHEESE++;
							searchQ[nextQIdx].offer(new Node(y, x));
							isCheese[y][x] = false;
						} else {
							searchQ[Q_IDX].offer(new Node(y, x));
						}
						visited[y][x] = true;
					}
				}
			}
			if (TANGO_CHEESE == TOTAL_CHEESE) {
				result.append(TIME + 1).append("\n").append(TOTAL_CHEESE);
				System.out.println(result);
				return;
			}
			TIME++;
			TOTAL_CHEESE -= TANGO_CHEESE;
			TANGO_CHEESE = 0;
			Q_IDX = nextQIdx;
		}
		result.append(TIME).append("\n").append(TANGO_CHEESE);
		System.out.println(result);
	}

	static boolean valid(int y, int x) {
		return y > 0 && y < Y - 1 && x > 0 && x < X - 1;
	}
}

class Node {
	int y, x;

	public Node(int y, int x) {
		this.y = y;
		this.x = x;
	}
}