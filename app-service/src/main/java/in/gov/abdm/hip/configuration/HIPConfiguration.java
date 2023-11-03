package in.gov.abdm.hip.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.web.reactive.function.client.WebClient;

import static in.gov.abdm.hip.constant.HIPConstant.*;

@Configuration
public class HIPConfiguration {

    private final String redisServer;
    private final int redisPort;
    private final String redisPassword;
    private final int redisDatabase;

    public HIPConfiguration(@Value(REDIS_SERVER) String redisServer,
                            @Value(REDIS_PORT) Integer redisPort,
                            @Value(REDIS_PASSWORD) String redisPassword,
                            @Value(REDIS_DATABASE) Integer redisDatabase) {

        this.redisServer = redisServer;
        this.redisPort = redisPort;
        this.redisDatabase = redisDatabase;
        this.redisPassword = redisPassword;
    }

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().build();
    }

    @Bean
    public LettuceConnectionFactory redisStandAloneConnectionFactory() {
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisServer);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);
        redisStandaloneConfiguration.setDatabase(redisDatabase);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisStandAloneConnectionFactory());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
