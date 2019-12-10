package com.SoftwareMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MICalculatorTest {

    /** Test cases based on example from : https://en.wikipedia.org/wiki/Halstead_complexity_measures#Example
     * */
    Object operators_string = "main, (), {}, int, scanf, &, &, &, (), (), (), ;, :,  =, +, +, /, ;, printf, ,, ,, ,, ,, ,, ,, ,, ;";
    Object[] operators = ((String) operators_string).split(", ");       // Single comma is the separator.

    Object operands_string = "a, b, c, avg, '%d %d %d', 3, a, b, c, 'avg = %d', avg, a, b, c, avg";
    Object[] operands = ((String) operands_string).split(", ");

MICalculator miCalculator= new MICalculator();
    @Test
    void calculateHalstead() {


        System.out.println(miCalculator.calculateHalstead(operators,operands));
        Assertions.assertEquals(miCalculator.calculateHalstead(operators,operands),178.4);

    }



    @Test
    void calculateCC() {

        int edges = 10;
        int nodes = 20;
        System.out.println(miCalculator.calculateCC(edges,nodes));
        Assertions.assertEquals(miCalculator.calculateCC(edges,nodes),32);

    }

    @Test
    void calculateMI() {
        int edges = 10;
        int nodes = 20;
        int lloc = 100;
        int loc = 150;
        int cloc = 20;

        System.out.println(miCalculator.calculateMI(operators, operands, edges,nodes, lloc, loc, cloc));
        Assertions.assertEquals(miCalculator.calculateMI(operators, operands, edges,nodes, lloc, loc, cloc),17);


    }
}