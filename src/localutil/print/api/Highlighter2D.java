package localutil.print.api;

@FunctionalInterface
public interface Highlighter2D {
    /**
     * 주어진 좌표의 셀을 하이라이트할지 여부를 결정합니다.
     * @param y 셀의 y 좌표
     * @param x 셀의 x 좌표
     * @return 하이라이트가 필요하면 true, 아니면 false
     */
    boolean shouldHighlight(int y, int x);

    /**
     * 하이라이트를 적용하지 않는 기본 Highlighter
     */
    Highlighter2D NONE = (y, x) -> false;
}
