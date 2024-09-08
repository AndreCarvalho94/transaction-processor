package br.com.acdev.transaction_processor.repository;

import br.com.acdev.transaction_processor.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
