package ui;


public class Range {
    private final int start;
    private final int end;


    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isInRange(int offset) {
        return start < offset && end > offset;
    }
}
