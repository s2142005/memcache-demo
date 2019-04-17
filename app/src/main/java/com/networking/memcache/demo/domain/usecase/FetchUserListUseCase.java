package com.networking.memcache.demo.domain.usecase;

import com.networking.memcache.demo.data.mapper.FetchUnsplashUsersMapper;
import com.networking.memcache.demo.data.repository.unsplash.UnsplashEndpointImpl;
import com.networking.memcache.demo.data.response.unsplash.FetchUnsplashUsersResponse;
import com.networking.memcache.demo.domain.model.UnsplashUser;
import com.networking.memcache.demo.domain.repository.FetchUserListRepository;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class FetchUserListUseCase implements FetchUserListRepository {

    private static final String USER_LIST_URL = "https://api.unsplash.com/search/users?page=%s&query=%s&client_id"
            + "=1d14d58a4235fc32835dcb8f83cc7c5ffff87960814932d2d243768dd8c78a0d";

    private static final String TAG = "FetchUserListUseCase";
    private final UnsplashEndpointImpl endpoint = new UnsplashEndpointImpl();
    private MutableLiveData<List<UnsplashUser>> users;

    @Override
    public MutableLiveData<List<UnsplashUser>> fetchUserList(String keyword) {
        if (users == null) {
            users = new MutableLiveData<>();
            try {
                loadUsers(keyword, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void loadUsers(String keyword, int offset) throws IOException {
        String url = String.format(USER_LIST_URL, offset, keyword);
        FetchUnsplashUsersResponse response = endpoint.fetchUsers(url);
        List<UnsplashUser> userList = FetchUnsplashUsersMapper.map(response);
        List<UnsplashUser> currentList = users.getValue();
        if (currentList != null) {
            currentList.addAll(userList);
            users.setValue(currentList);
        } else {
            users.setValue(userList);
        }
    }

    @Override
    public MutableLiveData<List<UnsplashUser>> fetchUserList(String searchKey, int offset) {
        if (users == null) {
            users = new MutableLiveData<>();
        }

        try {
            loadUsers(searchKey, offset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
