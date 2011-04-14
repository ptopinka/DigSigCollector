package test;

public class Calculator {
    private int a = 0;
    private int b = 0; // assume b > a
    
    public void setNums(int numA, int numB) {
        a = numA;
        b = numB;
    }
    
    public int add() {
        return a + b;
    }
    
    public int [] getNumInRange() {    
        int x = a;
        int len = (b - a) + 1;
        int [] range = new int [len];
        for (int i = 0; i < len; i++) {
            range[i]= x++;
            System.out.println("i: " + i + " ; range[i]: " + range[i]);
        }
        return range;
    }
}