package Implementation.N14499;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int N, M, X, Y, K, R_BOT = 3, C_BOT = 3, R_TOP = 1, C_TOP = 1;
	static int[][] map;
	static int[] val = new int[7],
	cIdx = {2, 1, 5, 6},
	rIdx = {4, 1, 3, 6};
	static StringBuilder result = new StringBuilder();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		Y = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			for (int m = 0; m < M; m++) {
				map[n][m] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());

		for (int k = 0; k < K; k++) {
			roll(Integer.parseInt(st.nextToken()));
		}
		System.out.println(result);
	}
	public static boolean isValid(int n, int m) {
		return n >= 0 && n < N && m >= 0 && m < M;
	}
	public static void roll(int way) {
		int nY = Y, nX = X;
		switch (way) {
			case 1: //EAST
				nX++;
				if (!isValid(nY, nX))
					return;

				R_BOT = setOffset(R_BOT, -1);
				R_TOP = getTopIdx(R_BOT);
				cIdx[C_BOT] = rIdx[R_BOT];
				cIdx[C_TOP] = rIdx[R_TOP];
				break;
			case 2: //WEST
				nX--;
				if (!isValid(nY, nX))
					return;

				R_BOT = setOffset(R_BOT, 1);
				R_TOP = getTopIdx(R_BOT);
				cIdx[C_BOT] = rIdx[R_BOT];
				cIdx[C_TOP] = rIdx[R_TOP];
				break;
			case 3://NORTH
				nY--;
				if (!isValid(nY, nX))
					return;

				C_BOT = setOffset(C_BOT, 1);
				C_TOP = getTopIdx(C_BOT);
				rIdx[R_BOT] = cIdx[C_BOT];
				rIdx[R_TOP] = cIdx[C_TOP];
				break;

			case 4:
				nY++;
				if (!isValid(nY, nX))
					return;

				C_BOT = setOffset(C_BOT, -1);
				C_TOP = getTopIdx(C_BOT);
				rIdx[R_BOT] = cIdx[C_BOT];
				rIdx[R_TOP] = cIdx[C_TOP];
				break;
		}

		if (map[nY][nX] == 0)
			map[nY][nX] = getBotVal();
		else {
			setBotVal(map[nY][nX]);
			map[nY][nX] = 0;
		}

		Y = nY;
		X = nX;
		result.append(getTopVal()).append("\n");
	}
	public static int getTopVal(){
		return val[rIdx[R_TOP]];
	}

	private static int getTopIdx(int def) {
		int topIdx = def;
		for (int i = 0; i < 2; i++) {
			topIdx = setOffset(topIdx, 1);
		}
		return topIdx;
	}

	public static int getBotVal(){
		return val[rIdx[R_BOT]];
	}
	public static void setBotVal(int newVal) {
		val[rIdx[R_BOT]] = newVal;
	}
	public static int setOffset(int def, int offset){
		int before = def + offset;
		if (before < 0)
			return  3;
		else if (before > 3)
			return  0;
		else
			return before;
	}
}
