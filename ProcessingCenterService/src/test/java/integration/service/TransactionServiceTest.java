package integration.service;



//@SpringBootTest(classes = {ProcessingCenterServiceRunner.class})
//public class TransactionServiceTest extends TestContainersBase {
//
//    @Autowired
//    private TransactionService transactionService;
//
//    private final TransactionDtoWithoutId transactionDtoWithoutId = new TransactionDtoWithoutId(
//            LocalDate.now(),
//            BigDecimal.valueOf(333.33),
//            null,
//            null,
//            null,
//            null,
//            "123"
//    );
//
//
//    @Test
//    public void CreateTransactionTest() {
//        TransactionDtoWithId createdTransaction = transactionService.create(transactionDtoWithoutId);
//
//        Assertions.assertAll(
//                () -> assertThat(createdTransaction.sum()).isEqualTo(transactionDtoWithoutId.sum()),
//                () -> assertThat(createdTransaction.authorizationCode()).isEqualTo(transactionDtoWithoutId.authorizationCode()),
//                () -> assertThat(createdTransaction.version()).isEqualTo(0L)
//        );
//    }
//
//    @Test
//    public void ReadTransactionTest() {
//        Long createdDtoId = transactionService.create(transactionDtoWithoutId).id();
//        TransactionDtoWithId readDto = transactionService.read(createdDtoId).orElse(null);
//
//        Assertions.assertAll(
//                () -> assertThat(readDto.sum()).isEqualTo(transactionDtoWithoutId.sum()),
//                () -> assertThat(readDto.authorizationCode()).isEqualTo(transactionDtoWithoutId.authorizationCode()),
//                () -> assertThat(readDto.version()).isEqualTo(0L)
//        );
//    }
//
//    @Test
//    public void ReadAllTransactionTest() {
//        int size = transactionService.readAll().size();
//        transactionService.create(transactionDtoWithoutId);
//        transactionService.create(transactionDtoWithoutId);
//        assertThat(transactionService.readAll()).hasSize(size + 2);
//    }
//
//    @Test
//    public void UpdateAndReadTransactionTest() {
//        TransactionDtoWithId createdTransaction = transactionService.create(transactionDtoWithoutId);
//        Long id = createdTransaction.id();
//        Long version = createdTransaction.version();
//
//        TransactionDtoWithId dtoForUpdate = new TransactionDtoWithId(
//                id,
//                LocalDate.now(),
//                BigDecimal.valueOf(123.45),
//                null,
//                null,
//                null,
//                null,
//                "12345",
//                version
//        );
//
//        TransactionDtoWithId updatedTransaction = transactionService.update(dtoForUpdate);
//
//        Assertions.assertAll(
//                () -> assertThat(updatedTransaction.sum()).isEqualTo(dtoForUpdate.sum()),
//                () -> assertThat(updatedTransaction.authorizationCode()).isEqualTo(dtoForUpdate.authorizationCode()),
//                () -> assertThat(updatedTransaction.version()).isEqualTo(dtoForUpdate.version() + 1L)
//        );
//    }
//
//    @Test
//    public void DeleteTransactionTest() {
//        Long id = transactionService.create(transactionDtoWithoutId).id();
//        transactionService.delete(id);
//
//        assertThat(transactionService.read(id)).isEmpty();
//    }
//
//    @Test
//    public void DeleteAllTransactionTest() {
//        transactionService.create(transactionDtoWithoutId);
//        transactionService.create(transactionDtoWithoutId);
//
//        transactionService.deleteAll();
//
//        assertThat(transactionService.readAll()).hasSize(0);
//    }
//}
