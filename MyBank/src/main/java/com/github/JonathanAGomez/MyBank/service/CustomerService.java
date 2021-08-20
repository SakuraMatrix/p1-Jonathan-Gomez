package com.github.JonathanAGomez.MyBank.service;

import com.github.JonathanAGomez.MyBank.domain.Customer;
import com.github.JonathanAGomez.MyBank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    private CustomerRepository customerRepo;

    public CustomerService(CustomerRepository customerRepository) {this.customerRepo = customerRepository;}

    public Flux<Customer> getAll() {
        return customerRepo.getAll();
    }

    public Mono<Customer> get(int id) {
        return customerRepo.get(id);
    }

    public Customer create(Customer customer){
        return customerRepo.create(customer);
    }
}