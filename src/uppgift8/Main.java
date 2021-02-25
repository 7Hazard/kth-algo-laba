package uppgift8;

public class Main {
    public static class BinarySearchTree<E extends Comparable<E>> {
        public E getNextLarger(E input) {
            if(root == null) return null;

            E result = null;

            var node = root;
            while(node != null)
            {
                var compare = node.data.compareTo(input);
                if(compare <= 0) // node data < input
                {
                    if(result != null && node.data.compareTo(result) < 0)
                        return result;
                    node = node.right;
                }
                else {
                    result = node.data;
                    node = node.left;
                }
            }
            return result;
        }

        private static class Node<E> {
            private E data;
            private Node<E> left, right;

            private Node(E d) {
                data = d;
                left = right = null;
            }

            @Override
            public String toString() {
                return data.toString();
            }
        }

        private Node<E> root;

        public BinarySearchTree() {
            root = null;
        }

        private boolean add(E data,Node<E> node){
            if(data.compareTo(node.data)==0)
                return false;
            else if(data.compareTo(node.data)<0)
                if(node.left==null){
                    node.left = new Node<E>(data);
                    return true;
                }else
                    return add(data,node.left);
            else
            if(node.right==null){
                node.right = new Node<E>(data);
                return true;
            }else
                return add(data,node.right);
        }

        public boolean add(E data){
            if(root==null){
                root = new Node<E>(data);
                return true;
            }else
                return add(data,root);
        }

        private void inOrder(Node<E> node, StringBuilder sb){
            if(node!=null){
                inOrder(node.left, sb);
                sb.append(": "+node.toString());
                inOrder(node.right, sb);
            }
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            inOrder(root,sb);
            return sb.toString();
        }

        private E find(E target, Node<E> node){
            if( node==null)
                return null;
            if(target.compareTo(node.data)==0)
                return node.data;
            if(target.compareTo(node.data)<0)
                return find(target,node.left);
            return find(target,node.right);
        }
        public E find(E target){
            return find(target, root);
        }
    }

    public static void main(String[] args) {
        var tree = new BinarySearchTree<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(5);
        tree.add(8);
        tree.add(12);

        System.out.println("-1: "+tree.getNextLarger(-1));
        System.out.println("0: "+tree.getNextLarger(0));
        System.out.println("1: "+tree.getNextLarger(1));
        System.out.println("2: "+tree.getNextLarger(2));
        System.out.println("3: "+tree.getNextLarger(3));
        System.out.println("5: "+tree.getNextLarger(5));
        System.out.println("12: "+tree.getNextLarger(12));
        System.out.println("100: "+tree.getNextLarger(100));
    }
}
