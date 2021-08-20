package com.github.JonathanAGomez.MyBank.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.JonathanAGomez.MyBank.domain.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepository {

    private CqlSession session;

    public CustomerRepository(CqlSession session){ this.session = session; }

    public Flux<Customer> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM mybank.customers"))
                .map(row -> new Customer(row.getInt("c_id"), row.getString("c_name"), row.getInt("account_id"))
        );
    }

    public Mono<Customer> get(String id){
        return Mono.from(session.executeReactive("SELECT * FROM mybank.customers WHERE c_id = " +id))
                .map(row -> new Customer(row.getInt("c_id"), row.getString("c_name"), row.getInt("account_id"))
                );
    }

    public Mono<Customer> get(int id) {
        return Mono.from(session.executeReactive("SELECT * FROM mybank.customers WHERE c_id = " +id))
                .map(row -> new Customer(row.getInt("c_id"), row.getString("c_name"), row.getInt("account_id"))
        );
    }

    public Customer create(Customer customer){
        SimpleStatement statement = SimpleStatement.builder("INSERT INTO mybank.customers (c_id, c_name, account_id) values (?, ?, ?)")
                .addPositionalValues(customer.getId(), customer.getName(), customer.getAccount_id())
                .build();
        Flux.from(session.executeReactive(statement)).subscribe();
        return customer;
    }


    public Mono<Customer> delete(Customer customer){
        SimpleStatement statement = SimpleStatement.builder("DELETE FROM mybank.customers where c_id = ?")
                .addPositionalValue(customer.getId())
                .build();
        Flux.from(session.executeReactive(statement)).subscribe();
        return Mono.just(customer);
    }
}