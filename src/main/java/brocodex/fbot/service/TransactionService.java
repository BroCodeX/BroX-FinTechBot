package brocodex.fbot.service;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.repository.transactions.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;

    public List<TransactionDTO> getAllTransactions(int limit) {
        return null;
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        return null;
    }

    public TransactionDTO showTransaction(Long id) {
        return null;
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO dto) {
        return null;
    }

    public void destroyTransaction(Long id) {

    }
}
