import org.java.Order;
import org.java.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderServiceTest {
    OrderService orderService = new OrderService();
    private final UUID defaultUUID = UUID.fromString("03a9d7bc-1ab7-4b15-b144-951daf699fc2");
    private final LocalDateTime orderDate = LocalDateTime.now();

    @Test()
    void mocandoUUID() {
        try(MockedStatic<UUID> mockedUUID = Mockito.mockStatic(UUID.class)) {
            mockedUUID.when(UUID::randomUUID).thenReturn(defaultUUID);
            Order order = orderService.createOrder("tenis", 4L, null);
            Assertions.assertEquals(defaultUUID.toString(), order.getId());
        }
    }

    @Test
    void mockandoLocalDateTime() {
        try(MockedStatic<LocalDateTime> mockedTime = Mockito.mockStatic(LocalDateTime.class)) {
            mockedTime.when(LocalDateTime::now).thenReturn(orderDate);
            Order order = orderService.createOrder("tenis", 4L, null);
            Assertions.assertEquals(orderDate, order.getCreationDate());
        }
    }
}
