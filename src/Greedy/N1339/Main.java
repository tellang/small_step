package Greedy.N1339;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static Map<Character, Integer> map = new HashMap<>();
	static List<Arr> list = new ArrayList<>();
	static List<Pair> pairs = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int MUL = 9;
		int result = 0;
		for (char i = 'A'; i <= 'Z'; i++) {
			pairs.add(new Pair(i, 0));
		}
		for (int n = 0; n < N; n++) {
			list.add(new Arr(br.readLine().toCharArray()));
		}
		for (Arr n : list) {
			for (int i = 0; i < n.l; i++) {
				int c = n.arr[i] - 'A';
				int v = pairs.get(c).v;
				pairs.get(c).v += (int)Math.pow(10, n.l - i - 1);
			}
		}
		pairs.sort((o1, o2) -> o2.v - o1.v);
		for (Pair pair : pairs) {
			result += pair.v * MUL;
			MUL--;
		}
		System.out.println(result);
	}
}

class Arr {
	int l;
	char[] arr;

	public Arr(char[] arr) {
		this.l = arr.length;
		this.arr = arr;
	}
}

class Pair {
	char c;
	int v;

	public Pair(char c, int v) {
		this.c = c;
		this.v = v;
	}
}