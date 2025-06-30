package sparta.연속된_부분의_합;

import java.util.Arrays;

class Solution {
	static int[] sum;

	public int[] solution(int[] sequence, int k) {
		int maxSize = sequence.length;
		int a = -1, b = -1, MIN = Integer.MAX_VALUE;
		sum = new int[maxSize];
		sum[0] = sequence[0];
		if (sequence[0] == k) {
			return new int[] {0, 0};
		}
		for (int i = 1; i < maxSize; i++) {
			sum[i] = sum[i - 1] + sequence[i];
			if (sum[i] == k) {
				a = 0;
				b = i;
				MIN = i + 1;
			}
			if (sequence[i] == k) {
				return new int[] {i, i};
			}
		}
		for (int i = 0; i < maxSize; i++) {
			int tango = sum[i] - k;
			int tangoIdx = Arrays.binarySearch(sum, 0, i, tango);
			if (tangoIdx >= 0) {
				int localMin = i - tangoIdx;
				if (localMin < MIN) {
					MIN = localMin;
					a = tangoIdx + 1;
					b = i;
				}
			}
		}
		return new int[] {a, b};
	}
}
