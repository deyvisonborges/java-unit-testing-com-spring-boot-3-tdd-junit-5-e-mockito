import org.java.CheckoutService;
import org.java.PaymentProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class CheckoutServiceTest {

    @Test
    void testMockObjectConstruction() {
        // A sintaxe correta passa a classe e depois o lambda (mock, context)
        try (MockedConstruction<PaymentProcessor> mocked = Mockito.mockConstruction(
                PaymentProcessor.class,
                (mock, context) -> {
                    // O 'context' representa o contexto da construção (argumentos do construtor, etc)
                    Mockito.when(mock.chargeCustomer(anyString(), any(BigDecimal.class)))
                            .thenReturn(BigDecimal.TEN);
                }
        )) {
            CheckoutService checkoutService = new CheckoutService();
            BigDecimal result = checkoutService.purchase("Macbook", "42");

            Assertions.assertEquals(BigDecimal.TEN, result);
            Assertions.assertEquals(1, mocked.constructed().size());
        }
    }
}