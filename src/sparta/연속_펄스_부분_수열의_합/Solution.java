package sparta.연속_펄스_부분_수열의_합;

import static java.lang.Math.*;

class Solution {
	public static void main(String[] args) {
		Solution solution = new Solution();
		System.out.println(solution.solution(new int[] {2, 3, -6, 1, 3, -1, 2, 4}));
	}

	public long solution(int[] sequence) {
		long MAX = 0;
		long[] pSum = new long[sequence.length];
		long[] mSum = new long[sequence.length];
		long ps = 1, ms = -1, invert = -1;
		pSum[0] = max(0, -sequence[0]);
		mSum[0] = max(0, sequence[0]);
		MAX = max(pSum[0], mSum[0]);
		for (int i = 1; i < sequence.length; i++) {
			long nextPSum = max(0, pSum[i - 1] + sequence[i] * ps);
			long nextMSum = max(0, mSum[i - 1] + sequence[i] * ms);

			pSum[i] = nextPSum;
			mSum[i] = nextMSum;

			MAX = max(max(MAX, pSum[i]), mSum[i]);

			ps *= invert;
			ms *= invert;
		}
		return MAX;
	}
}