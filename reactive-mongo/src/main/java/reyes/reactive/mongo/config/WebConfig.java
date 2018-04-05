package reyes.reactive.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfig {

    @Autowired
    private PersonHandler personHandler;

    @Bean
    public RouterFunction<?> routerFunction(){
        return route(GET("/persons"), personHandler::all)
                .and(route(GET("/persons/{firstName}"), personHandler::byFirstName));
    }
}
