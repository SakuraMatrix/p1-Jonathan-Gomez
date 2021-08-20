package com.github.JonathanAGomez.MyBank;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.JonathanAGomez.MyBank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan
public class AppConfig {
    @Autowired
    CustomerService customerService;

    @Bean
    public CqlSession session(){
        return CqlSession.builder().build();
    }

    @Bean
    public DisposableServer server() throws URISyntaxException {
        Path error = Paths.get(App.class.getResource("/error.html").toURI());
        Path index = Paths.get(App.class.getResource("/index.html").toURI());
        return HttpServer.create()
                .port(8080)
                .route(routes ->
                        routes.get("/CustomerFormEntry", (request, response) ->
                                        response.send(customerService.getAll()
                                                .map(App::toByteBuf)
                                                .log("http-server")))
                                //Posts
                                .post("/CustomerFormEntry", (request, response) ->
                                        response.send(request.receive().asString()
                                                .map(App::parseCustomer)
                                                .map(customerService::create)
                                                .map(App::toByteBuf)
                                                .log("http-server")))

                                //Gets
                                .get("/CustomerFormEntry/{param}", (request,response) ->
                                        response.send(customerService.get(Integer.parseInt(request.param("param")))
                                                .map(App::toByteBuf)
                                                .log("http-server")))
                                .get("/CustomerFormEntry/{param}", (request,response) ->
                                        response.sendString(Mono.just(request.param("param"))
                                                .log("http-server")))


                                .get("/", (request, response) ->
                                        response.sendFile(index))
                                .get("/error", (request, response) ->
                                        response.status(404).addHeader("Output", "Error")
                                                .sendFile(error))
                )
                .bindNow();
    }
}