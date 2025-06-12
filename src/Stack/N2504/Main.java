package Stack.N2504;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static char[] arr;
	static int RESULT;
	static ArrayDeque<Integer> stack = new ArrayDeque<>();

	public static void main(String[] args) throws IOException {
		arr = br.readLine().toCharArray();
		for (char next : arr) {
			if (next == '(' || next == '[')
				stack.push((next * (-1)));
			else if (next == ')' || next == ']') {
				if (isBadPair(stack.peek(), next)) {
					System.out.println(0);
					return;
				} else if (stack.peek() > 0) {
					int num = 0, multi = 1;
					while (!stack.isEmpty() && stack.peek() > 0) {
						num += stack.pop();
					}

					if (isBadPair(stack.peek(), next)) {
						System.out.println(0);
						return;
					} else {
						char popped = (char)(stack.pop() * (-1));//left syn pop
						if (popped == '[')
							multi = 3;
						else if (popped == '(')
							multi = 2;
						stack.push(num * multi);
					}

				} else { //good pair
					stack.pop();
					if (next == ']')
						stack.push(3);
					else
						stack.push(2);
				}
			}
		}
		while (!stack.isEmpty()) {
			int popped = stack.pop();
			if (popped < 0) {
				RESULT = 0;
				break;
			}
			RESULT += popped;
		}
		System.out.println(RESULT);
	}

	public static boolean isBadPair(Integer left, char right) {
		if (left == null)
			return true;
		left *= -1;
		return isBadPair((char)left.intValue(), right);
	}

	public static boolean isBadPair(char left, char right) {
		boolean rs = left == '(' && right == ']',
			sr = left == '[' && right == ')';
		return rs || sr;
	}

}
