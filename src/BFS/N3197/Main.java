package BFS.N3197;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/*
30M
2.25M + bfs <
1. 백조 연결
백조 visited 구성
L 에서 다른 L 찾기
	a. swanQ[a] 에서 bfs 시작
		i. 맨 처음은 L 위치
	b. bfs 도중 얼음을 만나면 swanQ[b] 에 추가
	c. a <-> b 토글 0 xor 1
2. 얼음 깨기
얼음 visited 구성
	a. 물이 있는 waterQ 에서 bfs
	b. 닿은 얼음 meltQ 에 추가
	c. meltQ 처리 하면서 다시 waterQ에 넣기

 */
public class Main {
	static int R, C, SWAN_Q_IDX = 0, DAY = 0, REMAIN_ICE;
	static boolean[][] waterVisited, swanVisited, isIce;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static Node[] swanNodes = new Node[2];
	static ArrayDeque<Node> waterQ = new ArrayDeque<>(),
		meltQ = new ArrayDeque<>();
	static ArrayDeque<Node>[] swanQ = new ArrayDeque[2];
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		waterVisited = new boolean[R][C];
		swanVisited = new boolean[R][C];
		isIce = new boolean[R][C];
		swanQ[0] = new ArrayDeque<>();
		swanQ[1] = new ArrayDeque<>();
		int swanIdx = 0;
		for (int r = 0; r < R; r++) {
			char[] arr = br.readLine().toCharArray();
			for (int c = 0; c < C; c++) {
				char n = arr[c];
				if (n == 'L') {
					Node node = new Node(r, c);
					swanNodes[swanIdx++] = node;
					waterQ.offer(node);
					waterVisited[r][c] = true;
				} else if (n == '.') {
					waterQ.offer(new Node(r, c));
					waterVisited[r][c] = true;
				} else {
					isIce[r][c] = true;
					REMAIN_ICE++;
				}
			}
		}
		Node startSwan = swanNodes[0];
		swanQ[SWAN_Q_IDX].offer(startSwan);
		swanVisited[startSwan.r][startSwan.c] = true;

		while (REMAIN_ICE > 0) {
			int nextSwanQIdx = SWAN_Q_IDX ^ 1;
			while (!swanQ[SWAN_Q_IDX].isEmpty()) {
				Node poll = swanQ[SWAN_Q_IDX].poll();
				if (findSwan(poll)) {
					System.out.println(DAY);
					System.exit(0);
				}

				for (int i = 0; i < 4; i++) {
					int nr = poll.r + dr[i], nc = poll.c + dc[i];
					if (isValid(nr, nc) && !swanVisited[nr][nc]) {
						Node newNode = new Node(nr, nc);
						swanVisited[nr][nc] = true;
						if (isIce[nr][nc]) {
							swanQ[nextSwanQIdx].offer(newNode);
						} else {
							swanQ[SWAN_Q_IDX].offer(newNode);
						}
					}
				}
			}
			SWAN_Q_IDX = nextSwanQIdx;

			while (!waterQ.isEmpty()) {
				Node poll = waterQ.poll();

				for (int i = 0; i < 4; i++) {
					int nr = poll.r + dr[i], nc = poll.c + dc[i];
					if (isValid(nr, nc) && !waterVisited[nr][nc]) {
						Node newNode = new Node(nr, nc);
						waterVisited[nr][nc] = true;
						if (isIce[nr][nc]) {
							meltQ.offer(newNode);
						} else {
							waterQ.offer(newNode);
						}
					}
				}
			}

			REMAIN_ICE -= meltQ.size();
			while (!meltQ.isEmpty()) {
				Node poll = meltQ.poll();
				waterQ.offer(poll);
				isIce[poll.r][poll.c] = false;
			}

			DAY++;
		}
		System.out.println(DAY);
	}

	public static boolean findSwan(Node poll) {
		Node finishSwan = swanNodes[1];
		return finishSwan.r == poll.r && finishSwan.c == poll.c;
	}

	static boolean isValid(int r, int c) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}
}

class Node {
	int r, c;

	public Node(int r, int c) {
		this.r = r;
		this.c = c;
	}
}
