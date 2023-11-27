package com.mrozowski.seatreservation.adapter.outgoing;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudTripRepository extends PagingAndSortingRepository<TripEntity, String>,
    JpaSpecificationExecutor<TripEntity> {
}
