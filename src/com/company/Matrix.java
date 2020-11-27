package com.company;

public class Matrix {
    private String[][] matrix;
    private int current_row_position;
    private int current_column_position;

    public Matrix(){
        matrix = new String[][]{
                {"0", "0", "0", "0"},
                {"0", "1", "0", "0"},
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"},
        };

        current_row_position = 1;
        current_column_position = 1;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int row, int column) {

        matrix[row][column] = "1";
        matrix[current_row_position][current_column_position] = "0";
        current_row_position = row;
        current_column_position = column;
    }

    public int getCurrent_row_position() {
        return current_row_position;
    }

    public int getCurrent_column_position() {
        return current_column_position;
    }
}
