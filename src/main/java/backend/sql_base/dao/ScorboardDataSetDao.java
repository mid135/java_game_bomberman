package backend.sql_base.dao;

import backend.sql_base.dataSets.ScorboardDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by Abovyan on 21.12.14.
 */
public class ScorboardDataSetDao {
    private SessionFactory sessionFactory;

    public ScorboardDataSetDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    synchronized public void save(ScorboardDataSet dataSet) {
        Session session = sessionFactory.openSession();
        Transaction trx = session.beginTransaction();
        session.save(dataSet);
        trx.commit();
        session.close();
    }

    synchronized public ScorboardDataSet read(long id) {
        Session session = sessionFactory.openSession();
        return (ScorboardDataSet) session.load(ScorboardDataSet.class, id);
    }


    @SuppressWarnings("unchecked")
    synchronized public List<ScorboardDataSet> readTop10() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ScorboardDataSet.class).addOrder(Order.desc("score")).setMaxResults(10);
        return (List<ScorboardDataSet>) criteria.list();
    }
}
