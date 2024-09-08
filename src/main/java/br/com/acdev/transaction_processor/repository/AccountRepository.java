package br.com.acdev.transaction_processor.repository;

import br.com.acdev.transaction_processor.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
