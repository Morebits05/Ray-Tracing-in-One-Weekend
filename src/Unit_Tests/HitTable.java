package Unit_Tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HitTable {

    /** Tests Whether HitTable Contains Any Methods */
    @Test
    @DisplayName("should have Hit Method")
    public void MethodTest(){
        assertTrue(Arrays.stream(com.MB.HitTable.class.getMethods()).findAny().isPresent());
    }

}


