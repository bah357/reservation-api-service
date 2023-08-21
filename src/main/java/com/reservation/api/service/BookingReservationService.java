package com.reservation.api.service;

import com.reservation.api.exception.BusinessException;
import com.reservation.api.model.reponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingReservationService {

  private final ReservationManagementService reservationManagementService;

  public ResponseDto bookReservation(Integer numberOfCustomer) {
    var requestNumberOfTable = calculateRequestedTables(numberOfCustomer);
    var booking = reservationManagementService.bookReservation(requestNumberOfTable);
    return ResponseDto.builder()
        .bookingId(booking.getBookingId())
        .numberOfReservedTables(requestNumberOfTable)
        .numberOfRemainingTables(reservationManagementService.getRemainingTables())
        .build();
  }

  private Integer calculateRequestedTables(Integer numberOfCustomer) {
    if (numberOfCustomer == null || numberOfCustomer.equals(0)) {
      throw new BusinessException("Require at least 1 customer to book a reservation.");
    }
    var requestNumberOfTable = numberOfCustomer / 4;
    if (numberOfCustomer % 4 > 0) {
      requestNumberOfTable++;
    }
    return requestNumberOfTable;
  }
}
