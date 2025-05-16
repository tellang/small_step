package BinarySearch.N2473;

/*
int 끼리 덧셈하면 long 으로 받아도 int 연산후 들어간다
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N, LOW = 0, HIGH, MID = 1;
	static long SUM = Long.MAX_VALUE;
	static long[] arr;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		arr = new long[N];
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < N; n++) {
			arr[n] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		int low = 0, mid = 1, high = N - 1;
		HIGH = high;
		for (;low < N-1; low++) {
			mid = low+1;
			high = N-1;
			while (mid < high) {
				long sum = arr[low] + arr[mid] + arr[high];
				if (Math.abs(sum) < Math.abs(SUM)) {
					SUM = sum;
					LOW = low;
					MID = mid;
					HIGH = high;
				}
				if (sum > 0) {
					high--;
				} else if (sum < 0) {
					mid++;
				} else
					break;
			}
			if (SUM == 0)
				break;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(arr[LOW]).append(' ').append(arr[MID]).append(' ').append(arr[HIGH]);
		System.out.println(sb);
	}
}
