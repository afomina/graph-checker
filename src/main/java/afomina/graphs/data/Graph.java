package afomina.graphs.data;

import afomina.graphs.count.ConnectivityCounter;
import afomina.graphs.count.RadDimCounter;
import afomina.graphs.count.VertexConnectivity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "graph")
public class Graph {
    private short[][] matrix;
    private String name;

    private Integer id;
    private String code;
    private int order;
    private Integer edgeAmount;
    private Integer edgeConnectivity;

    private Integer vertexConnectivity;
    private Boolean connected;
    private Integer radius;
    private Integer diametr;

    public Graph() {
    }

    public Graph(short[][] matrix) {
        this.matrix = matrix;
    }

    public Graph(short[][] matrix, String name) {
        this.matrix = matrix;
        this.name = name;
    }

    @Transient
    public short[][] getMatrix() {
        if (matrix == null) {
            BigInteger integer = new BigInteger(getCode(), 16);
            String code2 = integer.toString(2);
            matrix = new short[order][order];
            int idx = 0;
            for (int i = 0; i < matrix.length - 1; i++) {
                for (int j = i + 1; j < order; j++) {
                    matrix[i][j] = matrix[j][i] = Short.parseShort(code2.charAt(idx++) + ""); //TODO: not really sure about this
                }
            }
        }
        return matrix;
    }

    @Column(name = "vertex")
    public int getOrder() {
       /* if (order == 0 && matrix != null) {
            order = matrix.length;
        }*/
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
    public Integer getEdgeAmount() {
        /*if (edgeAmount == null) {
            int cnt = 0;
            for (int i = 0; i < matrix.length - 1; i++) {
                short[] shorts = matrix[i];
                for (int j = i + 1; j < shorts.length; j++) {
                    cnt += shorts[j];
                }
            }
            edgeAmount = cnt;
        }*/
        return edgeAmount;
    }

    public String getCode() {
        /*if (code == null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < matrix.length - 1; i++) {
                short[] shorts = matrix[i];
                for (int j = i + 1; j < shorts.length; j++) {
                    builder.append(shorts[j]);
                }
            }
            BigInteger big = new BigInteger(builder.toString(), 2);
            code = big.toString(16);
        }*/
        return code;
    }

    @Column(name = "EDGECON")
    public Integer getEdgeConnectivity() {
        return edgeConnectivity;
    }

    public void setEdgeConnectivity(Integer edgeCon) {
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

    public void setEdgeAmount(Integer edgeAmount) {
        this.edgeAmount = edgeAmount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "conn")
    public Boolean getConnected() {
        /*if (connected == null) {
            connected = new ConnectivityCounter().getInvariant(this);
        }*/
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Integer getRadius() {
        /*if (radius == null) {
            new RadDimCounter().getInvariant(this);
        }*/
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getDiametr() {
        /*if (diametr == null) {
            new RadDimCounter().getInvariant(this);
        }*/
        return diametr;
    }

    public void setDiametr(Integer diametr) {
        this.diametr = diametr;
    }

    @Column(name = "vertcon")
    public Integer getVertexConnectivity() {
       /* if (vertexConnectivity == null) {
            vertexConnectivity = new VertexConnectivity().getInvariant(this);
        }*/
        return vertexConnectivity;
    }

    public void setVertexConnectivity(Integer vertexConnectivity) {
        this.vertexConnectivity = vertexConnectivity;
    }
}
