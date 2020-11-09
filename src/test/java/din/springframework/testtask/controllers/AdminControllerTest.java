package din.springframework.testtask.controllers;

import din.springframework.testtask.model.User;
import din.springframework.testtask.services.CurrencyConverterHistoryServiceImpl;
import din.springframework.testtask.services.CurrencyService;
import din.springframework.testtask.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableSpringDataWebSupport
class AdminControllerTest {

    @Mock
    UserServiceImpl userServiceImpl;

    @Mock
    CurrencyConverterHistoryServiceImpl historyService;

    @Mock
    CurrencyService currencyService;

    AdminController adminController;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        adminController = new AdminController(userServiceImpl, historyService, currencyService);
    }

    @Test
    void testUserList() throws Exception {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        PageRequest pageRequest = PageRequest.of(1, 10);
        List<User> userList = Arrays.asList(user1, user2);
        Page<User> userPage = new PageImpl<>(userList, pageRequest, userList.size());

        when(userServiceImpl.findAll(Mockito.any(Pageable.class))).thenReturn(userPage);

        String viewName = adminController.userList(pageRequest, model);

        assertEquals("admin", viewName);
        verify(userServiceImpl, times(1)).findAll(any(Pageable.class));

    }

    @Test
    void getUser() {
        //given


        //when

        //then
    }
}