package com.kps.jpa.banking;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/bankController")
public class BankController {
	@Autowired
	BankAccountService bankAccountService;

	@GetMapping("/initBankData")
	public ResponseEntity<?>  initBankData() {
		HttpStatus httpStatus;
		StringBuilder message = new  StringBuilder();
		try {
			bankAccountService.initBankData();
			message.append("Innit Banking data successfully");
			httpStatus = HttpStatus.OK;
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
			message.append("Innit Banking data failed");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(message, httpStatus);
	}

	@GetMapping("/bankTransferring")
	public ResponseEntity<?> bankTransferring(
			@RequestParam Long srcBankId, @RequestParam Long descBankId, @RequestParam BigDecimal amount 
	) throws Exception{
		HttpStatus httpStatus;
		StringBuilder message = new  StringBuilder();
		try {
//			message.append(bankAccountService.tranfering(srcBankId, descBankId, amount));
			httpStatus = HttpStatus.OK;
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
			message.append("Transaction of banking transferrign failed");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(message, httpStatus);
	}
	
	@GetMapping("/demoIsolation")
	public ResponseEntity<?> demoIsolation(
			@RequestParam Long srcBankId
			,@RequestParam Long descBankId
			,@RequestParam BigDecimal transferAmount
			,@RequestParam BigDecimal withdrawAmount 
	) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();

				Thread withDraw = new Thread(()->{
					try {
						TimeUnit.SECONDS.sleep(3);
						BankAccount bankAccount = bankAccountService.withDraw(srcBankId, withdrawAmount);
						System.out.println("......................WITHDRAW result ......................");
						System.out.println(bankAccount.toString());
						if(!ObjectUtils.isEmpty(bankAccount)) {
							data.put("bankAccount",bankAccount);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						data.put("bankAccountInfoMsg",e.getMessage());
					}
				});
			Thread tranfering = new Thread(()->{
				try {
					Map<String, Object> result = bankAccountService.tranfering(srcBankId, descBankId, transferAmount);
					TimeUnit.SECONDS.sleep(2);
					System.out.println("......................TRANSFERRING result ......................");
					for(Entry<String, Object> entry: result.entrySet()) {
						System.out.println(entry.getKey());
						System.out.println(entry.getValue());
						System.out.println("...........................................................................");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			tranfering.start();
			withDraw.start();
			try {

				tranfering.join();
				withDraw.join();
			} catch (Exception e2) {
				// TODO: handle exception
			}
	
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	@PostMapping("/customSaveBanking")
	public ResponseEntity<?> customSaveBanking(
			@RequestBody BankAccount bankAccount
	) throws Exception{
		HttpStatus httpStatus;
		StringBuilder message = new  StringBuilder();
		try {
			int result = bankAccountService.customSaveBanking(bankAccount);
			message.append("result = "+result);
			httpStatus = HttpStatus.OK;
		} catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
			message.append("Transaction of banking transferrign failed");
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(message, httpStatus);
	}
	
}
