package com.mrozowski.seatreservation.adapter.outgoing;

import com.mrozowski.seatreservation.domain.command.FilterCriteria;
import com.mrozowski.seatreservation.domain.command.TripFilterCommand;
import com.mrozowski.seatreservation.domain.model.Trip;
import com.mrozowski.seatreservation.domain.port.TripRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class JpaTripRepository implements TripRepository {

  public static final String DATE = "date";
  private final CrudTripRepository repository;
  private final JpaTripMapper mapper;

  @Override
  public Page<Trip> getTripList(TripFilterCommand command) {
    var pageable = PageRequest.of(command.page(), command.pageSize(), Sort.by(DATE));
    var specification = buildSpecification(command.filters());
    return repository.findAll(specification, pageable).map(mapper::toTrip);
  }

  private Specification<TripEntity> buildSpecification(List<FilterCriteria> filters) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();

      for (FilterCriteria filter : filters) {
        predicates.add(buildPredicate(root, criteriaBuilder, filter));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  private Predicate buildPredicate(Root<TripEntity> root, CriteriaBuilder criteriaBuilder, FilterCriteria filter) {
    return criteriaBuilder.equal(root.get(filter.name()), filter.value());
  }
}
