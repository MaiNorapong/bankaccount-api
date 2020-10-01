package th.ac.ku.bankaccount.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import th.ac.ku.bankaccount.data.BankAccountRepository;
import th.ac.ku.bankaccount.model.BankAccount;
import th.ac.ku.bankaccount.model.Money;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/bankaccount")
public class BankAccountRestController {

    private BankAccountRepository repository;

    public BankAccountRestController(BankAccountRepository bankAccountRepository) {
        this.repository = bankAccountRepository;
    }

    @GetMapping("/customer/{customerId}")
    public List<BankAccount> getAllCustomerId(@PathVariable int customerId) {
        return repository.findByCustomerId(customerId);
    }

    @GetMapping
    public List<BankAccount> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public BankAccount getOne(@PathVariable int id) {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @PostMapping
    public BankAccount create(@RequestBody BankAccount bankAccount) {
        repository.save(bankAccount);
        return repository.findById(bankAccount.getId()).get();
    }

    @PutMapping("/{id}")
    public BankAccount update(@PathVariable int id,
                              @RequestBody BankAccount bankAccount) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Cannot edit balance directly"
        );
//        BankAccount record = repository.findById(id).get();
//        record.setBalance(bankAccount.getBalance());
//        repository.save(record);
//        return record;
    }

    @DeleteMapping("/{id}")
    public BankAccount delete(@PathVariable int id) {
        BankAccount record = repository.findById(id).get();
        repository.deleteById(id);
        return record;
    }

    @PostMapping("/deposit/{id}")
    public BankAccount deposit(@PathVariable int id,
                               @RequestBody Money money) {
        System.out.println(money);
        BankAccount bankAccount = repository.findById(id).get();
        bankAccount.deposit(money.getAmount());
        repository.save(bankAccount);
        return bankAccount;
    }

    @PostMapping("/withdraw/{id}")
    public BankAccount withdraw(@PathVariable int id,
                                @RequestBody Money money) {
        BankAccount bankAccount = repository.findById(id).get();
        try {
            bankAccount.withdraw(money.getAmount());
            repository.save(bankAccount);
            return bankAccount;
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Not enough money in account", e);
        }
    }

}
