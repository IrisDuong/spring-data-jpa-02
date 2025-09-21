package com.kps.jpa.banking;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InsufficientResourcesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.kps.jpa.exception.CustomSQLException;

@Service
public class BankAccountService {

	@Autowired
	BankAccountRepository bankAccountRepository;
	
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
	public void initBankData() {
		BankAccount src = BankAccount.builder()
				.name("Nguyen Khac Manh")
				.address("KP 11")
				.phoneNo("0784240253")
				.balance(new BigDecimal(100000000))
				.build();

		BankAccount des = BankAccount.builder()
				.name("Tran Ngoc Chau Bang")
				.address("KP An Hoa 3")
				.phoneNo("0903660626")
				.balance(new BigDecimal(24000000))
				.build();
		bankAccountRepository.save(des);
		bankAccountRepository.save(src);
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> tranfering(Long srcBankId, Long descBankId, BigDecimal amount) throws Exception {
		BankAccount descBankAccount = bankAccountRepository.findById(descBankId)
				.orElseThrow(()-> new Exception("Bank account was not found"));
		BankAccount srcBankAccount = bankAccountRepository.findById(srcBankId)
				.orElseThrow(()-> new Exception("Bank account was not found"));
		
		if(srcBankAccount.getBalance().compareTo(amount) < 0) {
			throw new InsufficientResourcesException("Insufficient funds in the account");
		}

		descBankAccount.setBalance(descBankAccount.getBalance().add(amount));
		srcBankAccount.setBalance(srcBankAccount.getBalance().subtract(amount));
		srcBankAccount =  bankAccountRepository.save(srcBankAccount);
		descBankAccount = bankAccountRepository.save(descBankAccount);
//		message = "Transfer "+amount+" from account "+srcBankAccount.getName()+" to account "+descBankAccount.getName()+" successfully";
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("srcBankAccount", srcBankAccount);
		data.put("descBankAccount", descBankAccount);
		return data;
	}
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public BankAccount checkBanlance(Long srcBankId) throws Exception {
		BankAccount bankAccount = bankAccountRepository.findById(srcBankId)
				.orElseThrow(()-> new Exception("Bank account was not found"));
		return bankAccount;
	}
	

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public BankAccount withDraw(Long srcBankId, BigDecimal amount) throws Exception {
		BankAccount bankAccount = bankAccountRepository.findById(srcBankId)
				.orElseThrow(()-> new Exception("Bank account was not found"));
		bankAccount.setBalance(bankAccount.getBalance().subtract(amount));
		bankAccount = bankAccountRepository.save(bankAccount);
		return bankAccount;
	}
	
	@Transactional(rollbackFor = CustomSQLException.class)
	public int customSaveBanking(BankAccount account) {
		return bankAccountRepository.customSaveBanking(account.getBalance(), account.getAddress(),account.getName(),account.getPhoneNo());
	}
}
