package Greedy.N1036;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;

/*
알파벳별로 차수의 누적값을 저장
Z일 경우 스킵
 */
public class Main {
	static final BigInteger THIRTY_SIX = BigInteger.valueOf(36);
	static final BigInteger THIRTY_FIVE = BigInteger.valueOf(35);
	static final BigInteger BIG_ZERO = BigInteger.ZERO;
	static final BigInteger BIG_Z_VALUE = BigInteger.valueOf(char2Int('Z'));
	static int N, K, MAX_SIZE = 0;
	static BigInteger RESULT = BigInteger.ZERO;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Pair[] pairs = new Pair[36];

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		for (int i = 0; i < 36; i++) {
			char c = decimalTo36Char(i);
			pairs[i] = new Pair(c, BIG_ZERO);
		}
		for (int n = 0; n < N; n++) {
			char[] num = br.readLine().toCharArray();
			int size = num.length - 1;
			MAX_SIZE = Math.max(MAX_SIZE, size);
			for (int i = 0; i <= size; i++) {
				int digit = size - i;
				char key = num[i];
				pairs[char2Int(key)].totalDigit = pairs[char2Int(key)].totalDigit.add(THIRTY_SIX.pow(digit));
			}
		}
		Arrays.sort(pairs, ((o1, o2) -> {
			BigInteger left = o2.totalDigit.multiply(THIRTY_FIVE.subtract(char2BigInt(o2.c)));
			BigInteger right = o1.totalDigit.multiply(THIRTY_FIVE.subtract(char2BigInt(o1.c)));
			BigInteger r = left.subtract(right);
			if (r.equals(BIG_ZERO)) {
				return (o2.totalDigit.subtract(o1.totalDigit)).compareTo(BIG_ZERO);
			}
			return r.compareTo(BIG_ZERO);
		}));
		K = Integer.parseInt(br.readLine());

		for (Pair pair : pairs) {
			if (pair.totalDigit.equals(BIG_ZERO))
				break;
			else if (pair.c == 'Z') {
				RESULT = RESULT.add(pair.totalDigit.multiply(BIG_Z_VALUE));
			} else if (K > 0) {
				K--;
				RESULT = RESULT.add(pair.totalDigit.multiply(BIG_Z_VALUE));
			} else
				RESULT = RESULT.add(pair.totalDigit.multiply(char2BigInt(pair.c)));
		}
		System.out.println(decimalTo36(RESULT));
	}

	public static String decimalTo36(BigInteger decimal) {
		StringBuilder sb = new StringBuilder();
		BigInteger scale = THIRTY_SIX;
		int top = 0;
		BigInteger up = BIG_ZERO;

		while (decimal.compareTo(up) >= 0) {
			up = scale.pow(top);
			top++;
		}
		top -= 2;

		while (top >= 0) {
			up = scale.pow(top);
			BigInteger[] divMod = decimal.divideAndRemainder(up);
			int decimalPart = divMod[0].intValue();
			char part36 = decimalTo36Char(decimalPart);
			sb.append(part36);
			decimal = divMod[1];
			top--;
		}

		if (sb.length() == 0)
			sb.append('0');

		return sb.toString();
	}

	private static char decimalTo36Char(int decimalPart) {
		char part36;
		if (decimalPart >= 10) {
			part36 = (char)('A' + decimalPart - 10);
		} else {
			part36 = (char)('0' + decimalPart);
		}
		return part36;
	}

	public static int char2Int(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		} else if (c >= 'a' && c <= 'z') {
			return c - 'a' + 10;
		} else if (c >= 'A' && c <= 'Z') {
			return c - 'A' + 10;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static BigInteger char2BigInt(char c) {
		return BigInteger.valueOf(char2Int(c));
	}
}

class Pair {
	char c;
	BigInteger totalDigit;

	public Pair(char c, BigInteger totalDigit) {
		this.c = c;
		this.totalDigit = totalDigit;
	}
}
