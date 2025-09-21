package com.kps.jpa.school;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SchoolDaoTest {

//	@Autowired
//	SchoolService schoolService;
	
//	@Autowired
//	PersonDao personDao;
//
//	@Autowired
//	StudentDao studentDao;
//
//	@Autowired
//	TeacherDao teacherDao;
//
//	@Autowired
//	TestEntityManager testEntityManager;
//	
//	@BeforeEach
//	void setup() {
//		personDao.deleteAll();
//		testEntityManager.flush();
//	}
	
	/**
	 * Save data
	 */
	
//	@Test
//	void testSavePerson() {
//		 Person p01 = Student.builder()
//				 .id("P01")
//				 .dob(Date.from(LocalDate.of(1991, 01, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//				 .fullName("Nguyen Khac Manh")
//				 .avgScore(9.0)
//				 .build();
//		 Person savedPerson = personDao.save(p01);
//		 assertThat(savedPerson).isNotNull();
//		 assertThat(savedPerson.getId()).isEqualTo("P01");
//		 
//		 Optional<Student> foundedStudent = studentDao.findById("P01");
//		 assertThat(foundedStudent).isPresent();
//		 assertThat(foundedStudent.get().getFullName()).isEqualTo("Nguyen Khac Manh");
//		 assertThat(foundedStudent.get().getAvgScore()).isEqualTo(7);
//		 
//	}

	
	@Test
	void testSaveAndRollbackOnError() {
//		Student std01 = Student.builder()
//				.id("ST_ERROR_001")
//				.fullName("Student To Be Rolled Back")
//				.dob(Date.from(LocalDate.of(1991, 01, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//				 .avgScore(0)
//                .build();
//		
//		assertThrows(IOException.class, ()->{
//			studentDao.save(std01);
//			if(std01.getAvgScore() == 0) {
//				throw new IOException("Error save student");
//			}
//		});
//		
//		assertThat(studentDao.findById("ST_ERROR_001")).isNotPresent();
	}
}
