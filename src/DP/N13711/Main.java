package DP.N13711;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[] B;
	static List<Integer> LIS = new ArrayList<>();
	static Map<Integer, Integer> index = new HashMap<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		N = parseInt(br.readLine());
		B = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < N; n++) {
			index.put(parseInt(st.nextToken()), n);
		}
		st = new StringTokenizer(br.readLine());
		for (int n = 0; n < N; n++) {
			B[n] = index.get(parseInt(st.nextToken()));
		}

		for (int n = 0; n < N; n++) {
			int i = Collections.binarySearch(LIS, B[n]);
			if (i < 0)
				i = -i - 1;
			if (i == LIS.size())
				LIS.add(B[n]);
			else
				LIS.set(i, B[n]);
		}
		System.out.println(LIS.size());
	}
}
