import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import org.hamcrest.MatcherAssert;

public class HamcrestMatchersTest {
    @Test
    void test() {
        List<Integer> scores = Arrays.asList(99, 100, 101, 105);
        MatcherAssert.assertThat(scores, Matchers.hasSize(4));
        MatcherAssert.assertThat(scores, Matchers.hasItems(99, 100, 101));
        MatcherAssert.assertThat(scores, Matchers.everyItem(Matchers.lessThan(106)));
        MatcherAssert.assertThat(scores, Matchers.everyItem(Matchers.greaterThan(98)));

        MatcherAssert.assertThat(null, Matchers.emptyOrNullString());

        Integer[] scores2 = {99, 100, 101, 105};
        MatcherAssert.assertThat(scores2, Matchers.arrayWithSize(4));
    }
}
