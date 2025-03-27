package doctorhoai.learn.apigateway;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@SpringBootApplication
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            System.out.println("Request Path: " + exchange.getRequest().getPath());
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        System.out.println("Response Status Code: " + exchange.getResponse().getStatusCode());
                    }));
        };
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
//                .route(
//                        p -> p
//                                .path("/doctorhoai/user/**")
//                                .filters(
//                                        f -> f
//                                                .rewritePath("/doctorhoai/user/(?<segment>.*)", "/${segment}")
//                                                .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
//                                                .circuitBreaker(
//                                                        config ->config.setName("userSupport")
//                                                                .setFallbackUri("forward:/userSupport")
//                                                )
//                                )
//                                .uri("lb://USERSERVICE")
//                )
//                .route(
//                        p -> p
//                                .path("/doctorhoai/rate/**")
//                                .filters(
//                                        f -> f
//                                                .rewritePath("/doctorhoai/rate/(?<segment>.*)", "/${segment}")
//                                                .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
//                                                .circuitBreaker(
//                                                        config ->config.setName("rateSupport")
//                                                                .setFallbackUri("forward:/rateSupport")
//                                                )
//                                )
//                                .uri("lb://RATESERVICE")
//                )
//                .route(
//                        p -> p
//                                .path("/doctorhoai/film/**")
//                                .filters(
//                                        f -> f
//                                                .rewritePath("/doctorhoai/film/(?<segment>.*)", "/${segment}")
//                                                .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
//                                                .circuitBreaker(
//                                                        config ->config.setName("filmSupport")
//                                                                .setFallbackUri("forward:/filmSupport")
//                                                )
//                                )
//                                .uri("lb://FILMSERVICE")
//                )
//                .route(
//                        p -> p
//                                .path("/doctorhoai/dish/**")
//                                .filters(
//                                        f -> f
//                                                .rewritePath("/doctorhoai/dish/(?<segment>.*)", "/${segment}")
//                                                .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
//                                                .circuitBreaker(
//                                                        config ->config.setName("dishSupport")
//                                                                .setFallbackUri("forward:/dishSupport")
//                                                )
//                                )
//                                .uri("lb://DISHSERVICE")
//                )
                .route(
                        p -> p
                                .path("/doctorhoai/proxy/**")
                                .filters(
                                        f -> f
                                                .rewritePath("/doctorhoai/proxy/(?<segment>.*)", "/${segment}")
                                                .addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
                                                .circuitBreaker(
                                                        config ->config.setName("proxySupport")
                                                                .setFallbackUri("forward:/proxySupport")
                                                )
                                                .preserveHostHeader()
                                )
                                .uri("lb://PROXYCLIENT")
                )
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(5))
                        .cancelRunningFuture(false)
                        .build())
                .build());
    }
}
