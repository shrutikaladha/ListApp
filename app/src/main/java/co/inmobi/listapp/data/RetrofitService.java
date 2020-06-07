package co.inmobi.listapp.data;

import java.util.List;

import co.inmobi.listapp.model.Post;
import co.inmobi.listapp.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/users")
    Call<List<User>> getUsers();
}
