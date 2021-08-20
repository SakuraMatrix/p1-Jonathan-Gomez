package com.github.JonathanAGomez.MyBank.controller;

import com.github.JonathanAGomez.MyBank.domain.Customer;
import com.github.JonathanAGomez.MyBank.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(value = "/CustomerFormEntry")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService service){
        this.customerService = service;
    }

    @GetMapping("")
    public Flux<Customer> getAll(){
        return customerService.getAll();
    }

    @GetMapping("/{c_id}")
    public Mono<Customer> get(@PathVariable("c_id") int id){
        return customerService.get(id);
    }

    @PostMapping("")
    public Customer create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping("/{c_id} {c_name} {account_id}")
    public Customer insert(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    public Mono<ServerResponse> doThing(ServerRequest request){
        return request.bodyToMono(Customer.class)
                .flatMap(customer -> Mono.just(customerService.create(customer)))
                .flatMap(customer -> ServerResponse.created(URI.create("/customers/" +customer.getId()))
                .contentType(MediaType.TEXT_HTML)
                .body(BodyInserters.fromValue(customer)));
    }
}