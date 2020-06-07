package co.inmobi.listapp.data;

import java.util.List;

import javax.inject.Inject;

import co.inmobi.listapp.base.StateLiveData;
import co.inmobi.listapp.model.Post;
import co.inmobi.listapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is used to fetch the response from various APIs using retrofit service.
 * This returns a StateLiveData object in response which would update the Observer about the current
 * status of API requests.
 * StateLiveData has 5 defined states defined as part of StateData object.
 * CREATED,
 * SUCCESS,
 * ERROR,
 * LOADING,
 * COMPLETE.
 * Here, we would be using 3 states to communicate with UI:  LOADING, SUCCESS, ERROR.
 */
public class RemoteDataSource {
    private final RetrofitService retrofitService;

    @Inject
    public RemoteDataSource(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public StateLiveData<List<Post>> getPosts() {
        final StateLiveData<List<Post>> postData = new StateLiveData<>();
        postData.postLoading();

        retrofitService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call,
                                   Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    postData.postSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                postData.postError(t);
            }
        });
        return postData;
    }

    public StateLiveData<List<User>> getUsers() {
        final StateLiveData<List<User>> userData = new StateLiveData<>();
        userData.postLoading();
        retrofitService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call,
                                   Response<List<User>> response) {
                if (response.isSuccessful()) {
                    userData.postSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                userData.postError(t);
            }
        });
        return userData;
    }
}
