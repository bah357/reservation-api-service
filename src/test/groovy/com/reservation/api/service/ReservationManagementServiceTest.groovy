package com.reservation.api.service

import com.reservation.api.exception.BusinessException
import spock.lang.Specification

class ReservationManagementServiceTest extends Specification {
  def reservationManagementService = new ReservationManagementService()

  def setup() {
    reservationManagementService.reset()
  }
  def "should initiate tables successfully when call initializeTables given call method for the first time"() {
    when:
    reservationManagementService.initializeTables(15)

    then:
    noExceptionThrown()
  }

  def "should throw exception when call initializeTables given call method more than one times"() {
    when:
    reservationManagementService.initializeTables(15)
    reservationManagementService.initializeTables(15)

    then:
    thrown BusinessException
    def exception = this.specificationContext.thrownException as BusinessException
    exception.message == "This application has already been initialized."
  }

  def "should throw exception when call bookReservation given no tables initialization"() {
    when:
    reservationManagementService.bookReservation(2)

    then:
    thrown BusinessException
    def exception = this.specificationContext.thrownException as BusinessException
    exception.message == "Initialization is required, please call /initial endpoint to initial total number of tables."
  }

  def "should book tables successfully when call bookReservation given tables are already initiated"() {
    given:
    reservationManagementService.initializeTables(15)

    when:
    def response = reservationManagementService.bookReservation(2)

    then:
    verifyAll(response) {
      bookingId == 1
      bookedTables.length == 2
    }
    reservationManagementService.getRemainingTables() == 13
  }

  def "should fail to book tables when call bookReservation given tables are fully booked"() {
    given:
    reservationManagementService.initializeTables(15)
    reservationManagementService.bookReservation(15)

    when:
    reservationManagementService.bookReservation(2)

    then:
    thrown BusinessException
    def exception = this.specificationContext.thrownException as BusinessException
    exception.message == "All table is fully booked."
  }

  def "should fail to book tables when call bookReservation given tables are not enough for the number of customers"() {
    given:
    reservationManagementService.initializeTables(15)
    reservationManagementService.bookReservation(10)

    when:
    reservationManagementService.bookReservation(7)

    then:
    thrown BusinessException
    def exception = this.specificationContext.thrownException as BusinessException
    exception.message == "Not enough tables for this reservation."
  }

  def "should cancel booking successfully when call bookReservation given valid booking data"() {
    given:
    reservationManagementService.initializeTables(15)
    reservationManagementService.bookReservation(3) //bookingId = 1
    reservationManagementService.bookReservation(4) //bookingId = 2

    when:
    def freeTables = reservationManagementService.cancelReservation(2)

    then:
    freeTables == 4
    reservationManagementService.getRemainingTables() == 12
  }

  def "should fail to cancel booking when call bookReservation given invalid bookingId"() {
    given:
    reservationManagementService.initializeTables(15)
    reservationManagementService.bookReservation(3) //bookingId = 1

    when:
    reservationManagementService.cancelReservation(2)

    then:
    thrown BusinessException
    def exception = this.specificationContext.thrownException as BusinessException
    exception.message == "This bookingId (2) does not exist."
  }

  def "should fail to cancel booking when call bookReservation given bookingId is null"() {
    given:
    reservationManagementService.initializeTables(15)
    reservationManagementService.bookReservation(3) //bookingId = 1

    when:
    reservationManagementService.cancelReservation(null)

    then:
    thrown BusinessException
    def exception = this.specificationContext.thrownException as BusinessException
    exception.message == "bookingId cannot be null."
  }
}
