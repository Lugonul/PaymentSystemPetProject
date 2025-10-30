package integration.controller;


//@SpringBootTest(classes = {ProcessingCenterServiceRunner.class})
//@AutoConfigureMockMvc
//public class TransactionControllerTest extends TestContainersBase {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper()
//            .registerModule(new JavaTimeModule())
//            .disableDefaultTyping();
//
//
//    @MockitoBean
//    private TransactionService transactionService;
//
//
//    private static final LocalDate nowDate = LocalDate.now();
//
//    private static TransactionDtoWithoutId transacion1BeforeCreate = new TransactionDtoWithoutId(
//            nowDate,
//            BigDecimal.valueOf(123.45),
//            null,
//            null,
//            null,
//            null,
//            "12345"
//    );
//
//    private static TransactionDtoWithId transacion1 = new TransactionDtoWithId(
//            1L,
//            nowDate,
//            BigDecimal.valueOf(123.45),
//            null,
//            null,
//            null,
//            null,
//            "12345",
//            0L
//    );
//    private static TransactionDtoWithId transacion2 = new TransactionDtoWithId(
//            2L,
//            nowDate,
//            BigDecimal.valueOf(54.321),
//            null,
//            null,
//            null,
//            null,
//            "12345",
//            0L
//    );
//
//
//    @Test
//    void getAllTransactions_ShouldReturnTransactions() throws Exception {
//        List<TransactionDtoWithId> transacionList = List.of(transacion1, transacion2);
//        when(transactionService.readAll()).thenReturn(transacionList);
//
//        mockMvc.perform(get("/api/transactions"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(transacion1.id()))
//                .andExpect(jsonPath("$[1].id").value(transacion2.id()))
//                .andExpect(jsonPath("$[0].sum").value(transacion1.sum()))
//                .andExpect(jsonPath("$[1].sum").value(transacion2.sum()));
//    }
//
//    @Test
//    void getTransactionById_ShouldReturnTransaction() throws Exception {
//        when(transactionService.read(transacion1.id())).thenReturn(Optional.of(transacion1));
//        mockMvc.perform(get("/api/transactions/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(transacion1.id()))
//                .andExpect(jsonPath("$.sum").value(transacion1.sum()));
//    }
//
//    @Test
//    void createTransaction_ShouldCreatedTransaction() throws Exception {
//        when(transactionService.create(any())).thenReturn(transacion1);
//
//        mockMvc.perform(post("/api/transactions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transacion1BeforeCreate)))
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"))
//                .andExpect(jsonPath("$.id").value(transacion1.id()))
//                .andExpect(jsonPath("$.sum").value(transacion1.sum()));
//    }
//
//
//    @Test
//    void updateTransaction_ShouldUpdatedTransaction() throws Exception {
//        when(transactionService.update(any(TransactionDtoWithId.class))).thenReturn(transacion2);
//
//        mockMvc.perform(put("/api/transactions/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transacion2)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(transacion2.id()))
//                .andExpect(jsonPath("$.sum").value(transacion2.sum()));
//    }
//
//
//    @Test
//    void deleteTransaction_ShouldReturnNoContent() throws Exception {
//        doNothing().when(transactionService).delete(1L);
//
//        mockMvc.perform(delete("/api/transactions/1"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void getTransactionById_ShouldReturnNotFound() throws Exception {
//        when(transactionService.read(999L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/api/transactions/999"))
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    void updateTransaction_ShouldReturnNotFound() throws Exception {
//        when(transactionService.update(any(TransactionDtoWithId.class)))
//                .thenThrow(new EntityNotFoundException("Transaction not found"));
//
//        mockMvc.perform(put("/api/transactions/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transacion2)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void deleteTransaction_ShouldReturnNotFound() throws Exception {
//        doThrow(new EntityNotFoundException("Transaction not found"))
//                .when(transactionService).delete(999L);
//
//        mockMvc.perform(delete("/api/transactions/999"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createTransaction_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
//        String invalidJson = """
//        {
//        "sum": example,
//        }
//        """;
//
//        mockMvc.perform(post("/api/transactions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidJson))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateTransaction_ShouldReturnBadRequest_WhenIdMismatch() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        LocalDateTime nowDateTime = LocalDateTime.now();
//
//
//        mockMvc.perform(put("/api/cards/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transacion1)))
//                .andExpect(status().isBadRequest());
//    }
//}