package localutil.print.api;

import localutil.print.core.Render;

import java.util.Arrays;
import java.util.function.Function;

/**
 * 2D 출력 인터페이스(오버로딩 제공)
 */
public interface MatrixPrinter extends Style {

    @FunctionalInterface
    interface Cell2D {
        String at(int y, int x);
    }

    default void print(int height, int width, Cell2D cell) {
        print(height, width, cell, Highlighter2D.NONE);
    }

    default void print(int height, int width, Cell2D cell, Highlighter2D highlighter) {
        if (height <= 0 || width <= 0) {
            System.out.println("(empty)");
            return;
        }
        Render.print2D(this, height, width, cell, highlighter);
    }

    // --- Overloads for int[][] ---
    default void print(int[][] data) { print(data, data.length, -1, Highlighter2D.NONE); }
    default void print(int[][] data, int yLimit, int xLimit) { print(data, yLimit, xLimit, Highlighter2D.NONE); }
    default void print(int[][] data, Highlighter2D highlighter) { print(data, data.length, -1, highlighter); }
    default void print(int[][] data, int yLimit, int xLimit, Highlighter2D highlighter) {
        int h = (yLimit > 0) ? Math.min(data.length, yLimit) : data.length;
        if (h == 0) { System.out.println("(empty)"); return; }
        int w = Arrays.stream(data, 0, h).mapToInt(row -> row.length).max().orElse(0);
        if (xLimit > 0) w = Math.min(w, xLimit);
        print(h, w, (y, x) -> (y < data.length && x < data[y].length) ? String.valueOf(data[y][x]) : "", highlighter);
    }
    
    // --- Overloads for long[][] ---
    default void print(long[][] data) { print(data, data.length, -1, Highlighter2D.NONE); }
    default void print(long[][] data, int yLimit, int xLimit) { print(data, yLimit, xLimit, Highlighter2D.NONE); }
    default void print(long[][] data, Highlighter2D highlighter) { print(data, data.length, -1, highlighter); }
    default void print(long[][] data, int yLimit, int xLimit, Highlighter2D highlighter) {
        int h = (yLimit > 0) ? Math.min(data.length, yLimit) : data.length;
        if (h == 0) { System.out.println("(empty)"); return; }
        int w = Arrays.stream(data, 0, h).mapToInt(row -> row.length).max().orElse(0);
        if (xLimit > 0) w = Math.min(w, xLimit);
        print(h, w, (y, x) -> (y < data.length && x < data[y].length) ? String.valueOf(data[y][x]) : "", highlighter);
    }

    // --- Overloads for boolean[][] ---
    default void print(boolean[][] data) { print(data, data.length, -1, Highlighter2D.NONE); }
    default void print(boolean[][] data, int yLimit, int xLimit) { print(data, yLimit, xLimit, Highlighter2D.NONE); }
    default void print(boolean[][] data, Highlighter2D highlighter) { print(data, data.length, -1, highlighter); }
    default void print(boolean[][] data, int yLimit, int xLimit, Highlighter2D highlighter) {
        int h = (yLimit > 0) ? Math.min(data.length, yLimit) : data.length;
        if (h == 0) { System.out.println("(empty)"); return; }
        int w = Arrays.stream(data, 0, h).mapToInt(row -> row.length).max().orElse(0);
        if (xLimit > 0) w = Math.min(w, xLimit);
        print(h, w, (y, x) -> (y < data.length && x < data[y].length) ? String.valueOf(data[y][x]) : "", highlighter);
    }

    // --- Overloads for T[][] ---
    default <T> void print(T[][] data) { print(data, data.length, -1, v -> v == null ? "" : v.toString(), Highlighter2D.NONE); }
    default <T> void print(T[][] data, int yLimit, int xLimit) { print(data, yLimit, xLimit, v -> v == null ? "" : v.toString(), Highlighter2D.NONE); }
    default <T> void print(T[][] data, Function<T, String> formatter) { print(data, data.length, -1, formatter, Highlighter2D.NONE); }
    default <T> void print(T[][] data, Highlighter2D highlighter) { print(data, data.length, -1, v -> v == null ? "" : v.toString(), highlighter); }
    default <T> void print(T[][] data, int yLimit, int xLimit, Function<T, String> formatter) { print(data, yLimit, xLimit, formatter, Highlighter2D.NONE); }
    default <T> void print(T[][] data, int yLimit, int xLimit, Highlighter2D highlighter) { print(data, yLimit, xLimit, v -> v == null ? "" : v.toString(), highlighter); }
    default <T> void print(T[][] data, Function<T, String> formatter, Highlighter2D highlighter) { print(data, data.length, -1, formatter, highlighter); }
    default <T> void print(T[][] data, int yLimit, int xLimit, Function<T, String> formatter, Highlighter2D highlighter) {
        int h = (yLimit > 0) ? Math.min(data.length, yLimit) : data.length;
        if (h == 0) { System.out.println("(empty)"); return; }
        int w = Arrays.stream(data, 0, h).mapToInt(row -> row.length).max().orElse(0);
        if (xLimit > 0) w = Math.min(w, xLimit);
        print(h, w, (y, x) -> {
            if (y < data.length && x < data[y].length && data[y][x] != null) {
                return formatter.apply(data[y][x]);
            }
            return "";
        }, highlighter);
    }
}