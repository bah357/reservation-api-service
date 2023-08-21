package com.reservation.api.controller

import com.reservation.api.model.request.BookingDto
import com.reservation.api.model.request.CancelDto
import com.reservation.api.model.request.InitialDto
import com.reservation.api.service.InitialReservationService
import com.reservation.api.service.BookingReservationService
import com.reservation.api.service.CancelReservationService
import spock.lang.Specification

class ReservationManagementControllerTest extends Specification {
  def initialService = Mock(InitialReservationService.class)
  def bookingService = Mock(BookingReservationService.class)
  def cancelService = Mock(CancelReservationService.class)
  def controller = new ReservationManagementController(initialService, bookingService, cancelService)

  def "should invoke InitialReservationService when call initializeTables method"() {
    given:
    def totalNumberOfTable = 10

    when:
    controller.initializeTables(InitialDto.builder().totalNumberOfTables(totalNumberOfTable).build())

    then:
    1 * initialService.initializeTables(totalNumberOfTable)
  }

  def "should invoke BookingReservationService when call bookReservation method"() {
    given:
    def numberOfCustomer = 4

    when:
    controller.bookReservation(BookingDto.builder().numberOfCustomer(numberOfCustomer).build())

    then:
    1 * bookingService.bookReservation(numberOfCustomer)
  }

  def "should invoke CancelReservationService when call bookReservation method"() {
    given:
    def bookingId = 101

    when:
    controller.cancelReservation(CancelDto.builder().bookingId(bookingId).build())

    then:
    1 * cancelService.cancelReservation(bookingId)
  }
}