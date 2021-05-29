package me.ras;

import java.util.*;

public class Main {
    public static final byte[][] MATRIX = new byte[][]{
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
    public static final byte[][] EZ_MATRIX = new byte[][]{
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {1, 0, 1, 0},
            {1, 0, 1, 1}
    };
    public static final byte[][] EZ_MATRIX_CUTTED = new byte[][]{
            {0, 1, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };

    public static void main(String[] args) {
        byte[][] m = EZ_MATRIX;
        // print the matrix
        printMatrix(m);
        // research
        List<Integer> rowIndexesThatCover = findShortestCoverageOfBoolMatrixApproximate(m);
        // show results
        System.out.println(rowIndexesThatCover);
    }

    public static List<Integer> findShortestCoverageOfBoolMatrixApproximate(byte[][] matrixToResearch) {
        // validation
        if (!isValid(matrixToResearch)) {
            System.err.println("Matrix does not have shortest coverage. One of the columns has no 1.");
            return null;
        }
        List<Integer> results = new ArrayList<>();
        // conversion to List<List>
        List<List<Byte>> lists = convertMatrixToLists(matrixToResearch);

        // while the matrix has elements after been cut
        while(hasUncoveredColumns(lists)) {
            // apply algorithm to find row
            int foundRowIndex = applySearchRowAlgorithm(lists);
            // save found row
            results.add(foundRowIndex);
            // cut the matrix
            setFoundRowAndEffectedColumnsToZero(lists, foundRowIndex);
        }

        return results;
    }

    public static boolean hasUncoveredColumns(List<List<Byte>> lists) {
        boolean hasUncoveredColumns = false;
        for (List<Byte> list : lists) {
            for (Byte aByte : list) {
                if (aByte == 1) {
                    hasUncoveredColumns = true;
                }
            }
        }
        return hasUncoveredColumns;
    }

    public static void setFoundRowAndEffectedColumnsToZero(List<List<Byte>> lists, int foundRowIndex) {
        for (int i = 0; i < lists.get(foundRowIndex).size(); i++) {
            if (lists.get(foundRowIndex).get(i) == 1) {
                for (List<Byte> list : lists) {
                    list.set(i, (byte) 0);
                }
            }
        }
    }

    private static int applySearchRowAlgorithm(List<List<Byte>> lists) {
        // find the least massive column
        int leastMassiveColumnIndex = findLeastMassiveColumn(lists);

        // in found least massive column - find most massive row index
        return findMostMassiveRowIndex(lists, leastMassiveColumnIndex);
    }

    public static int findMostMassiveRowIndex(List<List<Byte>> lists, int leastMassiveColumnIndex) {
        Map<Integer, Integer> rowIndexToWeight = new HashMap<>();
        // find all rows weights in according to leastMassiveColumnIndex
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).get(leastMassiveColumnIndex) == 1) {
                int rowWeight = 0;
                for (int j = 0; j < lists.get(i).size(); j++) {
                    rowWeight = rowWeight + lists.get(i).get(j);
                }
                rowIndexToWeight.put(i, rowWeight);
            }
        }

        // find most massive row index among found weights
        int resultMostMassiveRowIndex = 0;
        int currentMostMassiveRowValue = 0;
        for (int index: rowIndexToWeight.keySet()) {
            if (currentMostMassiveRowValue < rowIndexToWeight.get(index)) {
                currentMostMassiveRowValue = rowIndexToWeight.get(index);
                resultMostMassiveRowIndex = index;
            }
        } return resultMostMassiveRowIndex;
    }

    public static int findLeastMassiveColumn(List<List<Byte>> lists) {
        // find columns weights
        List<Integer> columnWeights = new ArrayList<>();
        for (int i = 0; i < lists.get(0).size(); i++) {
            columnWeights.add(0);
        }
        for (List<Byte> list : lists) {
            for (int j = 0; j < list.size(); j++) {
                columnWeights.set(j, columnWeights.get(j) + list.get(j));
            }
        }
        // find least massive column
        int indexOfLeastMassiveColumn = 0;
        int currentLeastMassiveColumn = columnWeights.get(0);
        for (int i = 0; i < columnWeights.size(); i++) {
            Integer columnWeight = columnWeights.get(i);
            if ((currentLeastMassiveColumn == 0 && columnWeight != 0) || (currentLeastMassiveColumn > columnWeight)) {
                currentLeastMassiveColumn = columnWeight;
                indexOfLeastMassiveColumn = i;
            }
        }
        return indexOfLeastMassiveColumn;
    }

    public static List<List<Byte>> convertMatrixToLists(byte[][] matrix) {
        List<List<Byte>> lists = new LinkedList<>();
        for (byte[] bytes : matrix) {
            List<Byte> newRowList = new LinkedList<>();
            for (byte aByte : bytes) {
                newRowList.add(aByte);
            }
            lists.add(newRowList);
        }
        return lists;
    }

    public static boolean isValid(byte[][] matrixToResearch) {
        byte[] coveredColumns = new byte[matrixToResearch[0].length];
        for (byte[] row: matrixToResearch) {
            for (int i = 0; i < row.length; i++) {
                if (isTrue(row[i])) {
                    coveredColumns[i] = 1;
                }
            }
        }
        // check all columns covered
        int sum = 0;
        for (byte element: coveredColumns) {
            sum = sum + element;
        }
        return sum == coveredColumns.length;
    }

    public static boolean isTrue(byte b) {
        return b == 1;
    }

    public static boolean isFalse(byte b) {
        return b == 0;
    }

    public static void printMatrix(byte[][] matrixToResearch) {
        for (byte[] row: matrixToResearch) {
            for (byte cell: row) {
                System.out.print(cell + "  ");
            }
            System.out.println();
        }
    }

    public static void printLists(List<List<Byte>> lists) {
        for (List<Byte> list: lists) {
            for (byte element: list) {
                System.out.print(element + "  ");
            }
            System.out.println();
        }
    }
}
