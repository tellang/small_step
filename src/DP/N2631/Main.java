package DP.N2631;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	static int N;
	static int[] arr;
	static List<Integer> lis = new ArrayList<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());
		arr = new int[N];
		for (int n = 0; n < N; n++) {
			arr[n] = parseInt(br.readLine());
		}
		for (int n : arr) {
			int i = Collections.binarySearch(lis, n);
			if (i < 0)
				i = -i - 1;
			if (i == lis.size())
				lis.add(n);
			else
				lis.set(i, n);
		}
		System.out.println(N - lis.size());
	}
}
