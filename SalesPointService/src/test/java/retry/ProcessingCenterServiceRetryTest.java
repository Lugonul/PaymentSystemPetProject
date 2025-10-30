package retry;

//@Profile("test")
//@SpringBootTest(classes = {ProcessingCenterService.class, RetryConfig.class})
//class ProcessingCenterServiceRetryTest {
//
//    @Autowired
//    private ProcessingCenterService service;
//
//    @MockitoBean
//    private ProcessingCenterClient feignClient;
//
//    @Test
//    void shouldRetryThreeTimesOnServerError() {
//        // Arrange
//        RequestDTO request = new RequestDTO(
//                LocalDate.now(),
//                BigDecimal.TEN,
//                4L,
//                6L,
//                8L,
//                6L,
//                "999"
//        );
//
//        when(feignClient.processPayment(any()))
//                .thenThrow(new ServerErrorException("Server error"));
//
//        // Act
//
//        try {
//            service.processPaymentWithRetry(request);
//        }
//        catch (ServerErrorException e) {}
//
//        verify(feignClient, times(3)).processPayment(any());
//    }
//
//    @Test
//    void shouldFailAfterThreeRetries() {
//        // Arrange
//RequestDTO request = new RequestDTO(
//        LocalDate.now(),
//        BigDecimal.TEN,
//        4L,
//        6L,
//        8L,
//        6L,
//        "999"
//);
//
//    when(feignClient.processPayment(any()))
//            .thenThrow(new ClientErrorException("Client error"));
//
//    // Act
//
//        try {
//        service.processPaymentWithRetry(request);
//    }
//        catch (ClientErrorException e) {}
//
//    verify(feignClient, times(1)).processPayment(any());
//}
//}