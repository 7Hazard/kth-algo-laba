package uppgift6;

import java.util.*;

public class Main {
    static StringBuilder solution = new StringBuilder();
    static char[] result;

    public static void main(String[] args) {
        char[] initialInput = "BECAD".toCharArray();
        var queue = new LinkedList<Node>();
        var top = new Node(null, initialInput);
        queue.addLast(top);
        while (!queue.isEmpty()) {
            // get first in queue
            var node = queue.removeFirst();

            // b
            node.b = b(node);
            if(ifReachedEnd(node, node.b)) break;
            queue.addLast(node.b);

            // s
            node.s = s(node);
            if(ifReachedEnd(node, node.s)) break;
            queue.addLast(node.s);
        }

        if (isSolved())
            System.out.println(solution.reverse() + " " + Arrays.toString(result));
        else
            System.out.println("No solutions found");
    }

    private static boolean ifReachedEnd(Node parent, Node child) {
        // if we've reached an end
        if (child == null)
        {
            // found solution
            result = parent.value;
            walkUpSolution(parent);
            return true;
        }
        return false;
    }

    private static boolean isSolved() {
        return solution.length() != 0;
    }

    // swap 2 first chars
    static Node b(Node parent) {
        var input = parent.value;
        if (isInOrder(input)) return null;

        var copy = Arrays.copyOf(input, input.length);
        var tmp = copy[0];
        copy[0] = copy[1];
        copy[1] = tmp;

        return new Node(parent, copy);
    }

    // rotate last char to first char
    static Node s(Node parent) {
        var input = parent.value;
        if (isInOrder(input)) return null;

        // rotate characters
        var copy = Arrays.copyOf(input, input.length);

        // save last char
        var tmp = copy[copy.length - 1];

        // move all chars one step to right
        for (int i = copy.length - 1; i > 0; i--)
            copy[i] = copy[i - 1];

        // set first char to saved in tmp
        copy[0] = tmp;

        return new Node(parent, copy);
    }

    private static void walkUpSolution(Node node) {
        // stop walking up if we've reached the top
        if (node == null)
            return;
        // set this node's parent's solution to this node
        if (node.parent != null) {
            if (node == node.parent.b) // if b
            {
                solution.append('b');
            } else if (node == node.parent.s) { // s
                solution.append('s');
            } else {
                throw new IllegalStateException();
            }
        }
        // continue walking up
        walkUpSolution(node.parent);
    }

    static boolean isInOrder(char[] input) {
        char lastChar = input[0];
        for (char c : input) {
            if (lastChar > c)
                return false;
            lastChar = c;
        }
        return true;
    }

    static class Node {
        private final char[] value;
        private final Node parent;
        private Node b;
        private Node s;

        Node(Node parent, char[] value) {
            this.value = value;
            this.parent = parent;
            this.b = null;
            this.s = null;
        }
    }
}
