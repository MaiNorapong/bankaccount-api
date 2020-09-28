package th.ac.ku.bankaccount.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.ac.ku.bankaccount.data.BankAccountRepository;
import th.ac.ku.bankaccount.model.BankAccount;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/bankaccount")
public class BankAccountRestController {

    private BankAccountRepository bankAccountRepository;

    public BankAccountRestController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping
    public List<BankAccount> getAll() {
        return bankAccountRepository.findAll();
    }

    @GetMapping("/{id}")
    public BankAccount getOne(@PathVariable int id) {
        try {
            return bankAccountRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
