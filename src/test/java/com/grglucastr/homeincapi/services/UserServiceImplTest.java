package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.UserMocks;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.repositories.UserRepository;
import com.grglucastr.homeincapi.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testFindById(){

        final User singleUser = UserMocks.getSingleUser();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(singleUser));

        final Optional<User> optUser = userService.findById(7834L);
        assertThat(optUser, notNullValue());
        assertThat(optUser.isPresent(), is(true));
        assertThat(optUser.get().getName(), is("Admin 222"));
        assertThat(optUser.get().getEmail(), is("contact@domain.com"));


    }
}
