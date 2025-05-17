package BinarySearch.N7453;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static long RESULT = 0L;
	static long[][] mtx;
	static double[] ab, cd;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		mtx = new long[4][N];
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			mtx[0][n] = Long.parseLong(st.nextToken());
			mtx[1][n] = Long.parseLong(st.nextToken());
			mtx[2][n] = Long.parseLong(st.nextToken());
			mtx[3][n] = Long.parseLong(st.nextToken());
		}
		ab = new double[N * N];
		cd = new double[N * N];
		int idx = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ab[idx] = mtx[0][i] + mtx[1][j];
				cd[idx] = mtx[2][i] + mtx[3][j];
				idx++;
			}
		}
		Arrays.sort(ab);
		Arrays.sort(cd);
		for (double l : ab) {
			double t = l * (-1L);
			double d = t - 0.5;
			int start = Arrays.binarySearch(cd, d);
			start = (start + 1) * (-1);
			if (start >= 0 && start < N * N) {
				if (cd[start] != t) {
					continue;
				}

				int end = Arrays.binarySearch(cd, t + 0.5);
				end = (end + 1) * (-1);
				end = Math.min(end, (N * N));
				if (cd[end - 1] != t) {
					continue;
				}

				RESULT += (long)end - (long)start;
			}
		}
		System.out.println(RESULT);
	}
}
