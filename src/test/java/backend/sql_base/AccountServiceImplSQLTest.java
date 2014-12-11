package backend.sql_base;

import backend.enums.AccountEnum;
import backend.sql_base.dataSets.UserDataSet;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceImplSQLTest extends TestCase {
    private AccountServiceImplSQL pool = new AccountServiceImplSQL();;
    private static HttpServletRequest request;
    private static HttpSession session;
    private AccountEnum accountEnum;

    @Before
    public void setUp() throws Exception{
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

    }

    @Test
    public void testRegister() throws Exception{
        UserDataSet user = new UserDataSet("mid","123","em");
        accountEnum = pool.register(user);
        assertEquals(AccountEnum.RegisterSuccess, accountEnum);
    }

    @Test
    public void testLogIn() throws Exception {
        when(request.getParameter("login")).thenReturn("mid1");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("email")).thenReturn("em");
        UserDataSet user = new UserDataSet("mid1","123","em");
        accountEnum = pool.register(user);
        pool.logIn("mid1","123",request);
        assertEquals("mid1",pool.getArraySessionId().get(request.getSession().getId()).getLogin());
    }

    @Test
    public void testFailLogin(){
        UserDataSet user = new UserDataSet("mid","123","em");
        when(request.getSession()).thenReturn(session);
        accountEnum = pool.register(user);
        assertEquals(AccountEnum.UserAlreadyExists,accountEnum);
    }
}