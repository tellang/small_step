package DP.N2342;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int[][][] steps; // idx, L, R
	static int L = 0, R = 1, MAX = 8*100_000;
	static List<Integer> chaebo = new ArrayList<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		int n;
		chaebo.add(0);
		do {
			n = parseInt(st.nextToken());
			chaebo.add(n);
		} while (n != 0);
		steps = new int[chaebo.size() -1][5][5];
		for (int i = 0; i < chaebo.size()-1; i++) {
			for (int j = 0; j < 5; j++) {
				Arrays.fill(steps[i][j], MAX);
			}
		}
		steps[0][0][0] = 0;
		int i = 1;
		for (; i < chaebo.size()-1; i++) {
			int note = chaebo.get(i);
			for (int j = 0; j < 5; j++) {
				for (int k = 0; k < 5; k++) {
					if (j != 0 && j == k)
						continue;
					steps[i][j][note] = Math.min(steps[i][j][note], (steps[i-1][j][k] + getCost(k, note)));
					steps[i][note][k] = Math.min(steps[i][note][k], (steps[i-1][j][k] + getCost(j, note)));
				}
			}

		}
		int min = Integer.MAX_VALUE;
		for (int[] ints : steps[i - 1]) {
			for (int num : ints) {
				min = Math.min(num, min);
			}
		}
		System.out.println(min);
	}

	public static int getCost(int before, int after) {
		if (before == 0)
			return 2;
		if (before == after)
			return 1;
		int range = Math.abs(before - after);
		if (range == 2)
			return 4;
		return 3;
	}
}
