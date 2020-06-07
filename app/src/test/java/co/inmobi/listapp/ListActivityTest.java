package co.inmobi.listapp;

import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;

import co.inmobi.listapp.base.StateData;
import co.inmobi.listapp.base.StateLiveData;
import co.inmobi.listapp.model.Post;
import co.inmobi.listapp.model.User;
import co.inmobi.listapp.ui.list.ListActivity;
import co.inmobi.listapp.ui.list.ListViewModel;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class ListActivityTest {
    private ListActivity activity;
    private ActivityController<ListActivity> activityController;
    @Mock
    private ListViewModel listViewModel;

    @Mock
    private StateLiveData<List<Post>> postLiveData;

    @Mock
    private StateLiveData<List<User>> userLiveData;

    @Captor
    private ArgumentCaptor<Observer<StateData<List<Post>>>> postObserverCaptor;

    @Captor
    private ArgumentCaptor<Observer<StateData<List<User>>>> userObserverCaptor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    ArrayList<Post> postList = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();

    @Before
    public void setup() {
        Mockito.when(listViewModel.getPosts()).thenReturn(postLiveData);
        Mockito.when(listViewModel.getUsers()).thenReturn(userLiveData);

        activityController = Robolectric.buildActivity(ListActivity.class);
        activity = activityController.get();
        activityController.create();
        activity.setTestViewModel(listViewModel);
        activityController.start();

        addPosts();
        addUsers();

        Mockito.verify(postLiveData).observe(ArgumentMatchers.any(LifecycleOwner.class), postObserverCaptor.capture());
        Mockito.verify(userLiveData).observe(ArgumentMatchers.any(LifecycleOwner.class), userObserverCaptor.capture());
    }

    //Add dummy post data for mocking.
    private void addPosts() {
        Post post = new Post();
        postList.add(post);
        postList.add(post);
        postList.add(post);
    }

    //Add dummy users data for mocking.
    private void addUsers() {
        User user = new User();
        userList.add(user);
        userList.add(user);
        userList.add(user);
    }

    @Test
    public void testInitialView() {
        //progress bar should be visible, error view should be hidden and recycler view should be hidden too.
        assertEquals(View.VISIBLE, activity.findViewById(R.id.pbList).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.tvError).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.rvPost).getVisibility());
    }

    @Test
    public void testSuccessResponseOfGetPostApi() {
        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.success(postList);

        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.GONE, activity.findViewById(R.id.pbList).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.tvError).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.rvPost).getVisibility());
    }

    @Test
    public void testErrorResponseOfGetPostApi() {
        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.error(new Throwable());

        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.GONE, activity.findViewById(R.id.pbList).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.tvError).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.rvPost).getVisibility());
    }

    @Test
    public void testLoadingrResponseOfGetPostApi() {
        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.loading();

        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.VISIBLE, activity.findViewById(R.id.pbList).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.tvError).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.rvPost).getVisibility());
    }

    @Test
    public void testSuccessResponseOfGetPostApiWithNoResults() {
        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.success(null);

        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.GONE, activity.findViewById(R.id.pbList).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.tvError).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.rvPost).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.tvNoResults).getVisibility());

    }

    @Test
    public void testLoadingResponseOfGetUsersApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.loading();

        userObserverCaptor.getValue().onChanged(stateData);

        assertEquals(View.GONE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llElapsedTime).getVisibility());
    }

    @Test
    public void testSuccessResponseOfGetUsersApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.success(userList);

        userObserverCaptor.getValue().onChanged(stateData);

        assertEquals(View.VISIBLE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llElapsedTime).getVisibility());
    }

    @Test
    public void testErrorResponseOfGetUsersApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.error(new Throwable());

        userObserverCaptor.getValue().onChanged(stateData);

        assertEquals(View.GONE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llElapsedTime).getVisibility());
    }

    @Test
    public void testCompletedResponseOfGetUsersApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.complete();

        userObserverCaptor.getValue().onChanged(stateData);

        assertEquals(View.GONE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llElapsedTime).getVisibility());
    }

    @Test
    public void testSuccessResponseOfUsersAndPostApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.success(userList);

        userObserverCaptor.getValue().onChanged(stateData);

        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.success(postList);

        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.VISIBLE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.llElapsedTime).getVisibility());
    }

    @Test
    public void testErrorResponseOfUsersAndPostApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.error(new Throwable());

        userObserverCaptor.getValue().onChanged(stateData);

        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.error(new Throwable());

        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.GONE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llElapsedTime).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llBottomView).getVisibility());
    }

    @Test
    public void testSuccessResponseOfUsersAndErrorResponsePostApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.success(userList);
        userObserverCaptor.getValue().onChanged(stateData);

        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.error(new Throwable());
        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.VISIBLE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llElapsedTime).getVisibility());
        assertEquals(View.VISIBLE, activity.findViewById(R.id.llBottomView).getVisibility());
    }

    @Test
    public void testErrorResponseOfUsersAndSuccessResponsePostApi() {
        StateData<List<User>> stateData = new StateData<>();
        stateData.error(new Throwable());
        userObserverCaptor.getValue().onChanged(stateData);

        StateData<List<Post>> postStateData = new StateData<>();
        postStateData.success(postList);
        postObserverCaptor.getValue().onChanged(postStateData);

        assertEquals(View.GONE, activity.findViewById(R.id.llNumChars).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llElapsedTime).getVisibility());
        assertEquals(View.GONE, activity.findViewById(R.id.llBottomView).getVisibility());
    }


}
