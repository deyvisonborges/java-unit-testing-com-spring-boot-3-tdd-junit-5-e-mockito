import org.java.MyUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class MyUtilsTest {
    @Test()
    void mockandoMetodoEstaticoQueRecebeParamentros() {
        try (MockedStatic<MyUtils> mockedStatic = Mockito.mockStatic(MyUtils.class)) {
            mockedStatic.when(() -> MyUtils.getWelcomeMessage(
                    ArgumentMatchers.eq("Deyv"),
                    ArgumentMatchers.anyBoolean()
            )).thenReturn("Hello Deyv");

            String result = MyUtils.getWelcomeMessage("Deyv", false);
            Assertions.assertEquals("Hello Deyv", result);
        }
    }
}
