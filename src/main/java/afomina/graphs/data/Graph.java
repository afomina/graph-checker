package afomina.graphs.data;

import afomina.graphs.count.ComponentCounter;
import afomina.graphs.count.Girth;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "graph")
@JsonAutoDetect
public class Graph {
    private boolean[][] matrix;
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

    private Integer chromeNumber;
    private Integer independenceNumber;

    public Graph() {
    }

    public Graph(boolean[][] matrix) {
        this.matrix = matrix;
    }

    public Graph(boolean[][] matrix, String name) {
        this.matrix = matrix;
        this.name = name;
    }

    @Transient
    public boolean[][] getMatrix() {
        if (matrix == null) {
            matrix = new boolean[order][order];
            if ("0".equals(getCode())) {
                return matrix;
            }

            BigInteger integer = new BigInteger(getCode(), 16);
            String code2 = integer.toString(2);
            int idx = 0;
            for (int i = 0; i < matrix.length - 1; i++) {
                for (int j = i + 1; j < order; j++) {
                    matrix[i][j] = matrix[j][i] = Short.parseShort(code2.charAt(idx++) + "") == 1;
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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMatrix(boolean[][] matrix) {
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

    @Column(name = "chrome_number")
    public Integer getChromeNumber() {
        return chromeNumber;
    }

    public void setChromeNumber(Integer chromeNumber) {
        this.chromeNumber = chromeNumber;
    }

    @Transient
    public boolean isAcyclic() {
        if (girth == null) {
            girth = new Girth().getInvariant(this);
        }
        return (girth == 0);
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

    @Transient
    public String matrixString() {
        boolean[][] m = getMatrix();
        String res = "";
        for (int i = 0; i < getOrder(); i++) {
            for (int j = 0; j < getOrder(); j++) {
                res += (m[i][j] ? 1 : 0) + " ";
            }
            res += "<br />";
        }
        return res;
    }

    @Transient
    public boolean isCon() {
        if (getComponents() == null) {
            setComponents(new ComponentCounter().getInvariant(this));
        }
        return getComponents() == 1;
    }

    @Column(name = "independenceNumber")
    public Integer getIndependenceNumber() {
        return independenceNumber;
    }

    public void setIndependenceNumber(Integer independenceNumber) {
        this.independenceNumber = independenceNumber;
    }

    @Transient
    public List<Integer> getAllInvariants() {
        List<Integer> invariants = new ArrayList<>();
        invariants.add(getChromeNumber());
        invariants.add(getComponents());
        invariants.add(getDiametr());
        invariants.add(getEdgeConnectivity());
        invariants.add(getExp());
        invariants.add(getGirth());
        invariants.add(getRadius());
        if (getIndependenceNumber() != null) {
            invariants.add(getIndependenceNumber());
        }
        invariants.add(getPrimitive());
        invariants.add(getTwoPartial());
        invariants.add(getVertexConnectivity());
        return invariants;
    }
}
