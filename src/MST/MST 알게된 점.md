# MST

* author : tellang
* date : 24. 12. 10.

---

## Union 클래스 만드는 형식으로 트리 합치기

- rank 와 parent를 사용
- rank의 크기를 비교하여 큰쪽으로 합쳐줌
    - 합치는 과정은 parent를 rank 높은쪽으로 바꿔주는 형식
    - rank가 같은경우 한쪽을 정해서 rank를 올리고 그쪽으로 parent로 몰아주기