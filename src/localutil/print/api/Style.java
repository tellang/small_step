package localutil.print.api;

/**
 * 라벨 규약 (X, Y, V)
 */
public interface Style {
    /**
     * 1D/2D 공용 X 라벨(예: "X:")
     * @return X 라벨 문자열
     */
    String xLabel();

    /**
     * 2D 전용 Y 라벨(예: "Y:")
     * @return Y 라벨 문자열
     */
    String yLabel();

    /**
     * 1D 전용 값 라벨(예: "V:")
     * @return V 라벨 문자열
     */
    String vLabel();
}
