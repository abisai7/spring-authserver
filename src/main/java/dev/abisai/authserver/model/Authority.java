package dev.abisai.authserver.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "authority_id_seq_gen")
    @GenericGenerator(
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "authority_id_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            },
            name = "authority_id_seq_gen"
    )
    protected Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}