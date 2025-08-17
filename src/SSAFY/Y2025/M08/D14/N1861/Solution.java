package SSAFY.Y2025.M08.D14.N1861;
/**
 * 1-based
 * 중복 없음
 * 증가로 탐색, 1씩 증가
 * N = 1K
 * 수 = 1M
 * ---
 * 어짜피 그리디 함이 보장된다
 * 그냥 1부터 탐색을 하고 거기넘어부터 탐색하자
 * 1M 갯수의 int 2개 8Mb
 */

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder result = new StringBuilder();

	static int T, N, N2, localNum, START_NODE_IDX, MAX_COUNT;
	static Node[] nodes = new Node[1000 * 1000 + 1]; // 방 번호를 인덱스로 사용하여 방의 좌표를 저장하는 배열
	static Node preNode;
	static int[] mv = {-1, 0, 1, 0, -1}; // 상우하좌 이동을 위한 배열

	/**
	 * 배열 초기화 안하고 덮어쓰는 방식 사용
	 * 어짜피 인덱스 증가형식으로 탐색 이전 방 확인 불필요
	 * 늘 roomIdx는 최고값
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("src/SSAFY.Y2025.M08.D14.N1861/input"));
		br = new BufferedReader(new InputStreamReader(System.in));

		T = parseInt(br.readLine().trim());

		for (int tc = 1; tc <= T; tc++) {
			result.append('#').append(tc).append(' ');
			N = parseInt(br.readLine().trim());
			N2 = N * N;
			MAX_COUNT = 0;
			START_NODE_IDX = 1;
			for (int y = 1; y <= N; y++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int x = 1; x <= N; x++) {
					localNum = parseInt(st.nextToken());
					nodes[localNum] = new Node(y, x);
				}
			}
			preNode = nodes[1];
			int localCount = 1;
			int localStartIdx = 1;
			for (int n = 2; n <= N2; n++) {
				if (isNextRoom(n)) {
					localCount++;
				} else {
					if (localCount > MAX_COUNT) {
						MAX_COUNT = localCount;
						START_NODE_IDX = localStartIdx;
					}
					localCount = 1;
					localStartIdx = n;
				}
				preNode = nodes[n];
			}
			if (localCount > MAX_COUNT) {
				MAX_COUNT = localCount;
				START_NODE_IDX = localStartIdx;
			}
			result.append(START_NODE_IDX).append(' ').append(MAX_COUNT).append('\n');
		}
		System.out.println(result);
	}

	/**
	 * 현재 방에서 다음 방으로 이동할 수 있는지 확인
	 * @param roomIdx 다음 방 번호
	 * @return 이동 가능 여부
	 */
	static boolean isNextRoom(int roomIdx) {
		Node postNode = nodes[roomIdx];
		for (int i = 0; i < 4; i++) {
			int nx = preNode.x + mv[i];
			int ny = preNode.y + mv[i + 1];
			if (ny == postNode.y && nx == postNode.x) {
				return true;
			}
		}
		return false;
	}

	static class Node {
		int y, x;

		public Node(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
}