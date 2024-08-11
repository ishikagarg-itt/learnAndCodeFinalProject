package org.example.Services;

import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Entity.User;
import org.example.Exception.NotFoundException;
import org.example.Repository.UserRepository;
import org.example.utils.AuthenticationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testLoginSucceeds() {
        try (MockedStatic<AuthenticationUtils> mockedUtils = Mockito.mockStatic(AuthenticationUtils.class)) {
            String username = "tester";
            String employeeId = "12345";
            String sessionToken = "sessionToken";

            LoginRequestDto loginRequest = new LoginRequestDto();
            loginRequest.setUserName(username);
            loginRequest.setEmployeeId(employeeId);
            User user = new User();
            when(userRepository.findByUserNameAndEmployeeId(username, employeeId)).thenReturn(Optional.of(user));
            mockedUtils.when(() -> AuthenticationUtils.generateSessionToken(user)).thenReturn(sessionToken);

            LoginResponseDto loginResponse = authenticationService.login(loginRequest);

            assertNotNull(loginResponse);
            assertEquals(sessionToken, loginResponse.getSessionToken());

            verify(userRepository, times(1)).findByUserNameAndEmployeeId(username, employeeId);
            mockedUtils.verify(() -> AuthenticationUtils.generateSessionToken(user), times(1));
        }
    }

    @Test(expected = NotFoundException.class)
    public void testLoginFails() {
        String username = "tester";
        String employeeId = "12345";

        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setUserName("testUser");
        loginRequest.setEmployeeId("testEmployeeId");
        lenient().when(userRepository.findByUserNameAndEmployeeId(username, employeeId)).thenReturn(Optional.empty());

        authenticationService.login(loginRequest);
    }
}
