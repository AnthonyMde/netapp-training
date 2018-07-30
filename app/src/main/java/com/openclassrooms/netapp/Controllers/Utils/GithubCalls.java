package com.openclassrooms.netapp.Controllers.Utils;

import android.support.annotation.Nullable;
import com.openclassrooms.netapp.Controllers.Models.GithubUser;
import java.lang.ref.WeakReference;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubCalls {
    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable List<GithubUser> users);
        void onFailure();
    }

    // 2 - Public method to start fetching users following by Jake Wharton
    public static void fetchUserFollowing(Callbacks callbacks, String username) {

        //2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);
        //2.2 - Get a Retrofit instance and the related endpoints
        GithubService githubService = GithubService.retrofit.create(GithubService.class);
        //2.3 - Create the call on Github API
        Call<List<GithubUser>> call = githubService.getFollowing(username);
        //2.4 - Start the call
        call.enqueue(new Callback<List<GithubUser>>() {
            @Override
            public void onResponse(Call<List<GithubUser>> call, Response<List<GithubUser>> response) {
                //2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<GithubUser>> call, Throwable t) {
                //2.5 - Call the proper callback in controller (MainFragment)
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onFailure();
            }
        });
    }
}
