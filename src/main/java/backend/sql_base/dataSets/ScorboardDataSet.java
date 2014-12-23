package backend.sql_base.dataSets;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Abovyan on 21.12.14.
 */
@Entity
@Table(name="scorboard")
public class ScorboardDataSet implements Serializable {
    private static final long serialVersionUID = -87089714326132798L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="userId")
    private long userId;
    @Column(name="userName")
    private String userName;
    @Column(name="score")
    private int score;

    public ScorboardDataSet() {

    }

    public ScorboardDataSet(long userId, String userName, int score) {
        this.id = -1;
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }

    public ScorboardDataSet(long id, long userId, String userName, int score) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
