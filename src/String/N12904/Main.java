package String.N12904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static String S;
	static char sTail;
	static StringBuilder T;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		S = br.readLine();
		sTail = S.charAt(S.length() - 1);
		T = new StringBuilder(br.readLine());
		while (T.length() > S.length()) {
			char tTail = T.charAt(T.length() - 1);
			T.deleteCharAt(T.length() - 1);
			if (tTail == 'B') {
				T.reverse();
			}
		}
		System.out.println(T.toString().equals(S) ? 1 : 0);
	}
}
