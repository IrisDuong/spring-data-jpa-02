package com.kps.jpa.inheritance.contract;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractService {

	@Autowired
	ContractDao contractDao;
	
	public Contract saveContract(Contract contract) {
		return contractDao.save(contract);
	}
	
	public Optional<Contract> getContractById(long id) {
		return contractDao.findById(id);
	}
	
	public List<Contract> getAllContracts(){
		try {

			Contract findedContract = contractDao.findById(5l).get();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		List<Contract> result = contractDao.findAll();
		result.forEach(contract-> System.out.println(contract.toString()));
		
//		Contract findedContract2 = contractDao.findById(1l).get();
//		findedContract2.setCustomerName("BiBo");
//		contractDao.save(findedContract2);
		
		return result;
	}
}
