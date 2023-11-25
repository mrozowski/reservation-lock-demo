package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.command.TripListFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.port.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
class JdbcTripRepository implements TripRepository {

  private CrudTripRepository repository;
  private JdbcTripMapper mapper;

  @Override
  public Page<Trip> getTripList(TripListFilterCommand command) {
    var pageable = PageRequest.of(command.page(), command.pageSize());
    return repository.findTripsWithFilter(command.departure(), command.destination(), command.date(), pageable)
        .map(mapper::toTrip);
  }
}
