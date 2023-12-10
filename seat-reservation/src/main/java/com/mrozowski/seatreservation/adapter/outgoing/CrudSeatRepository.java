package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
interface CrudSeatRepository extends CrudRepository<SeatEntity, Long> {

  Stream<SeatEntity> findAllByTripId(String tripId);
}
