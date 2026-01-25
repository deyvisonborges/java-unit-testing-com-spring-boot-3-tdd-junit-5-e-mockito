import org.java.Order;
import org.java.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.UUID;

public class OrderServiceTest {
    OrderService orderService = new OrderService();
    UUID defaultUUID = UUID.fromString("03a9d7bc-1ab7-4b15-b144-951daf699fc2");

    @Test()
    void deveIncluirUmRandomID() {
        try(MockedStatic<UUID> mockedUUID = Mockito.mockStatic(UUID.class)) {
            mockedUUID.when(UUID::randomUUID).thenReturn(defaultUUID);
            Order order = orderService.createOrder("tenis", 4L, null);
            Assertions.assertEquals(defaultUUID.toString(), order.getId());
        }
    }
}
