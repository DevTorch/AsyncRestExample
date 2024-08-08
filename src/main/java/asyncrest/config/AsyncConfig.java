package asyncrest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        return new ThreadPoolTaskExecutor() {{
            setCorePoolSize(5);
            setMaxPoolSize(5);
            setQueueCapacity(200);
            setThreadNamePrefix("AsyncThread-");
            initialize();
            log.info("Executor initialized");
        }};
    }
}
