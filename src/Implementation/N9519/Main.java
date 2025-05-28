package Implementation.N9519;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/*
뒷부분 절반이 앞부분과 섞임
홀수는 뒷부분이 짧다 n/2 = 뒷길이,
	ex) 5/2 = 2, 3개 2개, 0, 1, 2 | 3, 4
	6/2 = 3, 0, 1, 2 | 3, 4, 5
	n/2 + 1 = 뒷 배열 시작 인덱스
	a, f, b | e, c, d

	 f   e
	a, b, c | d, e, f
	a, f, b | c, d, e
	a, f, b | e, c, d
블링크 로직
동적으로 위치가 바뀌지 않는다

(base.size()-1)/2 = insertCount
int ic = 0
for i = ~ base.size()-1
	if(i%2 ==1 && ic < insertCount)
		ic++;
		stk.push(base.get(i))
	else
		buffer.add(base.get(i))

while(!stk.isEmpty())
	buffer.add(stk.pop())

size = 3 사이클 2
a b c
a c b

a b c

a b c d 사이클 3
a d b c
a c d b

a b c d

a b c d e 사이클 3 2삽
a e b d c
a c e d b

a b c d e

a b c d e f 사이클 5 2삽
a f b e c d
a d f c b e
a e d b f c
a c e f d b

a b c d e f

a b c d e f g 사이클 6 3삽
a g b f c e d
a d g e b c f
a f d c g b e
a e f b d g c
a c e g f d b

a b c d e f g h 사이클 4
a h b g c f d e
a e h d b f g c
a c e g h f d b

a b c d e f g h

규칙이 없네~
 */
public class Main {
	static int N, IC, CYCLE;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static List<Character> base, buffer, defaultArr;
	static List<List<Character>> results = new ArrayList<>();
	static ArrayDeque<Character> stack = new ArrayDeque<>();

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		base = new ArrayList<>();
		defaultArr = new ArrayList<>();
		for (char c : br.readLine().toCharArray()) {
			base.add(c);
			defaultArr.add(c);
		}
		results.add(defaultArr);
		int lastIdx = base.size() - 1;
		IC = lastIdx / 2;
		for (int n = 0; n < N; n++) {
			buffer = new ArrayList<>();
			int localIc = 0;
			stack.clear();
			for (int i = 0; i < base.size(); i++) {
				if (i % 2 == 1 && localIc < IC) {
					localIc++;
					stack.push(base.get(i));
				} else
					buffer.add(base.get(i));
			}

			while (!stack.isEmpty()) {
				buffer.add(stack.pop());
			}
			boolean found = true;
			List<Character> temp = new ArrayList<>();
			for (int i = 0; i < buffer.size(); i++) {
				if (buffer.get(i) != defaultArr.get(i)) {
					found = false;
				}
				temp.add(buffer.get(i));
			}

			if (found) {
				CYCLE = n + 1;
				StringBuilder sb = new StringBuilder();
				for (char c : results.get((N % CYCLE))) {
					sb.append(c);
				}
				System.out.println(sb);
				return;
			}
			results.add(temp);
			base = buffer;

		}

		StringBuilder sb = new StringBuilder();
		for (Character c : base) {
			sb.append(c);
		}
		System.out.println(sb);
	}
}
