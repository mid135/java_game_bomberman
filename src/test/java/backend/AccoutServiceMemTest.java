package backend;

import backend.test_memory_base.AccountServiceImpMemory;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;
import javax.servlet.http.*;

public class AccoutServiceMemTest {
    private AccountService pool=new AccountServiceImpMemory();
    private static HttpServletRequest request;
    private static HttpSession session;

    @Before
    public void createUsers() {
        User user = new User("mid","123","em");
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn("mid");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("email")).thenReturn("em");
        pool.register(user);
    }
    @Test
    public void testRegister() throws Exception {
        assertEquals(1,pool.getUsers().size());
        User user = new User("mid","123","em");
        assertEquals(true,user.equals(pool.getUsers().get("mid")));
    }

    @Test
    public void testLogIn() throws Exception {
        pool.logIn("mid","123",request);
        assertEquals("mid",pool.getArraySessionId().get(request.getSession().getId()).getLogin());
        pool.logOff(request);
        pool.logIn("mid","1243",request);//FAIL LOGIN
        assertEquals(0,pool.getArraySessionId().size());
    }
    @Test
    public void testLogOf() throws Exception {
        pool.logIn("mid","123",request);
        pool.logOff(request);
        assertEquals(0,pool.getArraySessionId().size());
       //users
    }


}