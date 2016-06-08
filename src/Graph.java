public class Graph {
    private short[][] matrix;
    private String name;

    public Graph(short[][] matrix) {
        this.matrix = matrix;
    }

    public Graph(short[][] matrix, String name) {
        this.matrix = matrix;
        this.name = name;
    }

    public short[][] getMatrix() {
        return matrix;
    }

    public int getOrder() {
        return matrix.length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
