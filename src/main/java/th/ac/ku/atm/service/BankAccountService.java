package th.ac.ku.atm.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {
    private RestTemplate restTemplate;

    private List<BankAccount> bankAccountList ;

    public BankAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void postConstruct() {
        this.bankAccountList = new ArrayList<>();
    }

    public List<BankAccount> getBankAccount() {
        return new ArrayList<>(this.bankAccountList) ;
    }

    public List<BankAccount> getCustomerBankAccount(int customerId) {
        String url = "http://localhost:8091/api/bankaccount/customer/" +
                customerId;
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }


    public void createBankAccount(BankAccount bankAccount) {
        bankAccountList.add(bankAccount) ;
    }

    public BankAccount findBankAccount(int id) {
        for (BankAccount bankAccount : bankAccountList) {
            if (bankAccount.getId() == id) {
                return bankAccount ;
            }
        } return null ;
    }

    public BankAccount checkId(BankAccount inputBankAccount) {
        BankAccount storedBankAccount = findBankAccount(inputBankAccount.getId()) ;

        if (storedBankAccount != null) {
            return storedBankAccount ;
        } return null ;
    }
}