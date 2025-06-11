package Sort.N1744;

import static java.util.Collections.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, result = 0;
	static boolean hasZero = false;
	static List<Integer> negative, positive;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		negative = new ArrayList<>();
		positive = new ArrayList<>();

		for (int n = 0; n < N; n++) {
			int number = Integer.parseInt(br.readLine());
			if (number < 0) {
				negative.add(number);
			} else if (number > 0) {
				positive.add(number);
			} else {
				hasZero = true;
			}
		}
		positive.sort(reverseOrder());
		sort(negative);
		sum(positive, false);
		sum(negative, true);
		System.out.println(result);
	}

	private static void sum(List<Integer> list, boolean isNegative) {
		int small = 0;
		int big = 0;
		int offset = 0;
		if (list.size() == 1) {
			offset = list.get(0);
			if (hasZero) {
				offset = Math.max(0, offset);
			}
			result += offset;
		} else if (!list.isEmpty()) {
			for (int i = 1; i < list.size(); i += 2) {
				big = list.get(i);
				small = list.get(i - 1);
				offset = Math.max(small * big, small + big);
				result += offset;
				if (i == list.size() - 2) {
					offset = list.get(list.size() - 1);
					if (hasZero) {
						offset = Math.max(0, offset);
					}
					result += offset;
				}
			}
		}
	}
}
