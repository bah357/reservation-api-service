package com.reservation.api.service;

import com.reservation.api.model.reponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelReservationService {

  private final ReservationManagementService reservationManagementService;

  public ResponseDto cancelReservation(Integer bookingId) {
    var numberOfFreeTables = reservationManagementService.cancelReservation(bookingId);
    return ResponseDto.builder()
        .numberOfFreeTables(numberOfFreeTables)
        .numberOfRemainingTables(reservationManagementService.getRemainingTables())
        .build();
  }
}
