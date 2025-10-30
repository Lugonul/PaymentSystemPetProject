package integration.cache;

//@SpringBootTest(classes = ProcessingCenterServiceRunner.class)
//@EnableCaching
//public class CacheTest extends TestContainersBase {
//
//    @Autowired
//    private TransactionService transactionService;
//
//    @Autowired
//    private CacheManager cacheManager;
//
//    @MockitoSpyBean
//    private TransactionRepository transactionRepository;
//
//    TransactionDtoWithoutId createDto = new TransactionDtoWithoutId(
//            LocalDate.now(),
//            BigDecimal.valueOf(333.33),
//            null,
//            null,
//            null,
//            null,
//            "123"
//    );
//
//    @Test
//    public void testCache() {
//        TransactionDtoWithId created = transactionService.create(createDto);
//        Long id = created.id();
//
//        // Очищаем кэш перед тестом
//        evictCacheForTransaction(id);
//
//        // Первое чтение - должно закэшироваться
//        Optional<TransactionDtoWithId> firstRead = transactionService.read(id);
//        assertTrue(firstRead.isPresent());
//
//        // Проверяем, что было обращение к БД
//        verify(transactionRepository, times(1)).findById(id);
//
//        // Проверяем, что данные есть в кэше (как Object)
//        assertCacheContains(id, created);
//
//        // Второе чтение - должно быть из кэша
//        Optional<TransactionDtoWithId> secondRead = transactionService.read(id);
//        assertTrue(secondRead.isPresent());
//
//        // Проверяем, что НЕ было дополнительного обращения к БД
//        verify(transactionRepository, times(1)).findById(id);
//
//        // Очищаем кэш
//        evictCacheForTransaction(id);
//
//        // Проверяем, что кэш очищен
//        assertCacheEmpty(id);
//    }
//
//    private void evictCacheForTransaction(Long id) {
//        Cache cache = cacheManager.getCache("transactions");
//        if (cache != null) {
//            cache.evict(id);
//        }
//    }
//
//    private void assertCacheEmpty(Long id) {
//        Cache cache = cacheManager.getCache("transactions");
//        if (cache != null) {
//            assertNull(cache.get(id, Object.class));
//        }
//    }
//
//    private void assertCacheContains(Long id, TransactionDtoWithId expected) {
//        Cache cache = cacheManager.getCache("transactions");
//        if (cache != null) {
//            Object cachedValue = cache.get(id, Object.class);
//            assertNotNull(cachedValue);
//
//            // Проверяем тип и содержимое
//            if (cachedValue instanceof TransactionDtoWithId) {
//                TransactionDtoWithId transaction = (TransactionDtoWithId) cachedValue;
//                assertEquals(expected.sum(), transaction.sum());
//                assertEquals(expected.transactionDate(), transaction.transactionDate());
//            } else {
//                fail("Cached value is not of type TransactionDtoWithId");
//            }
//        } else {
//            fail("Cache 'transactions' not found");
//        }
//    }
//}