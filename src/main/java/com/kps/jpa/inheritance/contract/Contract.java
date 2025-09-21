package com.kps.jpa.inheritance.contract;

import java.util.Date;
import java.util.Set;

import com.kps.jpa.company.dto.Gender;
import com.kps.jpa.company.entity.Address;
import com.kps.jpa.company.entity.Meeting;
import com.kps.jpa.company.entity.Salary;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "comp01_contract")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contract_type", discriminatorType = DiscriminatorType.STRING)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Contract {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractNo;

    private String customerName;

    private String signedDate;
    
    private String startDate;

    private String endDate;

    private Double totalAmount;

    private String status;
}
