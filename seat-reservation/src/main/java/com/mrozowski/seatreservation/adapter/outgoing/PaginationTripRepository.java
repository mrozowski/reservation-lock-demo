package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface PaginationTripRepository extends PagingAndSortingRepository<TripEntity, String>,
    JpaSpecificationExecutor<TripEntity> {

  Optional<TripEntity> findFirstById(String id);
}
