package org.otus.crm.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "address")
public class Address {
    @Id
    private Integer id;

    private String street;

    private Long clientId;

    @Override
    public String toString() {
        return "Address{" + "street='" + street + '\'' + '}';
    }

    @PersistenceCreator
    public Address(Integer id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }
}
