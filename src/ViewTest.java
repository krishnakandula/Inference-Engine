import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Krishna Chaitanya Kandula on 3/25/2017.
 */
class ViewTest {
    @Test
    void checkResolvedClauseCombinations() {
        View.resolvedClauseCombinations = new HashMap<>();

        //Add some values to map
        View.resolvedClauseCombinations.put(1, 5);
        View.resolvedClauseCombinations.put(3, 5);
        View.resolvedClauseCombinations.put(4, 7);
        View.resolvedClauseCombinations.put(2, 10);
        View.resolvedClauseCombinations.put(12, 3);
        View.resolvedClauseCombinations.put(6, 20);

        assertTrue(View.checkResolvedClauseCombinations(20, 6));
        assertTrue(View.checkResolvedClauseCombinations(2, 10));
        assertTrue(View.checkResolvedClauseCombinations(5, 3));
    }

}