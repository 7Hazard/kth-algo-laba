package uppgift1;

public class Main {
    public static void main(String[] args) {
        { // remove head
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            list.remove(0);
            System.out.println("");
        }
        { // remove in middle
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            list.addBack(66);
            list.addBack(60);
            list.remove(1);
            System.out.println("");
        }
        { // remove tail
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            list.addBack(66);
            list.remove(1);
            System.out.println("");
        }
    }
}
