package com.shinkle.userapi.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {


    @Mock
    Map<Integer, User> database;

    @InjectMocks
    UserService userService;

    @Test
    void shouldStoreUser() {
        String expectedName = "Caleb";
        User expectedUser = new User(2, expectedName);
        when(database.size()).thenReturn(1);
        when(database.put(any(), any())).thenReturn(null);

        User actualUser = userService.create(expectedName);

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void shouldStoreUsersWithUniqueIds() {
        String firstExpectedName = "Caleb";
        int firstExpectedId = 2;
        User firstExpectedUser = new User(firstExpectedId, firstExpectedName);
        String secondExpectedName = "Justin";
        int secondExpectedId = 3;
        User secondExpectedUser = new User(secondExpectedId, secondExpectedName);
        when(database.size()).thenReturn(1, 2);
        when(database.put(any(), any())).thenReturn(null);

        User firstActualUser = userService.create(firstExpectedName);
        User secondActualUser = userService.create(secondExpectedName);

        assertThat(firstActualUser).isEqualTo(firstExpectedUser);
        assertThat(secondActualUser).isEqualTo(secondExpectedUser);
    }

    @Test
    void shouldFindUser() {
        int expectedId = 4;
        User expectedUser = new User(expectedId, "Alex");
        when(database.get(anyInt())).thenReturn(new User(expectedId, "Alex"));

        User actualUser = userService.findById(expectedId);

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void shouldFailWhenItCantFindUser() {
        int unexpectedId = 5;
        when(database.get(anyInt()))
                .thenThrow(new UserNotFound("Could not find User with id: " + unexpectedId));

        assertThrows(UserNotFound.class, () -> userService.findById(unexpectedId));
    }
}