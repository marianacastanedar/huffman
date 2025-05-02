public class HuffmanNode implements Comparable<HuffmanNode> {
    public char symbol;
    public int frequency;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(char symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.symbol = '\0'; //por si es nulo
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}
