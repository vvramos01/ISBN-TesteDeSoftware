import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestValidador {

    @Test
    public void testScenario01(){
        assertTrue(Validador.validarISBN13("9780306406157"));
        assertTrue(Validador.validarISBN13("978-0-306-40615-7"));
    }
}
