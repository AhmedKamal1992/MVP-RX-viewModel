package ahmed.com.mvp_rx_viewmodel.injection.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import ahmed.com.mvp_rx_viewmodel.data.database.QuestionDao;
import ahmed.com.mvp_rx_viewmodel.data.database.StackOverflowDb;
import ahmed.com.mvp_rx_viewmodel.utils.Constants;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Kamal on 21-11-2017.
 */
@Module
public class DatabaseModule
{
    private static final String DATABASE = "database_name";

    @Provides
    @Named(DATABASE)
    String provideDatabaseName() {
        return Constants.DATABASE_NAME;
    }

    @Provides
    @Singleton
    StackOverflowDb provideStackOverflowDao(Context context, @Named(DATABASE) String databaseName)
    {
        return Room.databaseBuilder(context, StackOverflowDb.class, databaseName).build();
    }

    @Provides
    @Singleton
    QuestionDao provideQuestionDao(StackOverflowDb stackOverflowDb)
    {
        return stackOverflowDb.questionDao();
    }
}
