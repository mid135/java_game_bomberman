package backend;

import backend.test_memory_base.UserPool_mem;
import org.junit.Before;
import org.junit.Test;
import  static org.mockito.Mockito.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.junit.Assert.*;

public class UserPoolMemTest {
    private UserPool_mem pool=new UserPool_mem();
    private static HttpServletRequest request;
    private static HttpSession session;

    @Before
    public void createUsers() {
        User user = new User();
        user.password="123";
        user.email="123";
        user.login="123";
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        pool.register(user);
    }
    @Test
    public void testRegister() throws Exception {
        assertEquals(1,pool.getUsers().size());
    }
    @Test
    public void testLogIn() throws Exception {
        when(request.getParameter("login")).thenReturn("123");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("email")).thenReturn("123");

        pool.logIn("123","123",request);
        assertEquals("123",pool.getArraySessionId().get(request.getSession().getId()).login);
    }
}