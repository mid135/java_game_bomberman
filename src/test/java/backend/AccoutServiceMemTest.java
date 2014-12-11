package backend;

import backend.test_memory_base.AccoutServiceImplMemory;
import backend.test_memory_base.UserImplMemory;
import org.junit.Before;
import org.junit.Test;
import  static org.mockito.Mockito.*;

import javax.servlet.http.*;

import static org.junit.Assert.*;

public class AccoutServiceMemTest {
    private AccoutServiceImplMemory pool=new AccoutServiceImplMemory();
    private static HttpServletRequest request;
    private static HttpSession session;

    @Before
    public void createUsers() {
        UserImplMemory userImplMemory = new UserImplMemory("mid","123","em");
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        pool.register(userImplMemory);
    }
    @Test
    public void testRegister() throws Exception {
        assertEquals(1,pool.getUsers().size());
    }
    @Test
    public void testLogIn() throws Exception {
        when(request.getParameter("login")).thenReturn("mid");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("email")).thenReturn("em");

        pool.logIn("mid","123",request);
        assertEquals("mid",pool.getArraySessionId().get(request.getSession().getId()).getLogin());
    }
}