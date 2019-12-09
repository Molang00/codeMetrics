package com.testcode;

public class Test1 {
    public static void main(String[] args) {
        int a = 0;
        int b = this.AddOne(a);

        if (true) {
            System.out.println("Main method");
        }

        String c = new String("AADDDDDD");
        return;
    }

    public int AddOne(int a) {
        if (a < 0) {
            return -1;
        }
        return a + 1;
    }
}