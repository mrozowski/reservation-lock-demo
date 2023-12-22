package com.mrozowski.seatreservation.domain;

import com.mrozowski.seatreservation.domain.port.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class SeatLockService {

  private final SeatRepository seatRepository;

  @Scheduled(cron = "${reservation.seat-lock.scheduler}")
  void releaseExpiredLock(){
    log.info("Running release expired lock job");
    seatRepository.releaseExpiredLock();
  }
}
