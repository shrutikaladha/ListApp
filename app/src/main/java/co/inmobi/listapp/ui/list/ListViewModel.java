package co.inmobi.listapp.ui.list;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import co.inmobi.listapp.base.StateLiveData;
import co.inmobi.listapp.data.DataRepository;
import co.inmobi.listapp.model.Post;
import co.inmobi.listapp.model.User;

public class ListViewModel extends ViewModel {
    DataRepository dataRepository;

    @Inject
    public ListViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public StateLiveData<List<User>> getUsers() {
        return dataRepository.getUsers();
    }

    public StateLiveData<List<Post>> getPosts() {
        return dataRepository.getPosts();
    }
}
