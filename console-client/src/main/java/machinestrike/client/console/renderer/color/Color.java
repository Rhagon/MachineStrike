package machinestrike.client.console.renderer.color;

public final class Color {

    private final int r, g, b;
    private final String str;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.str = "\033[38;2;" + r + ";" + g + ";" + b + "m";
    }

    public int r() {
        return r;
    }

    public int g() {
        return g;
    }

    public int b() {
        return b;
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public boolean equals(Object second) {
        if(second instanceof Color c) {
            return r == c.r && g == c.g && b == c.b;
        }
        return false;
    }

}
