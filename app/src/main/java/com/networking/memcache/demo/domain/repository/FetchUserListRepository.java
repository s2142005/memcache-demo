package com.networking.memcache.demo.domain.repository;

import com.networking.memcache.demo.domain.model.UnsplashUser;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface FetchUserListRepository {

    MutableLiveData<List<UnsplashUser>> fetchUserList(String keyword);

    void loadUsers(String keyword, int offset) throws IOException;

    MutableLiveData<List<UnsplashUser>> fetchUserList(String searchKey, int offset);
}
