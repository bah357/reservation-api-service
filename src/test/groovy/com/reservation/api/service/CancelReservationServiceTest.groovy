package com.reservation.api.service

import com.reservation.api.exception.BusinessException
import spock.lang.Specification

class CancelReservationServiceTest extends Specification {
  def reservationService = Mock(ReservationManagementService.class)
  def cancelReservationService = new CancelReservationService(reservationService)

  def "should return numberOfFreeTables successfully when call cancelReservation given no error"() {
    given:
    reservationService.cancelReservation(_ as Integer) >> 5

    when:
    def response = cancelReservationService.cancelReservation(1)

    then:
    verifyAll(response) {
      numberOfFreeTables == 5
    }
  }

  def "should throw exception when call cancelReservation given reservationService throw error"() {
    given:
    reservationService.cancelReservation(_ as Integer) >> { throw new BusinessException("invalid bookingId") }

    when:
    cancelReservationService.cancelReservation(1)

    then:
    thrown BusinessException
  }
}
