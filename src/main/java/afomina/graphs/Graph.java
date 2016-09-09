package afomina.graphs;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "graph")
public class Graph {
    private short[][] matrix;
    private String name;
    private int edgeConnectivity;
    private int edgeAmount = -1;
    private String code;
    private Integer id;
    private int order;
    private Boolean connected;

    public Graph() {}

    public Graph(short[][] matrix) {
        this.matrix = matrix;
    }

    public Graph(short[][] matrix, String name) {
        this.matrix = matrix;
        this.name = name;
    }

    @Transient
    public short[][] getMatrix() {
        return matrix;
    }

    @Column(name = "vertex")
    public int getOrder() {
        if (order == 0&& matrix != null) {
             order = matrix.length;
        }
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Transient
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

    @Column(name = "EDGE")
    public int getEdgeAmount() {
        if (edgeAmount == -1) {
            int cnt = 0;
            for (int i = 0; i < matrix.length - 1; i++) {
                short[] shorts = matrix[i];
                for (int j = i+1; j < shorts.length; j++) {
                    cnt += shorts[j];
                }
            }
            edgeAmount = cnt;
        }
        return edgeAmount;
    }

    public String getCode() {
        if (code == null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < matrix.length - 1; i++) {
                short[] shorts = matrix[i];
                for (int j = i+1; j < shorts.length; j++) {
                    builder.append(shorts[j]);
                }
            }
            BigInteger big = new BigInteger(builder.toString(), 2);
//            int decimal = Integer.parseInt(builder.toString(), 2);
            code = big.toString(16);//Integer.toString(decimal, 16);
        }
        return code;
    }

    @Column(name = "EDGECON")
    public int getEdgeConnectivity() {
        return edgeConnectivity;
    }

    public void setEdgeConnectivity(int edgeCon) {
        this.edgeConnectivity = edgeCon;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMatrix(short[][] matrix) {
        this.matrix = matrix;
    }

    public void setEdgeAmount(int edgeAmount) {
        this.edgeAmount = edgeAmount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getConnected() {
        if (connected == null) {
            connected = new ConnectivityCounter().getInvariant(this);
        }
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }
}
