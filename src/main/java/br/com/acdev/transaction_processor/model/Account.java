package br.com.acdev.transaction_processor.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "document_number", unique = true)
    private String documentNumber;

    @ToString.Exclude
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Transaction> transactions;

}
