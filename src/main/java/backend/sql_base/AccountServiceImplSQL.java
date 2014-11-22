package backend.sql_base;

import backend.AccountService;
import backend.enums.AccountEnum;
import backend.sql_base.dao.UserDataSetDAO;
import backend.sql_base.dataSets.UserDataSet;
import backend.test_memory_base.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import resources.ResourceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mid on 23.10.14.
 */

//this should be an extention of userPool
public class AccountServiceImplSQL implements AccountService {
    private Map<String, User> arraySessionId = new HashMap<>();//все сессии пользователей - sessionId/UserImplMemory
    private Map<String, String> propertyForConfiguration = null;
    private UserDataSetDAO dao;
    private Configuration configuration;
    private SessionFactory sessionFactory;

    public AccountServiceImplSQL() {
        propertyForConfiguration = ResourceFactory.instance().getResource("./data/propertyForConfiguration.xml"); //настройки работы класса Configuration
        configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        sessionFactory = createSessionFactory(configuration);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //System.out.append(transaction.getLocalStatus().toString()).append('\n');
        session.close();

        dao = new UserDataSetDAO(sessionFactory);
    }

    public SessionFactory createSessionFactory(Configuration configuration) {
        for (Map.Entry<String, String> property : propertyForConfiguration.entrySet()) {
            configuration.setProperty(property.getKey(), property.getValue()); //установка свойств конфигурации
        }

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public Map<String, User> getArraySessionId() {return  this.arraySessionId;};

    @Override
    public Map getUsers() {return dao;};

    @Override
    public synchronized AccountEnum checkRegistration(String userName) {//проверка регистрации пользователя

        if (dao.readByName(userName) != null) {
            return AccountEnum.UserRegistered;
        } else  {
            return AccountEnum.UserNotRegistered;
        }
    }

    @Override
    public AccountEnum checkLogIn (HttpServletRequest request) {//проверка залогинен ли пользватель
        if (arraySessionId.containsKey(request.getSession().getId())) {//есть ли у данного sid юзер
            return AccountEnum.UserLoggedIn;//TODO все не может быть так просто
        } else {
            return AccountEnum.UserNotLoggedIn;
        }
    }

    @Override
    public AccountEnum logIn(String login, String password,HttpServletRequest request) {//залогинивагние пользователя
        UserDataSet user = null;
        if (checkLogIn(request) == AccountEnum.UserNotLoggedIn) {
            if ( (this.checkRegistration(login) == AccountEnum.UserRegistered) ) {// TODO эта проверка не совсем корректна
                if ((user = dao.readByName(login)).getPassword().equals(password)) {
                    arraySessionId.put(request.getSession().getId(), user);
                    return AccountEnum.LogInSuccess;
                } else {
                    return AccountEnum.LogInFail;
                }
            } else {
                return AccountEnum.LogInFail;
            }
        } else {
            return AccountEnum.UserLoggedIn;//TODO обдумать что делать,  если юзер уже залогинен//изменение в свзя с enum
        }
    }

    @Override
    public AccountEnum logOff (HttpServletRequest request) {//разлогинивание пользователя
        User cur;
        if (arraySessionId.containsKey(request.getSession().getId())) {
            cur = arraySessionId.get(request.getSession().getId());
            if ( (this.checkRegistration(cur.getLogin()) == AccountEnum.UserRegistered)
                    && (this.checkLogIn(request) == AccountEnum.UserLoggedIn) ) {
                arraySessionId.remove(request.getSession().getId());
                return AccountEnum.LogOffSuccess;
            } else {
                return AccountEnum.LogOffFail;
            }
        } else {
            return AccountEnum.LogOffFail;
        }
    }

    @Override
    public AccountEnum register(User user) {//регистрация пользователя
        if (dao.readByName(user.getLogin()) != null) {
            return AccountEnum.UserAlreadyExists;
        } else {
            dao.save((UserDataSet)user);
            return AccountEnum.RegisterSuccess;
        }
    }

    @Override
    public AccountEnum editProfile(User user) {
        return AccountEnum.EditSuccess;
    }
}
