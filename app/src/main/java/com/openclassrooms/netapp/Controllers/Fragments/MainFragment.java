package com.openclassrooms.netapp.Controllers.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.netapp.Controllers.Models.GithubUser;
import com.openclassrooms.netapp.Controllers.Utils.GithubCalls;
import com.openclassrooms.netapp.Controllers.Utils.NetworkAsyncTask;
import com.openclassrooms.netapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements NetworkAsyncTask.Listeners, GithubCalls.Callbacks{

    // FOR DESIGN
    @BindView(R.id.fragment_main_textview) TextView textView;

    public MainFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // -----------------
    // ACTIONS
    // -----------------

    @OnClick(R.id.fragment_main_button)
    public void submit(View view) {
        this.executeHttpRequestWithRetrofit();
    }

    @Override
    public void doInBackground() {
        this.textView.setText("Downloading...");
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(String success) {
        this.textView.setText(success);
    }

    //Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit() {
        this.textView.setText("Starting...");
        GithubCalls.fetchUserFollowing(this, "JakeWharton");
    }

    @Override
    public void onResponse(@Nullable List<GithubUser> users) {
        //When getting response, we update UI
        if (users != null) this.updateUIWithListOfUsers(users);
    }
    @Override
    public void onFailure() {
        //When getting error, we update UI
        this.updateUIWhenStopingHTTPRequest("An error happened !");
    }

    private void updateUIWithListOfUsers(List<GithubUser> users) {
        StringBuilder sb = new StringBuilder();
        for (GithubUser user : users) {
            sb.append("-"+user.getLogin()+"\n");
        }
        updateUIWhenStopingHTTPRequest(sb.toString());
    }
    private void updateUIWhenStopingHTTPRequest(String s) {
        this.textView.setText(s);
    }
}
