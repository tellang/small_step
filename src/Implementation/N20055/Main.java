package Implementation.N20055;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, K, START, END, TURN = 1, NN;
	static int[] stamina;
	static boolean[] isRobot;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		NN = N * 2;
		stamina = new int[NN];
		isRobot = new boolean[NN];
		START = 0;
		END = N - 1;
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < NN; n++) {
			stamina[n] = Integer.parseInt(st.nextToken());
		}

		while (K > 0) {
			START = back(START);
			END = back(END);
			isRobot[END] = false;
			for (int i = back(END); i != START; i = back(i)) {
				int next = next(i);
				if (isRobot[i] &&
					!isRobot[next] &&
					stamina[next] > 0) {
					stamina[next]--;
					isRobot[next] = true;
					isRobot[i] = false;
					if (stamina[next] == 0) {
						K--;
						if (K <= 0) {
							System.out.println(TURN);
							return;
						}
					}
				}
			}
			isRobot[END] = false;
			if (stamina[START] > 0) {
				stamina[START]--;
				isRobot[START] = true;
				if (stamina[START] == 0) {
					K--;
					if (K <= 0) {
						System.out.println(TURN);
						return;
					}
				}
			}
			TURN++;
		}

		System.out.println(TURN);
	}

	static int next(int i) {
		return (i + 1) % NN;
	}

	static int back(int i) {
		return next(i + NN - 2);
	}
}
