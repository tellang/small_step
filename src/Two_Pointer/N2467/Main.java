package Two_Pointer.N2467;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, LOW, HIGH, L, H, SUM;
	static int[] arr;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < N; n++) {
			arr[n] = Integer.parseInt(st.nextToken());
		}
		L = 0;
		H = N - 1;
		LOW = arr[L];
		HIGH = arr[H];
		SUM = LOW + HIGH;
		int low = LOW, high = HIGH, sum = SUM;
		while (L < H) {
			if (sum == 0)
				break;
			else if (sum < 0) {
				L++;
				low = arr[L];
			} else {
				H--;
				high = arr[H];
			}
			if (L == H)
				break;
			sum = low + high;
			if (Math.abs(SUM) > Math.abs(sum)) {
				LOW = low;
				HIGH = high;
				SUM = sum;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(LOW).append(' ').append(HIGH);
		System.out.println(sb);
	}
}
