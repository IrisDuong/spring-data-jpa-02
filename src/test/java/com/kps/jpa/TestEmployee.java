package com.kps.jpa;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kps.jpa.company.AddressService;
import com.kps.jpa.company.entity.Address;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.company.service.EmployeeService_01;
import com.kps.jpa.exception.CustomSQLException;

@SpringBootTest
public class TestEmployee {

	@Autowired
	EmployeeService_01 employeeService;
	
	@Autowired
	AddressService addressService;
	
//	@Test
//	public void testSaveEmployee() {
////		Employee employee = null;
//		Employee employee = new Employee("Le Thi Ngoc Dung");
//		Address  address = new Address("Can Tho");
//		employeeService.addEmployee(employee,address);
//		
//		List<Employee> gettedEmployee = employeeService.getAllEmps();
//		List<Address> gettedAddresses = addressService.getAllAddress();
//		
//		gettedAddresses.forEach(a-> System.out.println(a.toString()));
//		System.out.println("----------------------------------------------");
//		gettedEmployee.forEach(e-> System.out.println(e.toString()));
//	}
	
//	@Test
//	public void testUpdateAndReadEmployee() throws InterruptedException {
//		Integer id = 6;
//		Thread t1 = new Thread(()->{
//			employeeService.updateEmployee(id, new Employee("Nguyen Van A"));
////			employeeService.updateEmployee(id, new Employee("Nguyen Minh Khang"));
//			try {
//				TimeUnit.SECONDS.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		
//		Thread t2 = new Thread(()->{
//			Employee emp = employeeService.getEmployeeById(id);
//			System.out.println(".......... junit test ::  emp ...............");
//			System.out.println(emp.toString());	
//			try {
//				TimeUnit.SECONDS.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//		
//		t1.start();
//		t2.start();
//		
//		t1.join();
//		t2.join();
//	}
}
