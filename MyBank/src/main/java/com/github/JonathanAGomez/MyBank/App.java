package com.github.JonathanAGomez.MyBank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.JonathanAGomez.MyBank.domain.Customer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.DisposableServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws URISyntaxException {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        appContext.getBean(DisposableServer.class).onDispose().block();
    }

    static ByteBuf toByteBuf(Object o){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            OBJECT_MAPPER.writeValue(out, o);
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
    }

    static Customer parseCustomer(String str){
        Customer customer = null;
        try{ customer = OBJECT_MAPPER.readValue(str, Customer.class);
        } catch(JsonProcessingException ex){ }
        return customer;
    }
}