package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface CrudReservationRepository extends CrudRepository<ReservationEntity, Long> {

  Optional<ReservationEntity> findFirstByReferenceAndCustomerName(String reference, String customerName);

  @Modifying
  @Query("UPDATE ReservationEntity r SET r.paymentStatus = 'CANCELED' WHERE r.reference = :reference AND r.customerName" +
      " = :customerName")
  int cancelReservation(@Param("reference") String reference, @Param("customerName") String customerName);

  @Modifying
  @Query("UPDATE ReservationEntity r SET r.paymentStatus = :status WHERE r.id = :reservationId")
  int updatePaymentStatus(@Param("reservationId") String reservationId,
                          @Param("status") ReservationEntity.PaymentStatus status);
}
