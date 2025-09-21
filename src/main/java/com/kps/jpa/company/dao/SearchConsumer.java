package com.kps.jpa.company.dao;

import java.util.function.Consumer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchConsumer implements Consumer<SearchCriteria> {

	private CriteriaBuilder cb;
	private Predicate predicate;
	private Root root;
	public void accept(SearchCriteria t) {
		if(t.getOperation().equals(">")) {
			predicate = cb.and(cb.greaterThanOrEqualTo(root.get(t.getFieldName()), t.getValue().toString())) ;
		}else if(t.getOperation().equals("<")) {
			predicate = cb.and(cb.lessThanOrEqualTo(root.get(t.getFieldName()), t.getValue().toString()));
		}else {
			if(root.get(t.getFieldName()).getJavaType() == String.class) {
				predicate =  cb.and(cb.like(root.get(t.getFieldName()), "%"+t.getValue()+"%"));
			}else {
				predicate =  cb.and(cb.equal(root.get(t.getFieldName()), t.getValue()));
			}
		}
	};
}
