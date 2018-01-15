package io.felipepoliveira.senaikeep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {PersistenceConfig.class, WebConfig.class})
public class AppConfig {

}
