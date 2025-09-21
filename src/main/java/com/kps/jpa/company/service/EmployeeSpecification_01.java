package com.kps.jpa.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.kps.jpa.company.entity.Address;
import com.kps.jpa.company.entity.Employee;
import com.kps.jpa.company.entity.Salary;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeSpecification_01 implements Specification<Employee>{

	private String searchTerm;
	private Date dob;
	private Long salaryTo;
	private Long salaryFrom;
	private String address;
	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		Predicate dobPred = Optional.ofNullable(dob)
				.map(d-> cb.greaterThanOrEqualTo(root.get("dob"), d))
				.orElse(null);
		
		Predicate salaryPred = salaryPred(root, cb);
		Predicate addressPred = addressPred(root, cb);
		
//		if(salaryPred != null) query.distinct(true);
		query.distinct(true);
		
		Predicate empNamePred = null;
		if(StringUtils.hasText(searchTerm)) {
			empNamePred = like(cb, root.get("empName"), searchTerm);
			
		}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		Optional.ofNullable(empNamePred).ifPresent(predicates::add);
		Optional.ofNullable(dobPred).ifPresent(predicates::add);
		Optional.ofNullable(salaryPred).ifPresent(predicates::add);
		Optional.ofNullable(addressPred).ifPresent(predicates::add);
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
	
	private Predicate between(CriteriaBuilder cb, Path<Integer> field, int min, int max) {
		return cb.between(field, min, max);
	}
	
	private Predicate like(CriteriaBuilder cb, Path<String> field, String searchTerm) {
		return cb.like(field, "%"+searchTerm.trim().toLowerCase()+"%");
	}
	
	private Predicate equals(CriteriaBuilder cb, Path<Object> field, Object value) {
		return cb.equal(field, value);
	}
	
	private Predicate salaryPred(Root<Employee> root, CriteriaBuilder cb) {

		Join<Employee, Salary> salaryJoin = root.join("salaries", JoinType.INNER);
		return cb.and(
					cb.greaterThanOrEqualTo(salaryJoin.get("salary"), salaryFrom),
					cb.lessThanOrEqualTo(salaryJoin.get("salary"), salaryTo)
				);
		
	}
	
	private Predicate addressPred(Root<Employee> root, CriteriaBuilder cb) {
		Join<Employee, Address> addressJoin = root.join("addresses", JoinType.INNER);
		return cb.like(addressJoin.get("address"), "%"+ address+"%");
	}
}
