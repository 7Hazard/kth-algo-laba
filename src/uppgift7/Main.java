package uppgift7;

import java.util.*;

public class Main {
    enum Cell {
        b1, // (|)
            // --

        b2, // (|)
            //  --

        b3, // (-)-
            //    |

        b4, // (-)-
            //  |

        box,    // Single box
        empty
    }

    static class Step {
        private int row;
        private int column;
        Cell[][] puzzle;
        boolean testedb1 = false;
        boolean testedb2 = false;
        boolean testedb3 = false;
        boolean testedb4 = false;
        Step(Cell[][] puzzle, int row, int column)
        {
            // copy into puzzle
            this.puzzle = new Cell[5][5];
            for (int r = 0; r < 5; r++) {
                this.puzzle[r] = new Cell[5];
                for (int c = 0; c < 5; c++) {
                    this.puzzle[r][c] = puzzle[r][c];
                }
            }
            this.row = row;
            this.column = column;
        }

        public Cell[][] getPuzzle() {
            // copy into puzzle
            var puzzle = new Cell[5][5];
            for (int r = 0; r < 5; r++) {
                puzzle[r] = new Cell[5];
                for (int c = 0; c < 5; c++) {
                    puzzle[r][c] = this.puzzle[r][c];
                }
            }
            return puzzle;
        }
    }

    public static void main(String[] args) {
        var steps = new Stack<Step>();
        var solutions = new HashSet<String>();

        // make puzzle
        var puzzle = new Cell[5][5];
        for (int r = 0; r < puzzle.length; r++) {
            puzzle[r] = new Cell[5];
            Arrays.fill(puzzle[r], Cell.empty);
        }

        // place box
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter box row (0-4)");
        var boxRow = scanner.nextInt();
        System.out.println("Enter box column (0-4)");
        var boxColumn = scanner.nextInt();
        puzzle[boxRow][boxColumn] = Cell.box;

        steps.push(new Step(puzzle, 0, 0));
        while(!steps.empty())
        {
            var currentStep = steps.peek();
            puzzle = currentStep.getPuzzle(); // get puzzle of current step

            // check if current cell is empty
            if(puzzle[currentStep.row][currentStep.column] == Cell.empty)
            {
                // try placing a block
                // try block 1
                // (|)
                // --
                if(!currentStep.testedb1
                        && currentStep.column != 0
                        && puzzle[currentStep.row+1][currentStep.column-1] == Cell.empty
                        && puzzle[currentStep.row+1][currentStep.column] == Cell.empty)
                {
                    // place block and save state
                    puzzle[currentStep.row][currentStep.column] = Cell.b1;
                    puzzle[currentStep.row+1][currentStep.column-1] = Cell.b1;
                    puzzle[currentStep.row+1][currentStep.column] = Cell.b1;
                    currentStep.testedb1 = true;
                }
                // (|)
                //  --
                else if(!currentStep.testedb2
                        && currentStep.column != 4
                        && puzzle[currentStep.row+1][currentStep.column] == Cell.empty
                        && puzzle[currentStep.row+1][currentStep.column+1] == Cell.empty)
                {
                    puzzle[currentStep.row][currentStep.column] = Cell.b2;
                    puzzle[currentStep.row+1][currentStep.column] = Cell.b2;
                    puzzle[currentStep.row+1][currentStep.column+1] = Cell.b2;
                    currentStep.testedb2 = true;
                }
                // (-)-
                //    |
                else if(!currentStep.testedb3
                        && currentStep.column != 4
                        && puzzle[currentStep.row][currentStep.column+1] == Cell.empty
                        && puzzle[currentStep.row+1][currentStep.column+1] == Cell.empty)
                {
                    puzzle[currentStep.row][currentStep.column] = Cell.b3;
                    puzzle[currentStep.row][currentStep.column+1] = Cell.b3;
                    puzzle[currentStep.row+1][currentStep.column+1] = Cell.b3;
                    currentStep.testedb3 = true;
                }
                // (-)-
                //  |
                else if(!currentStep.testedb4
                        && currentStep.column != 4
                        && puzzle[currentStep.row][currentStep.column+1] == Cell.empty
                        && puzzle[currentStep.row+1][currentStep.column] == Cell.empty)
                {
                    puzzle[currentStep.row][currentStep.column] = Cell.b4;
                    puzzle[currentStep.row][currentStep.column+1] = Cell.b4;
                    puzzle[currentStep.row+1][currentStep.column] = Cell.b4;
                    currentStep.testedb4 = true;
                }
                else {
                    // no block placeable on empty cell, backtrack and discard last step
                    steps.pop();
                    // continue checking from there
                    continue;
                }

                // if last cell, check if solved after placement
                if(currentStep.row == 3 && currentStep.column == 4 && isSolved(puzzle))
                {
                    solutions.add(puzzleString(puzzle));
                    steps.pop();
                    continue;
                }

                // move to next cell
                var nextColumn = currentStep.column+1;
                var nextRow = currentStep.row;
                if(nextColumn == 5)
                {
                    nextRow = currentStep.row+1;
                    if(nextRow == 5-1)
                    {
                        // done all rows
                        // go back one step, try other blocks
                        steps.pop();
                        continue;
                    }
                    nextColumn=0;
                }
                // make new step and push it for next cell
                steps.push(new Step(puzzle, nextRow, nextColumn));
            }
            else {
                // current cell is not empty, move to next cell
                if(currentStep.row == 3 && currentStep.column == 4 && isSolved(puzzle))
                {
                    solutions.add(puzzleString(puzzle));
                    steps.pop();
                    continue;
                }

                currentStep.column++;
                if(currentStep.column == 5)
                {
                    currentStep.row++;
                    if(currentStep.row == 5-1)
                    {
                        // done all rows
                        // discard last step, go back one step, try other blocks
                        steps.pop();
                        continue;
                    }
                    currentStep.column=0;
                }
            }
        }

        // show solutions
        for (var solution : solutions) {
            System.out.println(solution);
        }
        System.out.println("Solutions: "+solutions.size());
    }

    private static boolean isSolved(Cell[][] puzzle) {
        for (Cell[] row : puzzle) {
            for (Cell cell : row) {
                if(cell == Cell.empty)
                    return false;
            }
        }
        return true;
    }

    private static String puzzleString(Cell[][] puzzle) {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : puzzle) {
            for (Cell cell : row) {
                switch (cell) {
                    case b1:
                        sb.append("1  ");
                        break;
                    case b2:
                        sb.append("2  ");
                        break;
                    case b3:
                        sb.append("3  ");
                        break;
                    case b4:
                        sb.append("4  ");
                        break;
                    case box:
                        sb.append("B  ");
                        break;
                    case empty:
                        sb.append("E  ");
                        break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
