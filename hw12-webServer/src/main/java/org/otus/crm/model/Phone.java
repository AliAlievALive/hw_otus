package org.otus.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Integer id, String number) {
        this.id = id;
        this.number = number;
    }
}
