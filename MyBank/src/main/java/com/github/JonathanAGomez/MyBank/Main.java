package com.github.JonathanAGomez.MyBank;

import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
    Path error = Paths.get(Main.class.getResource("/error.html").toURI());


    }
}
