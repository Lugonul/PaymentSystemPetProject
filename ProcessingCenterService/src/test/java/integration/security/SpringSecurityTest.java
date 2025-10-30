package integration.security;


//@SpringBootTest(classes = {ProcessingCenterServiceRunner.class})
//@AutoConfigureMockMvc
//public class SpringSecurityTest {
//
//    @MockitoBean
//    private CardService cardService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setup() {
//        when(cardService.readAll()).thenReturn(Collections.emptyList());
//    }
//
//    @Test
//    void anonymousAccessToPublicEndpoint_shouldSucceed() throws Exception {
//        mockMvc.perform(get("/api/cards"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void anonymousAccessToAdminEndpoint_shouldUnauthorized() throws Exception {
//        mockMvc.perform(get("/admin/acquiringBanks"))
//                .andExpect(status().isUnauthorized());
//    }
//
//
//    @Test
//    @WithMockUser
//    void userAccessToAdminEndpoint_shouldForbidden() throws Exception {
//        mockMvc.perform(get("/admin/acquiringBanks"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    void adminAccessToAdminEndpoint_shouldSucceed() throws Exception {
//        mockMvc.perform(get("/admin/acquiringBanks"))
//                .andExpect(status().isOk());
//    }
//}