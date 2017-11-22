package ahmed.com.mvp_rx_viewmodel.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ahmed.com.mvp_rx_viewmodel.data.model.Question;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */

@Database(entities = Question.class, version = 1)
public abstract class StackOverflowDb extends RoomDatabase
{

    public abstract QuestionDao questionDao();
}
