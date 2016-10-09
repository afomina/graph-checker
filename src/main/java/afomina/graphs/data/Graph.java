package afomina.graphs.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "graph")
@JsonAutoDetect
public class Graph {
    private short[][] matrix;
    private String name;

    private Integer id;
    private String code;
    private int order;
    private Integer edgeAmount;
    private Integer edgeConnectivity;

    private Integer vertexConnectivity;
    private Integer connected;
    private Integer radius;
    private Integer diametr;

    private Integer components;
    private Integer girth;
    private Integer primitive;
    private Integer exp;
    private Integer twoPartial;

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
            matrix = new short[order][order];
            if ("0".equals(getCode())) {
                return matrix;
            }

            BigInteger integer = new BigInteger(getCode(), 16);
            String code2 = integer.toString(2);
            int idx = 0;
            for (int i = 0; i < matrix.length - 1; i++) {
                for (int j = i + 1; j < order; j++) {
                    matrix[i][j] = matrix[j][i] = Short.parseShort(code2.charAt(idx++) + "");
                    if (idx == code2.length()) {
                        break;
                    }
                }
                if (idx == code2.length()) {
                    break;
                }
            }
        }
        return matrix;
    }

    @Column(name = "vertex")
    public int getOrder() {
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
        return edgeAmount;
    }

    public String getCode() {
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public Integer getConnected() {
        return connected;
    }

    public void setConnected(Integer connected) {
        this.connected = connected;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Integer getDiametr() {
        return diametr;
    }

    public void setDiametr(Integer diametr) {
        this.diametr = diametr;
    }

    @Column(name = "vertcon")
    public Integer getVertexConnectivity() {
        return vertexConnectivity;
    }

    public void setVertexConnectivity(Integer vertexConnectivity) {
        this.vertexConnectivity = vertexConnectivity;
    }

    public Integer getComponents() {
        return components;
    }

    public void setComponents(Integer components) {
        this.components = components;
    }

    public Integer getGirth() {
        return girth;
    }

    public void setGirth(Integer girth) {
        this.girth = girth;
    }

    @Transient
    public boolean isAcyclic() {
        return (girth!=null && girth==0);
    }

    public Integer getPrimitive() {
        return primitive;
    }

    public void setPrimitive(Integer primitive) {
        this.primitive = primitive;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    @Column(name = "twopartial")
    public Integer getTwoPartial() {
        return twoPartial;
    }

    public void setTwoPartial(Integer twoPartial) {
        this.twoPartial = twoPartial;
    }
}
