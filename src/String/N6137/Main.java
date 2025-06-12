package String.N6137;
/*
쵀대 1M <
그리디 하다
우선순위가 같은경우 다음 안쪽걸 비교한다
넣고 80개면 개행 추가

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static char[] arr;
	static int N, H, T, LOCAL_LEN;
	static StringBuilder result = new StringBuilder();

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		arr = new char[N];
		H = 0;
		T = N - 1;
		for (int n = 0; n < N; n++) {
			arr[n] = br.readLine().charAt(0);
		}
		while (H < T) {
			if (arr[H] > arr[T]) {
				result.append(arr[T--]);
			} else if (arr[H] < arr[T]) {
				result.append(arr[H++]);
			} else {
				int localH = H + 1;
				int localT = T - 1;
				boolean isTail = false;
				while (localT > localH) {
					if (arr[localH] > arr[localT]) {
						isTail = true;
						break;
					} else if (arr[localH] < arr[localT]) {
						isTail = false;
						break;
					} else {
						localH++;
						localT--;
					}
				}
				if (isTail) {
					result.append(arr[T--]);
				} else {
					result.append(arr[H++]);
				}
			}
			LOCAL_LEN++;
			if (LOCAL_LEN == 80) {
				LOCAL_LEN = 0;
				result.append('\n');
			}
		}
		result.append(arr[T]);
		System.out.println(result);
	}
}