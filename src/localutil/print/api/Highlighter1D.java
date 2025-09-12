package localutil.print.api;

@FunctionalInterface
public interface Highlighter1D {
    /**
     * 주어진 인덱스의 셀을 하이라이트할지 여부를 결정합니다.
     * @param x 셀의 x 인덱스
     * @return 하이라이트가 필요하면 true, 아니면 false
     */
    boolean shouldHighlight(int x);

    /**
     * 하이라이트를 적용하지 않는 기본 Highlighter
     */
    Highlighter1D NONE = x -> false;
}
