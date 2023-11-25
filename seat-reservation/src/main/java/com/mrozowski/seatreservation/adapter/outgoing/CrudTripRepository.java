package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CrudTripRepository extends PagingAndSortingRepository<TripEntity, String> {


  @Query("SELECT * FROM trip WHERE departure = :departure " +
      "AND destination = :destination " +
      "AND DATE(date) = :date")
  Page<TripEntity> findTripsWithFilter(
      @Param("departure") String departure,
      @Param("destination") String destination,
      @Param("date") LocalDate date,
      Pageable pageable);
}
