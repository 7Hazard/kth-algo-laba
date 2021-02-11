package uppgift5;

/**
 * Help with thought through following links
 * https://en.wikipedia.org/wiki/Trie
 * https://stackoverflow.com/questions/13109889/what-would-be-the-fastest-way-to-sort-an-array-of-words-containing-a-z-and-space
 */

import java.util.Arrays;
import java.util.HashSet;

public class Main {

    static HashSet<String> paths;
    static int leastSteps;
    private static char[] initialInput;

    public static void main(String[] args) {
        run("BEACD");
        run("BAECD");
        run("EADBC");
    }

    static void run(String input)
    {
        paths = new HashSet<>();
        leastSteps = 15;
        initialInput = input.toCharArray();
        var top = new Node(null, initialInput, "", 0);
        for (var p : paths) {
            System.out.println(p);
        }
        if(paths.isEmpty())
            System.out.println("No solutions found");
        System.out.println("");
    }

    // swap 2 first chars
    static Node b(Node parent, char[] input, String path, int steps) {
        if (steps == 15)
            return null;
        else if (isInOrder(input, steps, path))
            return null;

        var copy = Arrays.copyOf(input, input.length);
        var tmp = copy[0];
        copy[0] = copy[1];
        copy[1] = tmp;

        return new Node(parent, copy, path + 'b', ++steps);
    }

    // rotate last char to first char
    static Node s(Node parent, char[] input, String path, int steps) {
        if (steps == 15)
            return null;
        else if (isInOrder(input, steps, path))
            return null;

        // rotate characters
        var copy = Arrays.copyOf(input, input.length);

        // save last char
        var tmp = copy[copy.length - 1];

        // move all chars one step to right
        for (int i = copy.length - 1; i > 0; i--)
            copy[i] = copy[i - 1];

        // set first char to saved in tmp
        copy[0] = tmp;

        return new Node(parent, copy, path + 's', ++steps);
    }

    static boolean isInOrder(char[] input, int steps, String path) {
        char lastChar = input[0];
        for (char c : input) {
            if (lastChar > c)
                return false;
            lastChar = c;
        }

        if (leastSteps > steps) {
            leastSteps = steps;
            paths.clear();
        }
        if (steps == leastSteps) {
            paths.add(
                    Arrays.toString(initialInput) + "->" + Arrays.toString(input)
                            + ", " + path
                            + ", " + steps + " steps"
            );
        }

        return true;
    }

    static class Node {
        private final char[] value;
        private final Node parent;
        private final Node b;
        private final Node s;

        Node(Node parent, char[] value, String path, int steps) {
            this.value = value;
            this.parent = parent;
            this.b = b(this, value, path, steps);
            this.s = s(this, value, path, steps);
        }
    }
}
