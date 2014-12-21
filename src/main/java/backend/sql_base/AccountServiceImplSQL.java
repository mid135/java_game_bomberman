package backend.sql_base;

import backend.AccountService;
import backend.enums.AccountEnum;
import backend.sql_base.dao.ScorboardDataSetDao;
import backend.sql_base.dao.UserDataSetDAO;
import backend.sql_base.dataSets.ScorboardDataSet;
import backend.sql_base.dataSets.UserDataSet;
import backend.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import resources.ResourceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by mid on 23.10.14.
 */

//this should be an extention of userPool
public class AccountServiceImplSQL implements AccountService {
    private Map<String, User> arraySessionId = new HashMap<>();//все сессии пользователей - sessionId/UserImplMemory
    private Map<String, String> propertyForConfiguration = null;
    private UserDataSetDAO daoUser;
    private ScorboardDataSetDao daoScorboard;
    private Configuration configurationUser;
    private Configuration configurationScorboard;
    private SessionFactory sessionFactoryUser;
    private SessionFactory sessionFactoryScoreboard;

    public AccountServiceImplSQL() {
        propertyForConfiguration = ResourceFactory.instance().getResource("./data/propertyForConfiguration.xml"); //настройки работы класса Configuration
        configurationUser = new Configuration();
        configurationScorboard = new Configuration();
        configurationUser.addAnnotatedClass(UserDataSet.class);
        configurationScorboard.addAnnotatedClass(ScorboardDataSet.class);

        sessionFactoryUser = createSessionFactory(configurationUser);
        sessionFactoryScoreboard = createSessionFactory(configurationScorboard);
        //Session session = sessionFactory.openSession();
        //Transaction transaction = session.beginTransaction();
        //System.out.append(transaction.getLocalStatus().toString()).append('\n');
        //session.close();

        daoUser = new UserDataSetDAO(sessionFactoryUser);
        daoScorboard = new ScorboardDataSetDao(sessionFactoryScoreboard);
        //daoScorboard.save(new ScorboardDataSet(2,"Max", 5));
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
    public Map getUsers() {return daoUser;}

    @Override
    public synchronized AccountEnum checkRegistration(String userName) {//проверка регистрации пользователя

        if (daoUser.readByName(userName) != null) {
            return AccountEnum.UserRegistered;
        } else  {
            return AccountEnum.UserNotRegistered;
        }
    }
    @Override
    public AccountEnum checkLogIn (HttpServletRequest request) {//проверка залогинен ли пользватель
        if (arraySessionId.containsKey(request.getSession().getId())) {//есть ли у данного sid юзер
            return AccountEnum.UserLoggedIn;
        } else {
            return AccountEnum.UserNotLoggedIn;
        }
    }
    @Override
    public AccountEnum logIn(String login, String password,HttpServletRequest request) {//залогинивагние пользователя
        UserDataSet user = null;
        if (checkLogIn(request) == AccountEnum.UserNotLoggedIn) {
            if ( (this.checkRegistration(login) == AccountEnum.UserRegistered) ) {
                if ((user = daoUser.readByName(login)).getPassword().equals(password)) {
                    arraySessionId.put(request.getSession().getId(), user);
                    return AccountEnum.LogInSuccess;
                } else {
                    return AccountEnum.LogInFail;
                }
            } else {
                return AccountEnum.LogInFail;
            }
        } else {
            return AccountEnum.UserLoggedIn;
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
        if (daoUser.readByName(user.getLogin()) != null) {
            return AccountEnum.UserAlreadyExists;
        } else {
            daoUser.save((UserDataSet)user);
            return AccountEnum.RegisterSuccess;
        }
    }
    @Override
    public AccountEnum editProfile(User user) {
        return AccountEnum.EditSuccess;
    }

    @Override
    public JSONObject getScoreboard() {
        //TODO нарек пожалуйста сделай эту функцию, я поле уже добавил.
        //нужен limit 10 и сортировка по убыванию - чтоб максимальные очки выводить.

        JSONArray res = new JSONArray();

        try {
            List<ScorboardDataSet> scorboards = daoScorboard.readTop10();
            for (ScorboardDataSet scorboard : scorboards) {
                try {
                    JSONObject var = new JSONObject();
                    var.put( "score", scorboard.getScore() );
                    var.put( "user", scorboard.getUserName() );
                    res.put(var);
                } catch (JSONException e) {
                }
            }
        }catch (Exception e){

        }

        JSONObject fin = new JSONObject();
        try {
            fin.put("status", "1");
            fin.put("scores", res);
        } catch (JSONException e) {
        }
        return fin;
    }
}
