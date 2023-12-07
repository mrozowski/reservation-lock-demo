package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface CrudReservationRepository extends CrudRepository<ReservationEntity, Long> {

  Optional<ReservationEntity> findFirstByReferenceAndCustomerName(String reference, String customerName);
}
