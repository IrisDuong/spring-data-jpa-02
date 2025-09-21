package com.kps.jpa.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kps.jpa.company.entity.Employee;

public interface EmployeeRepository_01 extends JpaRepository<Employee, Integer>{

	@Modifying
	@Query(value = """
			UPDATE comp01_employee_01 e 
			SET e.emp_name = :emp.empName 
			, e.phone_no = :emp.phoneNo
			, e.email = :emp.email 
			, e.gender = :emp.gender 
			, e.dob = :emp.dob 
		WHERE e.id = :emp.id
		""", nativeQuery = true)
//	@Query("""
//			UPDATE Employee e 
//				SET e.empName = :#{#emp.empName} 
//				, e.phoneNo = :#{#emp.phoneNo}
//				, e.email = :#{#emp.email} 
//				, e.gender = :#{#emp.gender} 
//				, e.dob = :#{#emp.dob} 
//			WHERE e.id = :#{#emp.id}
//			""")
//	@Query("UPDATE Employee e SET e.empName = :#{#emp.empName} , e.phoneNo = :#{#emp.phoneNo} , e.email = :#{#emp.email} , e.gender = :#{#emp.gender} , e.dob = :#{#emp.dob} WHERE e.id = :#{#emp.id}")
	void updateEmployeeQuery(@Param("emp") Employee emp);
}
