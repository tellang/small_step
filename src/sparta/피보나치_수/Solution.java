package sparta.피보나치_수;

//n = 0.1M

class Solution {
	static long[][] DEFAULT_MTX = new long[][] {
		{0, 1},
		{1, 1}
	};
	static int DIV = 1234567;

	public int solution(int n) {
		int answer = 0;
		answer = (int)(fibo(n)[0][1] % DIV);
		return answer;
	}

	private long[][] fibo(int n) {
		if (n <= 1) {
			return DEFAULT_MTX;
		}
		long[][] mtx = fibo(n / 2);
		if (n % 2 == 0) {
			return multiMtx(mtx, mtx);
		} else {
			return multiMtx(multiMtx(mtx, mtx), DEFAULT_MTX);
		}
	}

	private long[][] multiMtx(long[][] mtx0, long[][] mtx1) {
		long a, b, c, d;
		a = (mtx0[0][0] * mtx1[0][0]) % DIV + (mtx0[0][1] * mtx1[1][0]) % DIV;
		b = (mtx0[0][0] * mtx1[0][1]) % DIV + (mtx0[0][1] * mtx1[1][1]) % DIV;
		c = (mtx0[1][0] * mtx1[0][0]) % DIV + (mtx0[1][1] * mtx1[1][0]) % DIV;
		d = (mtx0[1][0] * mtx1[0][1]) % DIV + (mtx0[1][1] * mtx1[1][1]) % DIV;

		return new long[][] {
			{a, b},
			{c, d}
		};
	}
}