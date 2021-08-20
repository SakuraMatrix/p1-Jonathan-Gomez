package com.github.JonathanAGomez.MyBank.domain;

public class Customer {

    private int id;
    private String name;
    private int account_id;

    public Customer(){

    }

    public Customer(int id, String name, int account_id){
        this.id = id;
        this.name = name;
        this.account_id = account_id;
    }

    public int getId(){ return id; }
    public void setId(int id) {this.id = id;}

    public String getName(){ return name; }
    public void setName(String name) {this.name = name;}

    public int getAccount_id(){ return account_id; }
    public void setAccount_id(int id) {this.account_id = account_id;}
}