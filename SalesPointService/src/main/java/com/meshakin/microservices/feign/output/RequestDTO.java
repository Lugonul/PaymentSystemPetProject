package com.meshakin.microservices.feign.output;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RequestDTO (
LocalDate transactionDate,
BigDecimal sum,
Long transactionTypeId,
Long cardId,
Long terminalId,
Long responseCodeId,
String authorizationCode
){
}
