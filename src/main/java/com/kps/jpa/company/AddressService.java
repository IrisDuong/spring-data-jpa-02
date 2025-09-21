package com.kps.jpa.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.kps.jpa.company.dao.AddressRepository;
import com.kps.jpa.company.entity.Address;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	public Address addAddress(Address address) {
		Address newAddress = addressRepository.save(address);
		return newAddress;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public List<Address> getAllAddress() {
		return addressRepository.findAll();
	}
}
