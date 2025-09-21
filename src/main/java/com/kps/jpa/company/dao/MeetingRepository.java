package com.kps.jpa.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kps.jpa.company.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

}
