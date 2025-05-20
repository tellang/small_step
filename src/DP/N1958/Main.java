package DP.N1958;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static char[] A, B, C;
	static int AS, BS, CS, MAX;
	static int[][][] dpABC;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		A = br.readLine().toCharArray();
		B = br.readLine().toCharArray();
		C = br.readLine().toCharArray();
		AS = A.length;
		BS = B.length;
		CS = C.length;
		dpABC = new int[AS + 1][BS + 1][CS + 1];
		for (int a = 1; a <= AS; a++) {
			for (int b = 1; b <= BS; b++) {
				for (int c = 1; c <= CS; c++) {
					if ((A[a - 1] == C[c - 1]) && (B[b - 1] == C[c - 1])) {
						dpABC[a][b][c] = dpABC[a - 1][b - 1][c - 1] + 1;
						MAX = Math.max(dpABC[a][b][c], MAX);
					} else {
						dpABC[a][b][c] = Math.max(Math.max(
								dpABC[a - 1][b][c],
								dpABC[a][b - 1][c]),
							dpABC[a][b][c - 1]);
					}
				}
			}
		}
		System.out.println(MAX);
	}
}
