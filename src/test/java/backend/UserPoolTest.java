package backend;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserPoolTest {
    private UserPool pool=new UserPool();
    @Before
    public void createUsers() {
        
        User user = new User();
        user.password="123";
        user.email="123";
        user.login="123";
        pool.register(user);
    }
    @Test
    public void testRegister() throws Exception {
        assertEquals(1,pool.getUsers().size());
    }
}