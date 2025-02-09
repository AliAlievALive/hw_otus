package org.otus.crm.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "address_id")
    private Address address;

    @EqualsAndHashCode.Exclude
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "client")
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this(id, name);
        this.address = address;
        this.phones = phones;
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        var phoneListCloned = this.phones.stream().map(Phone::clone).toList();
        return new Client(this.id, this.name, this.address == null ? null : this.address.clone(), phoneListCloned);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
