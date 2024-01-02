package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.port.ReservationRepository;
import com.mrozowski.seatreservation.domain.port.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class SeatLockService {

  private final SeatRepository seatRepository;
  private final ReservationRepository reservationRepository;

  @Scheduled(cron = "${reservation.seat-lock.scheduler}")
  @Transactional
  public void releaseExpiredLock(){
    log.info("Running release expired lock job");
    var seatIds = seatRepository.releaseExpiredLock();
    reservationRepository.cancelPendingReservations(seatIds);
  }
}
