package localutil.print.core;

import localutil.print.api.Highlighter1D;
import localutil.print.api.Highlighter2D;
import localutil.print.api.LinePrinter;
import localutil.print.api.MatrixPrinter;
import localutil.print.api.Style;

/**
 * 정렬·헤더·폭 계산 단일 렌더
 */
public final class Render {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED_TEXT = "\u001B[31m";
    private static final String ANSI_YELLOW_BG = "\u001B[43m";

    private Render() {}

    public static void print1D(Style style, int length, LinePrinter.Cell1D cell) {
        print1D(style, length, cell, Highlighter1D.NONE);
    }

    /**
     * 1D 데이터를 렌더링하고 콘솔에 출력합니다.
     */
    public static void print1D(Style style, int length, LinePrinter.Cell1D cell, Highlighter1D highlighter) {
        // 1. 값 캐시 및 열 폭 계산
        String[] values = new String[length];
        String[] indices = new String[length];
        int[] colWidths = new int[length];
        for (int i = 0; i < length; i++) {
            values[i] = cell.at(i);
            indices[i] = String.valueOf(i);
            colWidths[i] = Math.max(values[i].length(), indices[i].length());
        }

        // 2. 접두사 폭 계산
        int prefixWidth = Math.max(style.xLabel().length(), style.vLabel().length());
        String xPrefix = padRight(style.xLabel(), prefixWidth);
        String vPrefix = padRight(style.vLabel(), prefixWidth);

        // 3. StringBuilder로 출력 생성
        StringBuilder sb = new StringBuilder();

        // 헤더 행
        sb.append(xPrefix).append(" ");
        for (int i = 0; i < length; i++) {
            sb.append(padLeft(indices[i], colWidths[i]));
            if (i < length - 1) sb.append(" ");
        }
        sb.append("\n");

        // 값 행
        sb.append(vPrefix).append(" ");
        for (int i = 0; i < length; i++) {
            String paddedValue = padLeft(values[i], colWidths[i]);
            if (highlighter.shouldHighlight(i)) {
                paddedValue = ANSI_YELLOW_BG + ANSI_RED_TEXT + paddedValue + ANSI_RESET;
            }
            sb.append(paddedValue);
            if (i < length - 1) sb.append(" ");
        }
        sb.append("\n");

        System.out.print(sb.toString());
    }

    public static void print2D(Style style, int height, int width, MatrixPrinter.Cell2D cell) {
        print2D(style, height, width, cell, Highlighter2D.NONE);
    }

    /**
     * 2D 데이터를 렌더링하고 콘솔에 출력합니다.
     */
    public static void print2D(Style style, int height, int width, MatrixPrinter.Cell2D cell, Highlighter2D highlighter) {
        // 1. 값 캐시
        String[][] cache = new String[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cache[y][x] = cell.at(y, x);
            }
        }

        // 2. 열 폭 계산
        int[] colWidths = new int[width];
        for (int x = 0; x < width; x++) {
            int maxW = String.valueOf(x).length();
            for (int y = 0; y < height; y++) {
                maxW = Math.max(maxW, cache[y][x].length());
            }
            colWidths[x] = maxW;
        }

        // 3. 행 인덱스 자릿수 계산
        int rowDigits = String.valueOf(height - 1).length();
        if (rowDigits == 0) rowDigits = 1;

        // 4. 헤더 접두 폭 계산 및 X 라벨 준비
        String yLabelPart = style.yLabel() + " ";
        // Y + ' ' + '[' + N + '] '
        int headerPrefixWidth = yLabelPart.length() + 1 + rowDigits + 1 + 1;
        String xLabelHeader = padLeft(style.xLabel(), headerPrefixWidth - 1);


        StringBuilder sb = new StringBuilder();

        // 5. 헤더 행 출력
        sb.append(xLabelHeader).append(" ");
        for (int x = 0; x < width; x++) {
            sb.append(padLeft(String.valueOf(x), colWidths[x]));
            if (x < width - 1) sb.append(" ");
        }
        sb.append("\n");

        // 6. 데이터 행 출력
        for (int y = 0; y < height; y++) {
            // 행 접두사
            if (y == 0) {
                sb.append(yLabelPart);
            } else {
                sb.append(" ".repeat(yLabelPart.length()));
            }
            sb.append("[").append(padLeft(String.valueOf(y), rowDigits)).append("] ");

            // 데이터
            for (int x = 0; x < width; x++) {
                String paddedValue = padLeft(cache[y][x], colWidths[x]);
                if (highlighter.shouldHighlight(y, x)) {
                    paddedValue = ANSI_YELLOW_BG + ANSI_RED_TEXT + paddedValue + ANSI_RESET;
                }
                sb.append(paddedValue);
                if (x < width - 1) sb.append(" ");
            }
            sb.append("\n");
        }

        System.out.print(sb.toString());
    }

    private static String padLeft(String s, int n) {
        if (s.length() >= n) return s;
        return " ".repeat(n - s.length()) + s;
    }
    
    private static String padRight(String s, int n) {
        if (s.length() >= n) return s;
        return s + " ".repeat(n - s.length());
    }
}
