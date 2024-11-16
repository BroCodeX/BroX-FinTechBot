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
        String type = dto.getType();

        if (type.equals("income")) {
            return null;
        } else if (type.equals("expense")) {
            return null;
        } else {
            return null;
        }
    }

    public TransactionDTO showTransaction(Long id) {
        return null;
    }

    public void destroyTransaction(Long id) {

    }
}
