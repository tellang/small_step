package Two_Pointer.N1253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N, RESULT = 0;
	static int[] arr;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		arr = new int[N];
		for (int n = 0; n < N; n++) {
			arr[n] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		for (int t = 0; t < N; t++) {
			int s = 0;
			int b = N - 1;
			while (s < b) {
				if (b == t) {
					b--;
					continue;
				}
				if (s == t) {
					s++;
					continue;
				}
				int p = arr[s] + arr[b];
				if (p == arr[t]) {
					RESULT++;
					break;
				} else if (p > arr[t]) {
					b--;
				} else {
					s++;
				}

			}
		}
		System.out.println(RESULT);
	}

}
