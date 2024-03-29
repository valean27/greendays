package com.greendays.greendays.model.dal;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity(name = "client")
@Table(name = "client")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_type", nullable = false)
    private String clientType;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientType='" + clientType + '\'' +
                '}';
    }
}
