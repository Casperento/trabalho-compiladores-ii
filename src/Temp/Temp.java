package Temp;

public class Temp extends SimpleExp {
    private static int count;
    public boolean spillTemp;
    private int num;
    public String toString() {
        return "t" + num;
    }
    public Temp() {
        num=count++;
    }
    public Temp(int x) {
        num = x;
    }
}

