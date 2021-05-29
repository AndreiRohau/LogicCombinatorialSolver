package me.ras;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;
import static me.ras.Main.printLists;

public class MainTest {
    private static final byte[][] MATRIX = new byte[][]{
            {0, 1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 1, 1},
            {1, 0, 1, 1, 0, 0, 0},
            {1, 0, 0, 0, 1, 0, 1},
            {0, 1, 1, 0, 1, 1, 0},
            {1, 0, 1, 1, 1, 0, 0}
    };
    private static final byte[][] CUTTED_MATRIX = new byte[][]{
            {0, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1},
            {0, 1, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
    };
    private static final byte[][] EMPTY_MATRIX = new byte[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
    };

    private static final byte[][] INVALID_MATRIX = new byte[][]{
            {0, 1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 1, 1},
            {0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 1},
            {0, 1, 1, 0, 1, 1, 0},
            {0, 0, 1, 1, 1, 0, 0}
    };

    private static final List<List<Byte>> LISTS = Main.convertMatrixToLists(MATRIX);
    private static final List<List<Byte>> LISTS_FOR_CUT_TEST = Main.convertMatrixToLists(MATRIX);
    private static final List<List<Byte>> CUTTED_LISTS = Main.convertMatrixToLists(CUTTED_MATRIX);
    private static final List<List<Byte>> EMPTY_LISTS = Main.convertMatrixToLists(EMPTY_MATRIX);

    @Test
    public void testPrintMatrix() {
        Main.printMatrix(MATRIX);
    }

    @Test
    public void testValidation_valid() {
        boolean isValid = Main.isValid(MATRIX);
        assertTrue(isValid);
    }

    @Test
    public void testValidation_invalid() {
        boolean isValid = Main.isValid(INVALID_MATRIX);
        assertFalse(isValid);
    }

    @Test
    public void testIsTrue_valid() {
        boolean isValid = Main.isTrue((byte) 1);
        assertTrue(isValid);
    }

    @Test
    public void testIsFalse_valid() {
        boolean isValid = Main.isFalse((byte) 0);
        assertTrue(isValid);
    }

    @Test
    public void testConvertMatrixToLists_valid() {
        List<List<Byte>> lists = Main.convertMatrixToLists(MATRIX);
        printLists(lists);
    }

    @Test
    public void testFindLeastMassiveColumn_valid() {
        printLists(LISTS);
        int leastMassiveColumn = Main.findLeastMassiveColumn(LISTS);
        assertEquals(3, leastMassiveColumn);
    }

    @Test
    public void testFindLeastMassiveColumn_valid_cutted() {
        printLists(CUTTED_LISTS);
        int leastMassiveColumn = Main.findLeastMassiveColumn(CUTTED_LISTS);
        assertEquals(5, leastMassiveColumn);
    }

    @Test
    public void testFindMostMassiveRowIndex_valid() {
        printLists(LISTS);
        int leastMassiveColumn = Main.findLeastMassiveColumn(LISTS);
        int mostMassiveRowIndex = Main.findMostMassiveRowIndex(LISTS, leastMassiveColumn);
        assertEquals(3, leastMassiveColumn);
        assertEquals(5, mostMassiveRowIndex);
    }

    @Test
    public void testFindMostMassiveRowIndex_valid_cutted() {
        printLists(CUTTED_LISTS);
        int leastMassiveColumn = Main.findLeastMassiveColumn(CUTTED_LISTS);
        int mostMassiveRowIndex = Main.findMostMassiveRowIndex(CUTTED_LISTS, leastMassiveColumn);
        assertEquals(5, leastMassiveColumn);
        assertEquals(1, mostMassiveRowIndex);
    }

    @Test
    public void testSetFoundRowAndEffectedColumnsToZero_valid() {
        printLists(LISTS_FOR_CUT_TEST);
        int leastMassiveColumn = Main.findLeastMassiveColumn(LISTS_FOR_CUT_TEST);
        int mostMassiveRowIndex = Main.findMostMassiveRowIndex(LISTS_FOR_CUT_TEST, leastMassiveColumn);
        Main.setFoundRowAndEffectedColumnsToZero(LISTS_FOR_CUT_TEST, mostMassiveRowIndex);
        System.out.println("--------------------");
        printLists(LISTS_FOR_CUT_TEST);
    }

    @Test
    public void testHasUncoveredColumns_true() {
        assertTrue(Main.hasUncoveredColumns(CUTTED_LISTS));
    }

    @Test
    public void testHasUncoveredColumns_false() {
        assertFalse(Main.hasUncoveredColumns(EMPTY_LISTS));
    }

}