package com.example.orderapp.repository;

import com.example.orderapp.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationDao extends JpaRepository<Station, Integer> {
}
