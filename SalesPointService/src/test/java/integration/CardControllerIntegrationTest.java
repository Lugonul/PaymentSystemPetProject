package integration;


//public class CardControllerIntegrationTest {
//    private MockMvc mockMvc;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Mock
//    private CardService cardService;
//
//    @InjectMocks
//    private CardController cardController;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//
//        // Настройка ObjectMapper для правильной работы с датами
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(cardController)
//                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
//                .build();
//    }
//
//    @Test
//    void getAllCards_ShouldReturnCards() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        LocalDateTime nowDateTime = LocalDateTime.now();
//
//        CardDtoWithId card1 = new CardDtoWithId(
//                1L,
//                "777",
//                nowDate,
//                "777 Holder",
//                null,
//                null,
//                null,
//                nowDateTime,
//                nowDateTime
//        );
//
//        CardDtoWithId card2 = new CardDtoWithId(
//                2L,
//                "888",
//                nowDate,
//                "888 Holder",
//                null,
//                null,
//                null,
//                nowDateTime,
//                nowDateTime
//        );
//
//        List<CardDtoWithId> cards = List.of(card1, card2);
//
//        when(cardService.selectAll()).thenReturn(cards);
//
//        mockMvc.perform(get("/api/cards"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].cardNumber").value("777"))
//                .andExpect(jsonPath("$[0].holderName").value("777 Holder"))
//                .andExpect(jsonPath("$[1].id").value(2L))
//                .andExpect(jsonPath("$[1].cardNumber").value("888"))
//                .andExpect(jsonPath("$[1].holderName").value("888 Holder"));
//    }
//
//    @Test
//    void getCardById_ShouldReturnCard() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        LocalDateTime nowDateTime = LocalDateTime.now();
//
//        CardDtoWithId card = new CardDtoWithId(
//                1L,
//                "777",
//                nowDate,
//                "777 Holder",
//                null,
//                null,
//                null,
//                nowDateTime,
//                nowDateTime
//        );
//
//        when(cardService.selectById(1L)).thenReturn(Optional.of(card));
//
//        mockMvc.perform(get("/api/cards/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.cardNumber").value("777"))
//                .andExpect(jsonPath("$.holderName").value("777 Holder"));
//    }
//
//    @Test
//    void createCard_ShouldReturnCreated() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        LocalDateTime nowDateTime = LocalDateTime.now();
//
//        CardDtoWithoutId requestDto = new CardDtoWithoutId(
//                "999",
//                nowDate,
//                "New Holder",
//                null,
//                null,
//                null,
//                nowDateTime,
//                nowDateTime
//        );
//
//        CardDtoWithId responseDto = new CardDtoWithId(
//                3L,
//                "999",
//                nowDate,
//                "New Holder",
//                null,
//                null,
//                null,
//                nowDateTime,
//                nowDateTime
//        );
//
//        when(cardService.insert(any(CardDtoWithoutId.class))).thenReturn(responseDto);
//
//        mockMvc.perform(post("/api/cards")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"))
//                .andExpect(jsonPath("$.id").value(3L))
//                .andExpect(jsonPath("$.cardNumber").value("999"))
//                .andExpect(jsonPath("$.holderName").value("New Holder"));
//    }
//
//    @Test
//    void updateCard_ShouldReturnUpdatedCard() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        LocalDateTime nowDateTime = LocalDateTime.now();
//
//        CardDtoWithId requestDto = new CardDtoWithId(
//                1L, "777-updated", nowDate, "Updated Holder",
//                null, null, null, nowDateTime, nowDateTime
//        );
//
//        CardDtoWithId responseDto = new CardDtoWithId(
//                1L, "777-updated", nowDate, "Updated Holder",
//                null, null, null, nowDateTime, nowDateTime
//        );
//
//        when(cardService.update(any(CardDtoWithId.class))).thenReturn(responseDto);
//
//        mockMvc.perform(put("/api/cards/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.cardNumber").value("777-updated"))
//                .andExpect(jsonPath("$.holderName").value("Updated Holder"));
//    }
//
//    @Test
//    void deleteCard_ShouldReturnNoContent() throws Exception {
//        doNothing().when(cardService).delete(1L);
//
//        mockMvc.perform(delete("/api/cards/1"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void getAllCards_ShouldReturnEmptyList() throws Exception {
//        when(cardService.selectAll()).thenReturn(List.of());
//
//        mockMvc.perform(get("/api/cards"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$").isEmpty());
//    }
//
//    @Test
//    void getCardById_ShouldReturnNotFound() throws Exception {
//        when(cardService.selectById(999L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/api/cards/999"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void updateCard_ShouldReturnNotFound() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        LocalDateTime nowDateTime = LocalDateTime.now();
//
//        CardDtoWithId requestDto = new CardDtoWithId(
//                999L, "invalid", nowDate, "Invalid Holder",
//                null, null, null, nowDateTime, nowDateTime
//        );
//
//        when(cardService.update(any(CardDtoWithId.class)))
//                .thenThrow(new EntityNotFoundException("Card not found"));
//
//        mockMvc.perform(put("/api/cards/999")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void deleteCard_ShouldReturnNotFound() throws Exception {
//        doThrow(new EntityNotFoundException("Card not found"))
//                .when(cardService).delete(999L);
//
//        mockMvc.perform(delete("/api/cards/999"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createCard_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
//        String invalidJson = """
//        {
//            "cardNumber": 123,  // должно быть строкой
//            "expirationDate": "invalid-date", // неверный формат даты
//            "holderName": 456  // должно быть строкой
//        }
//        """;
//
//        mockMvc.perform(post("/api/cards")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidJson))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateCard_ShouldReturnBadRequest_WhenIdMismatch() throws Exception {
//        LocalDate nowDate = LocalDate.now();
//        LocalDateTime nowDateTime = LocalDateTime.now();
//
//        CardDtoWithId requestDto = new CardDtoWithId(
//                1L, "777", nowDate, "Holder",
//                null, null, null, nowDateTime, nowDateTime
//        );
//
//        mockMvc.perform(put("/api/cards/2")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isBadRequest());
//    }
//}