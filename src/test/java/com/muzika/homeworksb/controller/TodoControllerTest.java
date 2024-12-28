package com.muzika.homeworksb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muzika.homeworksb.dto.TodoCreateDto;
import com.muzika.homeworksb.dto.TodoResponseDto;
import com.muzika.homeworksb.enums.PriorityEnum;
import com.muzika.homeworksb.enums.RoleEnum;
import com.muzika.homeworksb.model.User;
import com.muzika.homeworksb.model.UserRole;
import com.muzika.homeworksb.repository.UserRepository;
import com.muzika.homeworksb.repository.UserRoleRepository;
import com.muzika.homeworksb.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private UserService userService;

    @SneakyThrows
    static void teardown(@Autowired DataSource dataSource, @Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(applicationContext)
            .build();

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                connection,
                new ClassPathResource("database/todo/todos-clean.sql")
            );
        }
    }

    @Transactional
    @BeforeAll
    static void beforeAll(
        @Autowired DataSource dataSource,
        @Autowired WebApplicationContext applicationContext,
        @Autowired UserRepository userRepository,
        @Autowired UserRoleRepository userRoleRepository
    ) {
        teardown(dataSource, applicationContext);

        UserRole userRole = new UserRole();
        userRole.setName(String.valueOf(RoleEnum.CLIENT));
        userRoleRepository.save(userRole);

        User user = new User();
        user.setEmail("testUser");
        user.setPassword("password");
        user.setRoles(List.of(userRole));
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            AuthorityUtils.createAuthorityList(userRole.getName())
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterAll
    static void afterAll(
        @Autowired DataSource dataSource,
        @Autowired WebApplicationContext applicationContext
    ){
        teardown(dataSource, applicationContext);
    }

    @Test
    @DisplayName("Create a new todo - valid request")
    @WithMockUser(username = "testUser", roles = {"CLIENT"})
    void createTodo_ValidRequest_Success() throws Exception {
        // Arrange
        TodoCreateDto requestDto = new TodoCreateDto(
            "Wake up",
            "",
            LocalDateTime.of(2024, 12, 16, 11, 30),
            PriorityEnum.LOW
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        // Act
        MvcResult result = mockMvc.perform(post("/todos")
                .principal(authenticatedUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isOk())
            .andReturn();

        // Assert
        TodoResponseDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), TodoResponseDto.class);
        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.id());
        Assertions.assertNotNull(responseDto.title());
        Assertions.assertNotNull(responseDto.description());
        Assertions.assertNotNull(responseDto.dueDate());
        Assertions.assertNotNull(responseDto.priority());
        Assertions.assertNull(responseDto.status());
        Assertions.assertNotNull(responseDto.createdDate());
        Assertions.assertNull(responseDto.updatedDate());
        Assertions.assertNotNull(responseDto.userId());
    }

    @Test
    @DisplayName("Create a new todo with not existed priority - failed request")
    void createTodo_WrongPriority_Fail() throws Exception {
        // Arrange
        String invalidRequest = """
        {
            "title": "Wake up",
            "dueDate": "2024-12-16T11:30:00",
            "priority": "111"
        }
        """;

        // Act
        MvcResult result = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andReturn();

        // Assert
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponse errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);

        Assertions.assertEquals("Bad Request", errorResponse.title());
        Assertions.assertEquals(400, errorResponse.status());
        Assertions.assertEquals("/todos", errorResponse.instance());
        Assertions.assertEquals("Failed to read request", errorResponse.detail());
    }

    public record ErrorResponse(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        Object properties
    ) {}

    @Test
    @DisplayName("Create a new todo with extra field and without description - success request")
    @WithMockUser(username = "testUser", roles = {"client"})
    void createTodo_ExtraFieldAndEmptyDescription_Success() throws Exception {
        // Arrange
        String invalidRequest = """
        {
            "title": "Wake up",
            "dueDate": "2024-12-16T11:30:04",
            "priority": "LOW",
            "extra": "extra"
        }
        """;

        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        // Act
        MvcResult result = mockMvc.perform(post("/todos")
                .principal(authenticatedUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isOk())
            .andReturn();

        // Assert
        TodoResponseDto responseDto = objectMapper.readValue(result.getResponse().getContentAsString(), TodoResponseDto.class);

        Assertions.assertNotNull(responseDto);
        Assertions.assertNotNull(responseDto.id());
        Assertions.assertNotNull(responseDto.title());
        Assertions.assertEquals("Wake up", responseDto.title());
        Assertions.assertNull(responseDto.description());
        Assertions.assertNotNull(responseDto.dueDate());
        Assertions.assertEquals("2024-12-16T11:30:04", responseDto.dueDate().toString());
        Assertions.assertNotNull(responseDto.priority());
        Assertions.assertEquals("LOW", responseDto.priority().toString());
        Assertions.assertNull(responseDto.status());
        Assertions.assertNotNull(responseDto.createdDate());
        Assertions.assertNull(responseDto.updatedDate());
        Assertions.assertNotNull(responseDto.userId());
    }

    @Test
    @DisplayName("Create a new todo without title - failed request")
    void createTodo_EmptyTitle_Fail() throws Exception {
        // Arrange
        String invalidRequest = """
        {
            "dueDate": "2024-12-16T11:30:00",
            "priority": "LOW"
        }
        """;

        // Act
        MvcResult result = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andReturn();

        // Assert
        String responseContent = result.getResponse().getContentAsString();
        ValidationErrorResponse errorResponse = objectMapper.readValue(responseContent, ValidationErrorResponse.class);
        Assertions.assertEquals("BAD_REQUEST", errorResponse.status());
        Assertions.assertTrue(errorResponse.errors().contains("Field `title`:Title can't be empty"));
    }

    public record ValidationErrorResponse(
        //LocalDateTime time,  // Commented to avoid error with `InvalidDefinitionException: Java 8 date/time type java.time.LocalDateTime` what is not a goal to check.
        String status,
        List<String> errors
    ) {}

    @Test
    @DisplayName("Create a new todo without due date - failed request")
    void createTodo_EmptyDueDate_Fail() throws Exception {
        // Arrange
        String invalidRequest = """
        {
            "title": "Wake up"
        }
        """;

        // Act
        MvcResult result = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andReturn();

        // Assert
        String responseContent = result.getResponse().getContentAsString();
        ValidationErrorResponse errorResponse = objectMapper.readValue(responseContent, ValidationErrorResponse.class);

        Assertions.assertEquals("BAD_REQUEST", errorResponse.status());
        Assertions.assertTrue(errorResponse.errors().contains("Field `dueDate`:Due date is required"));
    }
}
