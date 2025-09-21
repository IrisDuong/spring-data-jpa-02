package com.kps.jpa.company.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.kps.jpa.company.dto.EmployeeRequestDTO;
import com.kps.jpa.company.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class EmployeeRepository_02 {

	@PersistenceContext
	EntityManager entityManager;
	
	public Map<String, Object> search01(int pageNo, int pageSize, EmployeeRequestDTO search,String... sortBy){
//		StringBuilder selectSql = new StringBuilder("""
//				SELECT DISTINCT new com.kps.jpa.employee.EmployeeDTO(emp.id,emp.empName,emp.phoneNo,emp.email,emp.gender,emp.dob)
//				FROM Employee emp 
//				INNER JOIN Salary s ON s.employee.id = emp.id 
//				WHERE 1=1 
//				""");
		StringBuilder selectSql = new StringBuilder("""
				SELECT DISTINCT emp
				FROM Employee emp 
				INNER JOIN Salary s ON s.employee.id = emp.id 
				WHERE 1=1 
				""");
		StringBuilder countSql = new StringBuilder("""
				SELECT  count(DISTINCT emp.id,emp.empName,emp.phoneNo,emp.email,emp.gender,emp.createdBy,emp.updatedBy,emp.createdAt,emp.updatedAt,emp.dob,s.id )
				FROM Employee emp INNER JOIN Salary s ON s.employee.id = emp.id WHERE 1=1 
				""");
//		StringBuilder countSql = new StringBuilder("""
//				SELECT  count(*)
//				FROM Employee emp INNER JOIN Salary s ON s.employee.id = emp.id WHERE 1=1 
//				""");
		if(Optional.ofNullable(search).isPresent()) {
			selectSql.append("OR LOWER(emp.empName) LIKE LOWER(:empName) ");
			selectSql.append("OR LOWER(emp.phoneNo) LIKE LOWER(:phoneNo) ");
			selectSql.append("OR LOWER(emp.email) LIKE LOWER(:email) ");
			selectSql.append("OR s.salary BETWEEN :salaryFrom AND :salaryTo ");
			
			StringJoiner orders = new StringJoiner(",");
			Stream.of(sortBy)
					.forEach(s->{
						Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
						Matcher matcher = pattern.matcher(s);
						if(matcher.find()) {
							if(matcher.group(3).trim().toLowerCase().equalsIgnoreCase("asc")) {
								orders.add(String.format("emp.%s ASC", matcher.group(1)));
							}else {
								orders.add(String.format("emp.%s DESC", matcher.group(1)));
							}
						}
					});
			selectSql.append(" ORDER BY "+orders.toString());
			// count

			countSql.append("OR LOWER(emp.empName) LIKE LOWER(:empName) ");
			countSql.append("OR LOWER(emp.phoneNo) LIKE LOWER(:phoneNo) ");
			countSql.append("OR LOWER(emp.email) LIKE LOWER(:email) ");
			countSql.append("OR s.salary BETWEEN :salaryFrom AND :salaryTo ");
			
		}
		Query selectQuery = entityManager.createQuery(selectSql.toString());
		selectQuery.setParameter("empName",  "%"+search.getEmpName()+"%");
		selectQuery.setParameter("phoneNo",  "%"+search.getPhoneNo()+"%");
		selectQuery.setParameter("email", "%"+search.getEmail()+"%");
		selectQuery.setParameter("salaryFrom", search.getSalaryFrom());
		selectQuery.setParameter("salaryTo", search.getSalaryTo());
		
		/**
		 * pagination
		 */
		int offset = pageNo * pageSize;
		selectQuery.setFirstResult(offset);
		selectQuery.setMaxResults(pageSize);
		
		/**
		 * count total elements
		 */
		Query countQuery = entityManager.createQuery(countSql.toString());
		countQuery.setParameter("empName",  "%"+search.getEmpName()+"%");
		countQuery.setParameter("phoneNo",  "%"+search.getPhoneNo()+"%");
		countQuery.setParameter("email", "%"+search.getEmail()+"%");
		countQuery.setParameter("salaryFrom", search.getSalaryFrom());
		countQuery.setParameter("salaryTo", search.getSalaryTo());
		List<Employee> result = selectQuery.getResultList();
		Long totalItems = (Long) countQuery.getSingleResult();
		
		/**
		 * total page
		 */
		
//		Page<?> page = new PageImpl<Employee>(result,  PageRequest.of(pageNo, pageSize), totalItems);
		
		Page<?> page = new PageImpl<Employee>(result, PageRequest.of(pageNo, pageSize), totalItems);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		data.put("totalItems", totalItems);
		data.put("totalPages", page.getTotalPages());
		return data;
	}
	
	public Map<String, Object> search02(int pageNo, int pageSize, String[] searchs,String... sortBy){
//		List<Sort> orders = new ArrayList<Order>();
		
		List<SearchCriteria> searchCriterias = Stream.of(searchs)
				.map(search->{
					Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(.*)");
					Matcher matcher = pattern.matcher(search);
					if(matcher.find())
						return SearchCriteria.builder()
								.fieldName(matcher.group(1))
								.operation(matcher.group(2))
								.value(matcher.group(3))
								.build();
					return null;
				}).toList();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> selectCq = cb.createQuery(Employee.class);
		Root<Employee> selectRoot = selectCq.from(Employee.class);
		Predicate selectPredicate = cb.conjunction();
		SearchConsumer searchConsumer = new SearchConsumer(cb,selectPredicate, selectRoot);
		
		if(searchCriterias != null && searchCriterias.size() > 0) 
			searchCriterias.forEach(searchConsumer);
		selectPredicate = searchConsumer.getPredicate();
		selectCq.where(selectPredicate);
		Stream.of(sortBy)
				.forEach(sort->{
					Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
					Matcher matcher = pattern.matcher(sort);
					if(matcher.find()) {
						if(matcher.group(3).trim().toLowerCase().equalsIgnoreCase("desc")) {
							selectCq.orderBy(cb.desc(selectRoot.get(matcher.group(1))));
						}else {
							selectCq.orderBy(cb.asc(selectRoot.get(matcher.group(1))));
						}
					}
				});
		TypedQuery<Employee> query =  entityManager.createQuery(selectCq);
		query.setFirstResult(pageNo).setMaxResults(pageSize);

		List<Employee> result = query.getResultList();
		/**
		 * total element
		 */
		CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
		Root<Employee> countRoot = countCq.from(Employee.class);
		Predicate countPredicate = cb.conjunction();
		SearchConsumer countSearchConsumer = new SearchConsumer(cb,countPredicate, countRoot);
		searchCriterias.forEach(countSearchConsumer);
		countPredicate = countSearchConsumer.getPredicate();
		countCq.select(cb.count(countRoot));
		countCq.where(countPredicate);
		Long totalCount = entityManager.createQuery(countCq).getSingleResult();
		Page<?> page = new PageImpl<Employee>(result, PageRequest.of(pageNo, pageSize), totalCount);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", result);
		data.put("totalCount", totalCount);
		data.put("totalPages", page.getTotalPages());
		return data;
	}
}
