package com.reservation.api.service;

import com.reservation.api.exception.BusinessException;
import com.reservation.api.model.Booking;
import com.reservation.api.model.Table;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationManagementService {

  private Integer totalTables;
  private final HashMap<Integer, Table> reservedTables = new HashMap<>();
  private final HashMap<Integer, Table> remainingTables = new HashMap<>();
  private final HashMap<Integer, Booking> currentBooking = new HashMap<>();
  private static AtomicInteger count = new AtomicInteger(0);

  public void initializeTables(Integer totalNumberOfTables) {
    if (totalTables != null) {
      throw new BusinessException("This application has already been initialized.");
    }
    totalTables = totalNumberOfTables;
    for (int i = 1; i <= totalTables; i++) {
      remainingTables.put(i, Table.builder().tableId(i).build());
    }
  }

  public Booking bookReservation(Integer requestNumberOfTable) {
    verifyInitialization();
    verifyAvailableTables(requestNumberOfTable);
    var bookingId = count.incrementAndGet();
    var booking = bookTables(bookingId, requestNumberOfTable);
    currentBooking.put(bookingId, booking);
    return booking;
  }

  public Integer cancelReservation(Integer bookingId) {
    verifyInitialization();
    verifyBooking(bookingId);
    var numberOfFreeTables = cancelTables(bookingId);
    currentBooking.remove(bookingId);
    return numberOfFreeTables;
  }

  public void reset() {
    totalTables = null;
    reservedTables.clear();
    remainingTables.clear();
    currentBooking.clear();
    count = new AtomicInteger(0);
  }

  private Booking bookTables(Integer bookingId, Integer requestNumberOfTable) {
    var newBookingTables = new Integer[requestNumberOfTable];
    int counter = 0;
    for (var data : remainingTables.entrySet()) {
      if (counter == requestNumberOfTable) {
        break;
      }
      newBookingTables[counter] = data.getKey();
      reservedTables.put(data.getKey(), data.getValue());
      counter++;
    }
    for (var tableId : newBookingTables) {
      remainingTables.remove(tableId);
    }
    return Booking.builder().bookingId(bookingId).bookedTables(newBookingTables).build();
  }

  private Integer cancelTables(Integer bookingId) {
    var booking = currentBooking.get(bookingId);
    var numberOfFreeTables = booking.getBookedTables().length;
    for (var tableId : booking.getBookedTables()) {
      var table = reservedTables.get(tableId);
      remainingTables.put(tableId, table);
      reservedTables.remove(tableId);
    }
    return numberOfFreeTables;
  }

  private void verifyInitialization() {
    if (totalTables == null) {
      throw new BusinessException("Initialization is required, please call /initial endpoint to initial total number of tables.");
    }
  }

  private void verifyAvailableTables(Integer requestNumberOfTable) {
    if (totalTables.equals(reservedTables.size())) {
      throw new BusinessException("All table is fully booked.");
    }
    if (remainingTables.size() - requestNumberOfTable < 0) {
      throw new BusinessException("Not enough tables for this reservation.");
    }
  }

  private void verifyBooking(Integer bookingId) {
    if (bookingId == null) {
      throw new BusinessException("bookingId cannot be null.");
    }
    if (currentBooking.get(bookingId) == null) {
      throw new BusinessException(String.format("This bookingId (%s) does not exist.", bookingId));
    }
  }

  public Integer getRemainingTables() {
    return totalTables - reservedTables.size();
  }
}
