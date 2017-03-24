package models;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class Literal {
    private String name;
    private boolean negation;

    public Literal(String name, boolean negation){
        this.name = name;
        this.negation = negation;
    }

    public String getName() {
        return name;
    }

    public boolean isNegated() {
        return negation;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(isNegated())
            builder.append("~");
        builder.append(name);

        return builder.toString();
    }
}
