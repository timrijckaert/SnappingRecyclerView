package be.tim.rijckaert.snaprecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by trijckaert on 26/09/16.
 */

public class MockFragment extends Fragment {

    public MockFragment() {}

    public static MockFragment newInstance() {
        return new MockFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mock, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWrappingRV((RecyclerView) view.findViewById(R.id.wrapping_recyclerview));
    }

    private void setupWrappingRV(final RecyclerView wrappingRecyclerView) {
        wrappingRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        wrappingRecyclerView.setHasFixedSize(true);
        wrappingRecyclerView.setAdapter(new WrappingSnapRecyclerAdapter(this.getContext()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}