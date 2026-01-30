package org.java.appspring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ActiveProfiles("testcontainer")
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.28");

        public static void start() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of("spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword(),
                    "spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"
                    );
            }


        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            start();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testContainers =
                new MapPropertySource("test-containers", (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testContainers);
        }
    }

}
