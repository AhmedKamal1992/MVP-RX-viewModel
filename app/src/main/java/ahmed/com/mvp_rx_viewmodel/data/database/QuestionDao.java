package ahmed.com.mvp_rx_viewmodel.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.utils.Constants;
import io.reactivex.Flowable;

import static ahmed.com.mvp_rx_viewmodel.utils.DBQueries.DELETE_FROM;
import static ahmed.com.mvp_rx_viewmodel.utils.DBQueries.SELECT_FROM;
import static ahmed.com.mvp_rx_viewmodel.utils.DBQueries.WHERE;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@Dao
public interface QuestionDao
{
    @Query("SELECT * FROM " + Constants.QUESTION_TABLE_NAME)
    Flowable<List<Question>> getAllQuestions();

    @Query("SELECT * FROM " + Constants.QUESTION_TABLE_NAME + " WHERE id == :id")
    Flowable<Question> getQuestionById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Query("DELETE FROM " + Constants.QUESTION_TABLE_NAME)
    void deleteAll();
}
