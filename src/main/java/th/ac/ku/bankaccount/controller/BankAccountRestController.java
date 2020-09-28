package th.ac.ku.bankaccount.controller;

import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public BankAccount create(@RequestBody BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
        return bankAccountRepository.findById(bankAccount.getId()).get();
    }

    @PutMapping("/{id}")
    public BankAccount update(@PathVariable int id,
                              @RequestBody BankAccount bankAccount) {
        BankAccount record = bankAccountRepository.findById(id).get();
        record.setBalance(bankAccount.getBalance());
        bankAccountRepository.save(record);
        return record;
    }
}
