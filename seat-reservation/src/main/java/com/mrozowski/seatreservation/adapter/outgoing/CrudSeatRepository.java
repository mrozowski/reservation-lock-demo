package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
interface CrudSeatRepository extends CrudRepository<SeatEntity, Long> {

  Stream<SeatEntity> findAllByTripId(String tripId);

  Optional<SeatEntity> findFirstByTripIdAndSeatNumber(String tripId, String seatNumber);

  @Modifying
  @Query("UPDATE SeatEntity s SET s.status = 'LOCKED', " +
      "s.lockExpirationTime = :expirationDateTime, s.lockSessionToken = :sessionToken" +
      " WHERE s.tripId = :tripId AND s.seatNumber = :seatNumber")
  int lockSeat(@Param("tripId") String tripId,
               @Param("seatNumber") String seatNumber,
               @Param("sessionToken") String sessionToken,
               @Param("expirationDateTime") OffsetDateTime expirationDateTime);
}
