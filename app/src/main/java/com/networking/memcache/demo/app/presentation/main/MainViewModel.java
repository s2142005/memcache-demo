package com.networking.memcache.demo.app.presentation.main;

import com.networking.memcache.demo.domain.model.UnsplashUser;
import com.networking.memcache.demo.domain.usecase.FetchUserListUseCase;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final FetchUserListUseCase fetchUserListUseCase = new FetchUserListUseCase();

    public MutableLiveData<List<UnsplashUser>> fetchUsersData(String keyword) {
        return fetchUserListUseCase.fetchUserList(keyword);
    }

    public MutableLiveData<List<UnsplashUser>> loadMore(String searchKey, int offset) {
        return fetchUserListUseCase.fetchUserList(searchKey, offset);
    }
}
