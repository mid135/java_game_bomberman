package backend;

import backend.test_memory_base.AccoutServiveImpMemory;
import org.junit.Before;
import org.junit.Test;
import  static org.mockito.Mockito.*;

import javax.servlet.http.*;

import static org.junit.Assert.*;

public class UserPoolMemTest {
    private AccoutServiveImpMemory pool=new AccoutServiveImpMemory();
    private static HttpServletRequest request;
    private static HttpSession session;

    @Before
    public void createUsers() {
        User user = new User("123","123","123");
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
        assertEquals("123",pool.getArraySessionId().get(request.getSession().getId()).getLogin());
    }
}