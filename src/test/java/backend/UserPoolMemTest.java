package backend;

import backend.test_memory_base.UserPool_mem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
    final private HttpServletRequest request = Mockito.mock(HttpServletRequest.class) ;
    final private static HttpSession session = Mockito.mock(HttpSession.class);
    @Before
    public void createUsers() {
        User user = new User();
        user.password="123";
        user.email="123";
        user.login="123";

    }
    @Test
    public void testRegister() throws Exception {
        assertEquals(1,pool.getUsers().size());
    }
    @Test
    public void testLogIn() throws Exception {

        assertEquals("123",pool.getArraySessionId().get(request.getSession().getId()).login);

    }
}