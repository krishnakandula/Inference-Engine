package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 3/24/2017.
 */
public class Clause {
    private List<Literal> literals;
    private List<Integer> combinedClauses;
    private int number;

    public Clause(List<Literal> literals, int number){
        this.literals = literals;
        combinedClauses = new ArrayList<>(2);
        this.number = number;
    }

    public Clause(List<Literal> literals){
        this.literals = literals;
        combinedClauses = new ArrayList<>(2);
    }

    /**
     * Copy constructor
     * @param clause the clause to be copied
     */
    public Clause(Clause clause){
        this.literals = new ArrayList<>(clause.getLiterals());
        this.combinedClauses = new ArrayList<>();
        this.number = clause.getNumber();
    }

    public List<Literal> getLiterals() {
        return literals;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Integer> getCombinedClauses() {
        return combinedClauses;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(number + ".  ");
        for(Literal l : literals)
            builder.append(l + " ");
        builder.append(" {");
        for(Integer i : combinedClauses)
            builder.append(i + " ");
        builder.append("}");

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Clause))
            return false;

        if(this.getLiterals().size() != ((Clause) obj).getLiterals().size())
            return false;

        //Check all literals. If even one is different, return true
        for(Literal l : literals){
            if(!((Clause) obj).literals.contains(l))
                return false;
        }

        return true;
    }
}
