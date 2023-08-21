package com.reservation.api.service

import spock.lang.Specification

class InitialReservationServiceTest extends Specification {
  def reservationService = Mock(ReservationManagementService.class)
  def initialReservationService = new InitialReservationService(reservationService)

  def "should return success message when call initializeTables"() {
    when:
    def response = initialReservationService.initializeTables(20)

    then:
    1 * reservationService.initializeTables(20)
    verifyAll(response) {
      message == "Initiate number of table successfully."
    }
  }
}
