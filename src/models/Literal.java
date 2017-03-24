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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Literal))
            return false;

        //Check if name and negation are the same
        if(this.name == ((Literal) obj).name && this.negation == ((Literal) obj).negation)
            return false;

        return true;
    }
}
