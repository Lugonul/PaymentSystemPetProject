package service.hibernate;


//public class CardServiceTest {
//
//    static CardService cardService;
//    static AnnotationConfigApplicationContext context;
//    CardDtoWithId CARD_DTO_WITH_ID = new CardDtoWithId(
//            1L,
//            "777",
//            LocalDate.now(),
//            "777 Holder",
//            null,
//            null,
//            null,
//            LocalDateTime.of(2000, 1, 1, 0, 0, 0),
//            LocalDateTime.of(2000, 1, 1, 0, 0, 0),
//            0L
//    );
//    CardDtoWithoutId CARD_DTO_WITHOUT_ID = new CardDtoWithoutId(
//            "777",
//            LocalDate.now(),
//            "777 Holder",
//            null,
//            null,
//            null,
//            LocalDateTime.of(2000, 1, 1, 0, 0, 0),
//            LocalDateTime.of(2000, 1, 1, 0, 0, 0)
//    );
//
//
//    @BeforeAll
//    public static void init() {
//        context = new AnnotationConfigApplicationContext(AppConfig.class);
//        cardService = context.getBean(CardService.class);
//
//    }
//    @Test
//    public void insertTest() {
//        CardDtoWithId cardDtoWithId = cardService.create(CARD_DTO_WITHOUT_ID);
//        CardDtoWithoutId cardDtoWithoutId = new CardDtoWithoutId(
//                cardDtoWithId.cardNumber(),
//                cardDtoWithId.expirationDate(),
//                cardDtoWithId.holderName(),
//                cardDtoWithId.cardStatusId(),
//                cardDtoWithId.paymentSystemId(),
//                cardDtoWithId.accountId(),
//                cardDtoWithId.receivedFromIssuingBank(),
//                cardDtoWithId.sentToIssuingBank()
//        );
//        Assertions.assertEquals(CARD_DTO_WITHOUT_ID, cardDtoWithoutId);
//    }
//
//    @Test
//    public void updateTest() {
//        CardDtoWithId cardDtoWithId = new CardDtoWithId(
//                cardService.readAll().getFirst().id(),
//                "4321",
//                LocalDate.now(),
//                "43331 Holder",
//                null,
//                null,
//                null,
//                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
//                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
//                0L
//        );
//        for (int i = 0; i < 5; i++) {
//            cardService.create(CARD_DTO_WITHOUT_ID);}
//        Assertions.assertEquals(cardDtoWithId, cardService.update(cardDtoWithId));
//    }
//
//    @Test
//    public void deleteTest() {
//        cardService.create(CARD_DTO_WITHOUT_ID);
//        Long id = cardService.readAll().getFirst().id();
//        cardService.delete(id);
//        Assertions.assertNull(cardService.read(id).orElse(null));
//    }
//
//
//    @Test
//    public void selectByIdTest() {
//        cardService.create(CARD_DTO_WITHOUT_ID);
//        Long id = cardService.readAll().getFirst().id();
//        Assertions.assertEquals(CARD_DTO_WITH_ID, cardService.read(id).get());
//    }
//
//    @Test
//    public void selectAllTest() {
//        int count = cardService.readAll().size();
//        for (int i = 0; i < 2; i++) {
//            cardService.create(CARD_DTO_WITHOUT_ID);
//        }
//        Assertions.assertEquals(count + 2, cardService.readAll().size());
//    }
//    @Test
//    public void truncateTest() {
//        cardService.create(CARD_DTO_WITHOUT_ID);
//        cardService.deleteAll();
//        Assertions.assertEquals(0, cardService.readAll().size());
//    }

//    @AfterAll
//    static void afterAll() {
//        context.getBean(Operations.class).dropAllTables();
//        context.close();
//    }
//}
