package afomina.graphs.mine.condition;

import afomina.graphs.data.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Condition {

    public enum OPERATION {
        SUM("{} + {}"),
        MULT("{} * {}"),
        POW("power({}, {})");

        String pattern;

        OPERATION(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }

    public enum INVARIANT {
        CHROM_NUM("chromeNumber"),
        COMPONENTS("components"),
        DIAMETER("diametr"),
        EDGE_CON("edgeConnectivity"),
        EXPONENT("exp"),
        GIRTH("girth"),
        RADIUS("radius"),
        VERTCON("vertexConnectivity");

        String property;

        INVARIANT(String property) {
            this.property = property;
        }
    }

    private OPERATION operation;
    private INVARIANT[] invariants = new INVARIANT[3];
    private Boolean result;
    /**
     * Cashed fields
     */
    private Map<String, Field> fields = new HashMap();

    private static final Logger log = LoggerFactory.getLogger(Condition.class);

    public Condition(OPERATION operation, INVARIANT... invariants) {
        this.operation = operation;
        this.invariants = invariants;
    }

    public OPERATION getOperation() {
        return operation;
    }

    public void setOperation(OPERATION operation) {
        this.operation = operation;
    }

    public INVARIANT[] getInvariants() {
        return invariants;
    }

    public void setInvariants(INVARIANT[] invariants) {
        this.invariants = invariants;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public boolean calculate(Graph graph) {
        Field aField = getField(invariants[0].property);
        Field bField = getField(invariants[1].property);
        Field cField = getField(invariants[2].property);
        if (aField == null || bField == null || cField == null) {
            return false;
        }
        int a, b, c;
        try {
            a = aField.getInt(graph);
            b = bField.getInt(graph);
            c = cField.getInt(graph);
        } catch (IllegalAccessException e) {
            log.error("condition calc error: illegal access to field", e);
            return false;
        }
        switch (operation) {
            case SUM:
                setResult(a <= b + c);
                break;
            case MULT:
                setResult(a <= b * c);
                break;
            case POW:
                setResult(a <= Math.pow(b, c));
                break;
        }
        return getResult();
    }

    @Override
    public String toString() {
        String expression = operation.pattern;
        expression.replaceFirst("\\{\\}", invariants[1].property);
        expression.replaceFirst("\\{\\}", invariants[2].property);
        return invariants[0].property + " <= " + expression + " is " + result;
    }

    private final static Class graphClass = Graph.class;

    private Field getField(String prop) {
        if (fields.containsKey(prop)) {
            return fields.get(prop);
        }
        try {
            Field field = graphClass.getDeclaredField(prop);
            fields.put(prop, field);
            return field;
        } catch (NoSuchFieldException e) {
            log.error("condition calc error: no such field", e);
        }
        return null;
    }

}
