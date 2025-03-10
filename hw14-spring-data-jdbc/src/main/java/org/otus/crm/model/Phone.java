package org.otus.crm.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("phone")
public class Phone {
    @Id
    private Integer id;

    private String number;

    private String clientId;

    public Phone(String number, String clientId) {
        this(null, number, clientId);
    }

    @PersistenceCreator
    public Phone(Integer id, String number, String clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }
}
