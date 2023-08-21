package com.reservation.api.service

import com.reservation.api.exception.BusinessException
import com.reservation.api.model.Booking
import spock.lang.Specification

class BookingReservationServiceTest extends Specification {
  def reservationManagementService = Mock(ReservationManagementService.class)
  def bookingService = new BookingReservationService(reservationManagementService)

  def "should calculate numberOfReservedTables correctly when call bookReservation given numberOfCustomer is equal to 6"() {
    given:
    def numberOfCustomer = 6
    reservationManagementService.bookReservation(_ as Integer) >> Booking.builder().bookingId(1).build()

    when:
    def response = bookingService.bookReservation(numberOfCustomer)

    then: "numberOfReservedTables = 2"
    verifyAll(response) {
      bookingId == 1
      numberOfReservedTables == 2
    }
  }

  def "should calculate numberOfReservedTables correctly when call bookReservation given numberOfCustomer is equal to 12"() {
    given:
    def numberOfCustomer = 12
    reservationManagementService.bookReservation(_ as Integer) >> Booking.builder().bookingId(2).build()

    when:
    def response = bookingService.bookReservation(numberOfCustomer)

    then: "numberOfReservedTables = 3"
    verifyAll(response) {
      bookingId == 2
      numberOfReservedTables == 3
    }
  }

  def "should throw exception when numberOfCustomer is equal to 0 or null"() {
    when:
    bookingService.bookReservation(numberOfCustomer as Integer)

    then:
    thrown BusinessException

    where:
    numberOfCustomer << [0, null]
  }
}
