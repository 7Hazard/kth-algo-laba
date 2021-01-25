package uppgift2;

/**
 * Test unit for SingleLinkedList
 */
public class Main {
    public static void main(String[] args) {
        { // remove first
            var list = new SingleLinkedList<Integer>();
            list.addBack(69); // to be removed
            list.addBack(66);
            list.addBack(60);
            var it = list.iterator();
            var next = it.next();
            it.remove();

            assert list.get(0) == 66;
            assert list.get(1) == 60;
            System.out.println("OK remove first");
        }
        { // remove second
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            list.addBack(66); // to be removed
            list.addBack(60);
            var it = list.iterator();
            var next = it.next();
            next = it.next();
            it.remove();

            assert list.get(0) == 69;
            assert list.get(1) == 60;
            System.out.println("OK remove second");
        }
        { // remove last
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            list.addBack(66);
            list.addBack(60); // to be removed
            var it = list.iterator();
            var next = it.next();
            next = it.next();
            next = it.next();
            it.remove();

            assert list.get(0) == 69;
            assert list.get(1) == 66;
            System.out.println("OK remove last");
        }
        { // next remove next remove
            var list = new SingleLinkedList<Integer>();
            list.addBack(69); // to be removed
            list.addBack(66); // to be removed
            list.addBack(60);
            var it = list.iterator();
            var next = it.next();
            it.remove();
            next = it.next();
            it.remove();
            next = it.next();

            assert list.get(0) == 60;
            System.out.println("OK next remove next remove");
        }
        { // next next remove next remove next
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            list.addBack(66); // to be removed
            list.addBack(60); // to be removed
            list.addBack(70);
            var it = list.iterator();
            var next = it.next();
            next = it.next();
            it.remove();
            next = it.next();
            it.remove();
            next = it.next();

            assert list.get(0) == 69;
            assert list.get(1) == 70;
            System.out.println("OK next next remove next remove next");
        }
        { // remove before next()
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            var it = list.iterator();
            try {
                it.remove(); // should throw
                assert false;
                System.out.println("Error");
            } catch (Exception e) {
                System.out.println("OK remove before next()");
            }
        }
        { // remove twice
            var list = new SingleLinkedList<Integer>();
            list.addBack(69);
            list.addBack(66);
            var it = list.iterator();
            var next = it.next();
            try {
                it.remove();
                it.remove(); // should throw
                assert false;
                System.out.println("Error");
            } catch (Exception e) {
                System.out.println("OK remove twice");
            }
        }
    }
}
