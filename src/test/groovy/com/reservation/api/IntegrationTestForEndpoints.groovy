package com.reservation.api

import io.restassured.http.ContentType
import jakarta.annotation.PostConstruct
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import io.restassured.RestAssured
import spock.lang.Specification
import org.springframework.http.HttpStatus
import static io.restassured.RestAssured.given

@AutoConfigureObservability
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestForEndpoints extends Specification {

  @LocalServerPort
  private int port


  @PostConstruct
  private void init() {
    RestAssured.port = port
  }

  def cleanup() {
    RestAssured.reset()
  }

  def "should return status 200 when call health_endpoint"() {
    expect:
    given()
        .when()
        .get("/actuator/health")
        .then()
        .statusCode(HttpStatus.OK.value())
  }

  def "should return status 202 when call initial"() {
    expect:
    given()
        .contentType(ContentType.JSON)
        .body("{\"totalNumberOfTables\": 15}")
        .when()
        .post("/v1/reservation/initial")
        .then()
        .statusCode(HttpStatus.ACCEPTED.value())
  }

  def "should return status 201 when call booking"() {
    expect:
    given()
        .contentType(ContentType.JSON)
        .body("{\"numberOfCustomer\": 8}")
        .when()
        .post("/v1/reservation/booking")
        .then()
        .statusCode(HttpStatus.CREATED.value())
  }

  def "should return status 200 when call cancel"() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"numberOfCustomer\": 8}")
        .when()
        .post("/v1/reservation/booking")
    expect:
    given()
        .contentType(ContentType.JSON)
        .body("{\"bookingId\": 1}")
        .when()
        .post("/v1/reservation/cancel")
        .then()
        .statusCode(HttpStatus.OK.value())
  }
}