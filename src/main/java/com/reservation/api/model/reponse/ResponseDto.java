package com.reservation.api.model.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

  @JsonInclude(Include.NON_NULL)
  private String message;

  @JsonInclude(Include.NON_NULL)
  private Integer bookingId;

  @JsonInclude(Include.NON_NULL)
  private Integer numberOfReservedTables;

  @JsonInclude(Include.NON_NULL)
  private Integer numberOfFreeTables;

  @JsonInclude(Include.NON_NULL)
  private Integer numberOfRemainingTables;
}
