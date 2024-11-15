package org.otus.homework;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final NavigableMap<Customer, String> customers = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        return this.copy(customers.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> e = customers.higherEntry(customer);
        return this.copy(e);
    }

    public void add(Customer customer, String data) {
        Customer newCustomer = new Customer(customer.getId(), customer.getName(), customer.getScores());
        customers.put(newCustomer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> e) {
        return e == null ? null : Map.entry(new Customer(e.getKey()), e.getValue());
    }
}
