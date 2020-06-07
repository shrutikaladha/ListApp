package co.inmobi.listapp.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.inmobi.listapp.base.StateLiveData;
import co.inmobi.listapp.model.Post;
import co.inmobi.listapp.model.User;

/**
 * This class is added to serve the data to ViewModels through multiple sources.
 * Currently, only RemoteDataSource is added as the application has only online support.
 * This can be extended to serve data from local repository also, adding a local data source going ahead.
 * <p>
 * This is a wrapper to hide the logic from where data is being served to View Model.
 */
@Singleton
public class DataRepository {

    private final RemoteDataSource remoteDataSource;

    @Inject
    public DataRepository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    /**
     * This method would fetch latest posts from API end-point.
     *
     * @return
     */
    public StateLiveData<List<Post>> getPosts() {
        return remoteDataSource.getPosts();
    }

    /**
     * This method would fetch all users from API end-point.
     *
     * @return
     */
    public StateLiveData<List<User>> getUsers() {
        return remoteDataSource.getUsers();
    }

}
