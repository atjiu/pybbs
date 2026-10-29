package co.yiiu.pybbs.controller.api;

import co.yiiu.pybbs.exception.ApiException;
import co.yiiu.pybbs.model.User;
import co.yiiu.pybbs.service.ICodeService;
import co.yiiu.pybbs.service.ISystemConfigService;
import co.yiiu.pybbs.service.IUserService;
import co.yiiu.pybbs.util.Result;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class SettingsApiControllerTest {

    private SettingsApiController controller;
    private IUserService userService;
    private ICodeService codeService;
    private ISystemConfigService systemConfigService;
    private User user;

    @Before
    public void setUp() throws Exception {
        controller = new SettingsApiController();
        userService = mock(IUserService.class);
        codeService = mock(ICodeService.class);
        systemConfigService = mock(ISystemConfigService.class);
        user = new User();
        user.setId(42);
        user.setToken("fixture-token");
        user.setPassword("existing-password-hash");

        Field baseUserService = BaseApiController.class.getDeclaredField("userService");
        baseUserService.setAccessible(true);
        baseUserService.set(controller, userService);
        ReflectionTestUtils.setField(controller, "userService", userService);
        ReflectionTestUtils.setField(controller, "codeService", codeService);
        ReflectionTestUtils.setField(controller, "systemConfigService", systemConfigService);
        when(userService.selectByToken("fixture-token")).thenReturn(user);

        Map<String, String> config = new HashMap<>();
        config.put("email_mobile_code_count", "5");
        config.put("name", "PyBBS");
        when(systemConfigService.selectAllConfig()).thenReturn(config);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("token", "fixture-token");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @After
    public void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void emailChangeCodeIsBoundToAuthenticatedUser() {
        when(codeService.count("change@example.com", null)).thenReturn(0);
        when(codeService.sendEmail(eq(42), eq("change@example.com"), anyString(), anyString())).thenReturn(true);

        Result result = controller.sendEmailCode("change@example.com");

        assertEquals(Integer.valueOf(200), result.getCode());
        verify(codeService).sendEmail(eq(42), eq("change@example.com"), anyString(), anyString());
    }

    @Test
    public void emailChangeCodeHonorsConfiguredDailyLimit() {
        when(codeService.count("change@example.com", null)).thenReturn(6);

        Result result = controller.sendEmailCode("change@example.com");

        assertEquals(Integer.valueOf(201), result.getCode());
        verify(codeService, never()).sendEmail(anyInt(), anyString(), anyString(), anyString());
    }

    @Test
    public void passwordChangeRejectsPasswordThatDoesNotMeetRegistrationPolicy() {
        when(userService.selectByIdWithoutCache(42)).thenReturn(user);
        Map<String, String> body = new HashMap<>();
        body.put("oldPassword", "old-password");
        body.put("newPassword", "short1A");

        try {
            controller.updatePassword(body);
            fail("Expected the weak password to be rejected");
        } catch (ApiException expected) {
            verify(userService, never()).update(any(User.class));
        }
    }
}
