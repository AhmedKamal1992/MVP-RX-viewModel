package ahmed.com.mvp_rx_viewmodel.presentation.questions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ahmed.com.mvp_rx_viewmodel.R;
import ahmed.com.mvp_rx_viewmodel.data.model.Question;
import ahmed.com.mvp_rx_viewmodel.injection.question.DaggerQuestionsComponent;
import ahmed.com.mvp_rx_viewmodel.injection.question.QuestionsPresenterModule;
import ahmed.com.mvp_rx_viewmodel.presentation.base.BaseActivity;
import ahmed.com.mvp_rx_viewmodel.presentation.base.BasePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends BaseActivity implements QuestionsContract.View
{
    @BindView(R.id.question_recycler)
    RecyclerView questionRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.notiText)
    TextView notiText;

    private QuestionAdapter adapter;
    @Inject
    QuestionsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ButterKnife.bind(this);
        DaggerQuestionsComponent.builder()
                .questionsPresenterModule(new QuestionsPresenterModule(this))
                .questionInteractorComponent(getQuestionRepositoryComponent())
                .build()
                .inject(this);
        initUI();

    }


    private void initUI()
    {
        // Setup recycler view
        adapter = new QuestionAdapter(new ArrayList<>());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        questionRecyclerView.setLayoutManager(layoutManager);
        questionRecyclerView.setAdapter(adapter);
        questionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(
                (view, position) -> presenter.getQuestion(adapter.getItem(position).getId()));

        // Refresh layout
        refreshLayout.setOnRefreshListener(() -> presenter.loadQuestions(true));
        // Set notification text visible first
        notiText.setVisibility(View.GONE);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.questions_menu, menu);

        // Setup search widget in action bar
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                presenter.search(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public void showQuestions(List<Question> questions)
    {
        notiText.setVisibility(View.GONE);
        adapter.replaceData(questions);
    }

    @Override
    public void clearQuestions()
    {
        adapter.clearData();
    }

    @Override
    public void showNoDataMessage()
    {
        showNotification("No data .. please pull to refresh");
    }

    @Override
    public void showErrorMessage(String error)
    {
        showNotification(error);
    }

    @Override
    public void showQuestionDetail(Question question)
    {
        Toast.makeText(this , question.getTitle() , Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideLoading()
    {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showEmptySearchResult()
    {
        showNotification("No Results");
    }

    private void showNotification(String message){
        notiText.setVisibility(View.VISIBLE);
        notiText.setText(message);
    }
}
