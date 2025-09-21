package com.kps.jpa.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kps.jpa.company.entity.Employee;

public interface EmployeeRepositorySpec_01 extends  JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee>{

}
