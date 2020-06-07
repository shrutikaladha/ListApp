package co.inmobi.listapp.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import co.inmobi.listapp.ListApplication;
import co.inmobi.listapp.base.StateData;
import co.inmobi.listapp.dagger.ListComponent;
import co.inmobi.listapp.databinding.ActivityListBinding;
import co.inmobi.listapp.model.Post;
import co.inmobi.listapp.model.User;

public class ListActivity extends AppCompatActivity {

    @Inject
    ListViewModel listViewModel;

    ActivityListBinding activityListBinding;
    ListComponent listComponent;
    PostListAdapter postListAdapter;

    public static final String TAG = "ListActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listComponent = ((ListApplication) getApplicationContext()).getAppComponent().listComponent().create();
        listComponent.inject(this);

        activityListBinding = ActivityListBinding.inflate(getLayoutInflater());
        View view = activityListBinding.getRoot();
        setContentView(view);

        postListAdapter = new PostListAdapter();
        activityListBinding.rvPost.setLayoutManager(new LinearLayoutManager(this));
        activityListBinding.rvPost.setAdapter(postListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribeUI();
    }

    private void subscribeUI() {
        Log.d(TAG, "subscribeUI()");

        Observer<StateData<List<Post>>> postsListObserver = new Observer<StateData<List<Post>>>() {
            @Override
            public void onChanged(StateData<List<Post>> listStateData) {
                Log.d(TAG, "onChanged called for postList with state: " + listStateData.getStatus());
                switch (listStateData.getStatus()) {
                    case SUCCESS:
                        hideListProgressLoader();
                        List<Post> postList = listStateData.getData();
                        if (postList != null && postList.size() > 0) {
                            showRecyclerViewPostList();
                            postListAdapter.setPosts(postList);
                        } else {
                            showNoResultsFound();
                        }
                        break;
                    case ERROR:
                        hideListProgressLoader();
                        Throwable e = listStateData.getError();
                        showError();
                        break;
                    case LOADING:
                        showListProgressLoader();
                        break;
                    case COMPLETE:
                        hideListProgressLoader();
                        break;
                }
            }
        };

        Observer<StateData<List<User>>> userListObserver = new Observer<StateData<List<User>>>() {
            @Override
            public void onChanged(StateData<List<User>> listStateData) {
                Log.d(TAG, "onChanged called for userList with state: " + listStateData.getStatus());

                switch (listStateData.getStatus()) {
                    case SUCCESS:
                        List<User> userList = listStateData.getData();
                        int numUsers = userList == null ? 0 : userList.size();
                        showTotalNumberOfUsers(numUsers);
                        break;
                    default:
                        break;
                }
            }
        };

        listViewModel.getPosts().observe(this, postsListObserver);
        listViewModel.getUsers().observe(this, userListObserver);
    }

    private void showRecyclerViewPostList() {
        Log.d(TAG, "showRecyclerViewPostList()");
        activityListBinding.rvPost.setVisibility(View.VISIBLE);
    }

    private void showError() {
        Log.d(TAG, "showError()");
        activityListBinding.tvError.setVisibility(View.VISIBLE);
        activityListBinding.rvPost.setVisibility(View.GONE);
    }

    private void showNoResultsFound() {
        Log.d(TAG, "showNoResultsFound()");
        activityListBinding.tvNoResults.setVisibility(View.VISIBLE);
    }

    private void showTotalNumberOfUsers(int numUsers) {
        Log.d(TAG, "showTotalNumberOfUsers() : " + numUsers);

        activityListBinding.tvChars.setText(numUsers + "");
        activityListBinding.llNumChars.setVisibility(View.VISIBLE);
        activityListBinding.llBottomView.setVisibility(View.VISIBLE);
    }

    private void hideListProgressLoader() {
        Log.d(TAG, "hideListProgressLoader()");
        activityListBinding.pbList.setVisibility(View.GONE);
    }

    private void showListProgressLoader() {
        Log.d(TAG, "showListProgressLoader()");
        activityListBinding.pbList.setVisibility(View.VISIBLE);
    }
}
