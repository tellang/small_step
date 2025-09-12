package localutil.print.console;

import localutil.print.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 콘솔 구현(ASCII/UNICODE, Enum 싱글톤)
 */
public enum ConsolePrinter implements LinePrinter, MatrixPrinter {
    ASCII("X:", "Y:", "V:"),
    UNICODE("X→", "Y↓", "V:");

    private final String xLabel;
    private final String yLabel;
    private final String vLabel;

    ConsolePrinter(String xLabel, String yLabel, String vLabel) {
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.vLabel = vLabel;
    }

    @Override public String xLabel() { return xLabel; }
    @Override public String yLabel() { return yLabel; }
    @Override public String vLabel() { return vLabel; }

    // --- Methods for List from LinePrinter ---
    public <T> void printList(List<T> data) { printList(data, data.size(), Highlighter1D.NONE); }
    public <T> void printList(List<T> data, int xLimit) { printList(data, xLimit, Highlighter1D.NONE); }
    public <T> void printList(List<T> data, Highlighter1D highlighter) { printList(data, data.size(), v -> v == null ? "" : v.toString(), highlighter); }
    public <T> void printList(List<T> data, int xLimit, Highlighter1D highlighter) { printList(data, xLimit, v -> v == null ? "" : v.toString(), highlighter); }
    public <T> void printList(List<T> data, int xLimit, Function<T, String> formatter) { printList(data, xLimit, formatter, Highlighter1D.NONE); }
    public <T> void printList(List<T> data, int xLimit, Function<T, String> formatter, Highlighter1D highlighter) {
        int len = Math.min(data.size(), xLimit);
        print1D(len, x -> {
            T item = data.get(x);
            return item == null ? "" : formatter.apply(item);
        }, highlighter);
    }

    // --- Methods for List-based matrices from MatrixPrinter ---
    public <T> void printListOfLists(List<List<T>> data) { printListOfLists(data, data.size(), -1, Highlighter2D.NONE); }
    public <T> void printListOfLists(List<List<T>> data, int yLimit, int xLimit) { printListOfLists(data, yLimit, xLimit, Highlighter2D.NONE); }
    public <T> void printListOfLists(List<List<T>> data, Highlighter2D highlighter) { printListOfLists(data, data.size(), -1, highlighter); }
    public <T> void printListOfLists(List<List<T>> data, int yLimit, int xLimit, Highlighter2D highlighter) {
        printListOfLists(data, yLimit, xLimit, v -> v == null ? "" : v.toString(), highlighter);
    }
    public <T> void printListOfLists(List<List<T>> data, int yLimit, int xLimit, Function<T, String> formatter) { printListOfLists(data, yLimit, xLimit, formatter, Highlighter2D.NONE); }
    public <T> void printListOfLists(List<List<T>> data, int yLimit, int xLimit, Function<T, String> formatter, Highlighter2D highlighter) {
        int h = (yLimit > 0) ? Math.min(data.size(), yLimit) : data.size();
        if (h == 0) { System.out.println("(empty)"); return; }
        int w = data.stream().limit(h).mapToInt(List::size).max().orElse(0);
        if (xLimit > 0) w = Math.min(w, xLimit);
        print(h, w, (y, x) -> {
            if (y < data.size() && x < data.get(y).size() && data.get(y).get(x) != null) {
                return formatter.apply(data.get(y).get(x));
            }
            return "";
        }, highlighter);
    }

    public <T> void printArrayOfLists(List<T>[] data) { printArrayOfLists(data, data.length, -1, Highlighter2D.NONE); }
    public <T> void printArrayOfLists(List<T>[] data, int yLimit, int xLimit) { printArrayOfLists(data, yLimit, xLimit, Highlighter2D.NONE); }
    public <T> void printArrayOfLists(List<T>[] data, Highlighter2D highlighter) { printArrayOfLists(data, data.length, -1, highlighter); }
    public <T> void printArrayOfLists(List<T>[] data, int yLimit, int xLimit, Highlighter2D highlighter) {
        printArrayOfLists(data, yLimit, xLimit, v -> v == null ? "" : v.toString(), highlighter);
    }
    public <T> void printArrayOfLists(List<T>[] data, int yLimit, int xLimit, Function<T, String> formatter) { printArrayOfLists(data, yLimit, xLimit, formatter, Highlighter2D.NONE); }
    public <T> void printArrayOfLists(List<T>[] data, int yLimit, int xLimit, Function<T, String> formatter, Highlighter2D highlighter) {
        int h = (yLimit > 0) ? Math.min(data.length, yLimit) : data.length;
        if (h == 0) { System.out.println("(empty)"); return; }
        int w = Arrays.stream(data, 0, h).mapToInt(List::size).max().orElse(0);
        if (xLimit > 0) w = Math.min(w, xLimit);
        print(h, w, (y, x) -> {
            if (y < data.length && x < data[y].size() && data[y].get(x) != null) {
                return formatter.apply(data[y].get(x));
            }
            return "";
        }, highlighter);
    }
}