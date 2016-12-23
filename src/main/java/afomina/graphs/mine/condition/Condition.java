package afomina.graphs.mine.condition;

import afomina.graphs.data.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Condition {

    public enum OPERATION {
        SUM("{} + {}"),
        MULT("{} * {}"),
        POW("power({}, {})"),
        MINUS_ONE("{} - 1", true),
        PLUS_ONE("{} + 1", true),
        DIVIDE_TWO("{} / 2", true),
        MULT_TWO("{} * 2", true);

        String pattern;
        boolean oneParam;

        OPERATION(String pattern) {
            this.pattern = pattern;
        }

        OPERATION(String pattern, boolean oneParam) {
            this.pattern = pattern;
            this.oneParam = oneParam;
        }

        public String getPattern() {
            return pattern;
        }

        public static List<OPERATION> twoParamOperations() {
            OPERATION[] values = values();
            List<OPERATION> res = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                if (!values[i].oneParam) {
                    res.add(values[i]);
                }
            }
            return res;
        }

        public static List<OPERATION> oneParamOperations() {
            OPERATION[] values = values();
            List<OPERATION> res = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                if (values[i].oneParam) {
                    res.add(values[i]);
                }
            }
            return res;
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
        VERTCON("vertexConnectivity"),
        ORDER("order"),
        EDGES("edgeAmount"),
        INDEPENDENCE_NUM("independenceNumber");

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
        Field cField = null;
        if (invariants[2] != null) {
            cField = getField(invariants[2].property);
        }
        if (aField == null || bField == null) {
            return false;
        }
        Integer a, b, c = null;
        try {
            a = (Integer) aField.get(graph);
            b = (Integer) bField.get(graph);
            if (cField != null) {
                c = (Integer) cField.get(graph);
            }
        } catch (IllegalAccessException e) {
            log.error("condition calc error: illegal access to field", e);
            return false;
        }
        if (a == null || b == null || (c == null && invariants[2] != null)) {
            setResult(false);
        } else {
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
                case MINUS_ONE:
                    setResult(a <= b - 1);
                    break;
                case PLUS_ONE:
                    setResult(a <= b + 1);
                    break;
                case DIVIDE_TWO:
                    setResult(a <= b / 2);
                    break;
                case MULT_TWO:
                    setResult(a <= b * 2);
                    break;
            }
        }
        return getResult();
    }

    @Override
    public String toString() {
        String expression = operation.pattern;
        expression = expression.replaceFirst("\\{\\}", invariants[1].property);
        if (invariants[2] != null) {
            expression = expression.replaceFirst("\\{\\}", invariants[2].property);
        }
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
