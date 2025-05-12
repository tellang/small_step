package Implementation.N17144;
/*
직접 이동시
50*6*K = 300K

직접 확산시 50*50*5*K = 12500K
도합 1.5M
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int R, C, T, TOTAL_DUST = 0;
	static int[][] map;
	static int[] mv = {-1, 0, 1, 0, -1};
	static List<Integer> cleanerRPos = new ArrayList<>();
	static Queue<Node> sQ = new ArrayDeque<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		map = new int[R][C];

		for (int r = 0; r < R; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < C; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				if (map[r][c] == -1) {
					cleanerRPos.add(r);
				}
				if (map[r][c] > 0) {
					TOTAL_DUST += map[r][c];
				}
			}
		}

		for (int t = 0; t < T; t++) {
			for (int r = 0; r < R; r++) {
				for (int c = 0; c < C; c++) {
					if (map[r][c] < 5)
						continue;
					int count = 0;
					int nV = Math.floorDiv(map[r][c], 5);
					for (int i = 0; i < 4; i++) {
						int nR = r + mv[i];
						int nC = c + mv[i + 1];
						if (isValid(nR, nC) &&
							(map[nR][nC] != -1)
						){
							count++;
							sQ.offer(new Node(nR, nC, nV));
						}
					}
					map[r][c] -= (nV * count);
				}
			}
			while (!sQ.isEmpty()) {
				Node node = sQ.poll();
				map[node.r][node.c] += node.v;
			}
			dustFlow();
		}
		System.out.println(TOTAL_DUST);
	}

	static boolean isValid(int r, int c) {
		return c >= 0 && r >= 0 && r < R && c < C;
	}

	static void dustFlow() {
		int sR = cleanerRPos.get(0) - 1;
		int sC;

		if (sR >= 0) {
			TOTAL_DUST -= map[sR][0];
			map[sR--][0] = 0;
		}
		while (sR >= 0) {
			map[sR + 1][0] += map[sR][0];
			map[sR][0] = 0;
			sR--;
		}

		sC = 1;
		while (sC < C) {
			map[0][sC - 1] += map[0][sC];
			map[0][sC] = 0;
			sC++;
		}

		sR = 1;
		while (sR <= cleanerRPos.get(0)) {
			map[sR - 1][C - 1] += map[sR][C - 1];
			map[sR][C - 1] = 0;
			sR++;
		}

		sC = C - 2;
		while (sC > 0){
			map[cleanerRPos.get(0)][sC + 1] += map[cleanerRPos.get(0)][sC];
			map[cleanerRPos.get(0)][sC] = 0;
			sC--;
		}

		sR = cleanerRPos.get(1) + 1;
		if(sR < R) {
			TOTAL_DUST -= map[sR][0];
			map[sR++][0] = 0;
		}
		while (sR < R) {
			map[sR - 1][0] += map[sR][0];
			map[sR][0] = 0;
			sR++;
		}

		sC = 1;
		while (sC < C) {
			map[R-1][sC - 1] += map[R-1][sC];
			map[R-1][sC] = 0;
			sC++;
		}

		sR = R-2;
		while (sR >= cleanerRPos.get(1)) {
			map[sR + 1][C - 1] += map[sR][C-1];
			map[sR][C-1] = 0;
			sR--;
		}

		sC = C - 2;
		while (sC > 0) {
			map[cleanerRPos.get(1)][sC + 1] += map[cleanerRPos.get(1)][sC];
			map[cleanerRPos.get(1)][sC] = 0;
			sC--;
		}
	}
}

class Node {
	int r, c, v;

	public Node(int r, int c, int v) {
		this.r = r;
		this.c = c;
		this.v = v;
	}
}
