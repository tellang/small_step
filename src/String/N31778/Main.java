package String.N31778;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
최대로 C를 오른쯕으로 밀고
0부터 탐색해 가며
arr[i] == P
	arr[i] = arr[i-1] + 1
arr[i] == C
	arr[i] = arr[i-1]
	result += arr[i] C 2

 */
public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int N, K;
	static long RESULT;
	static char[] arr;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new char[N];
		arr = br.readLine().toCharArray();
		int head = 0;
		int tail = N - 1;
		while (K > 0 && (head < tail) && head < N && tail >= 0) {
			while (tail >= 0 && arr[tail] == 'C')
				tail--;
			while (head < N && arr[head] == 'P')
				head++;
			if (head < tail && head < N && tail >= 0) {
				arr[head] = 'P';
				arr[tail] = 'C';
				head++;
				tail--;
				K--;
			}
		}

		long last = arr[0] == 'P' ? 1 : 0;
		long next = 0;
		int n = 1;
		for (; n < Math.min(2, N); n++) {
			next = last;
			if (arr[n] == 'P')
				next++;
		}
		for (; n < N; n++) {
			next = last;
			if (arr[n] == 'P')
				next++;
			else
				RESULT += nC2(next);
		}
		System.out.println(RESULT);
	}

	private static long nC2(long n) {
		return (n * (n - 1)) >> 1;
	}

}
