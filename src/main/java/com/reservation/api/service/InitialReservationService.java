package com.reservation.api.service;

import com.reservation.api.model.reponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitialReservationService {

  private final ReservationManagementService reservationManagementService;

  public ResponseDto initializeTables(Integer totalNumberOfTables) {
    reservationManagementService.initializeTables(totalNumberOfTables);
    return ResponseDto.builder().message("Initiate number of table successfully.").build();
  }
}
