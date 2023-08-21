package com.reservation.api.controller;

import com.reservation.api.model.reponse.ResponseDto;
import com.reservation.api.model.request.BookingDto;
import com.reservation.api.model.request.CancelDto;
import com.reservation.api.model.request.InitialDto;
import com.reservation.api.service.BookingReservationService;
import com.reservation.api.service.CancelReservationService;
import com.reservation.api.service.InitialReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/v1/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationManagementController {

  private final InitialReservationService initialReservationService;
  private final BookingReservationService bookingReservationService;
  private final CancelReservationService cancelReservationService;

  @PostMapping("/initial")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseDto initializeTables(@RequestBody InitialDto initialDto) {
    return initialReservationService.initializeTables(initialDto.getTotalNumberOfTables());
  }

  @PostMapping("/booking")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseDto bookReservation(@RequestBody BookingDto bookingDto) {
    return bookingReservationService.bookReservation(bookingDto.getNumberOfCustomer());
  }

  @PostMapping("/cancel")
  @ResponseStatus(HttpStatus.OK)
  public ResponseDto cancelReservation(@RequestBody CancelDto cancelDto) {
    return cancelReservationService.cancelReservation(cancelDto.getBookingId());
  }
}
