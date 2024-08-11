package org.example.Controller;

import org.example.Dto.LoginRequestDto;
import org.example.Dto.LoginResponseDto;
import org.example.Services.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    public void testLoginSucceeds() {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setUserName("testUser");
        loginRequest.setEmployeeId("testEmployeeId");

        LoginResponseDto expectedResponse = new LoginResponseDto();
        expectedResponse.setSessionToken("validToken");

        when(authenticationService.login(loginRequest)).thenReturn(expectedResponse);

        LoginResponseDto actualResponse = authenticationController.login(loginRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(authenticationService, times(1)).login(loginRequest);
    }

    @Test
    public void testLoginFails() {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setUserName("testUser");
        loginRequest.setEmployeeId("testEmployeeId");

        when(authenticationService.login(loginRequest)).thenReturn(null);

        LoginResponseDto actualResponse = authenticationController.login(loginRequest);

        assertEquals(null, actualResponse);
        verify(authenticationService, times(1)).login(loginRequest);
    }
}
