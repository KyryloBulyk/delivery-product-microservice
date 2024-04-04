package kyrylo.delivery.com.deliveryproductmicroservice.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class UserClientConfiguration {

    private static final String INTERNAL_SECRET_HEADER = "X-Internal-Secret";
    private static final String INTERNAL_SECRET_VALUE = "expected-value";

    @Bean
    public RequestInterceptor internalSecretInterceptor() {
        return (RequestTemplate template) -> template.header(INTERNAL_SECRET_HEADER, INTERNAL_SECRET_VALUE);
    }
}
