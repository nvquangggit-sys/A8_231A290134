package vn.edu.vhu.ltdd.a8fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * Fragment đơn giản nhất — chỉ để quan sát vòng đời.
 * Mở Logcat, lọc theo A8_231A290134 rồi chuyển tab qua lại để thấy thứ tự các phương thức.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "A8_231A290134/Home";

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach – đã gắn vào Activity");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate – tạo Fragment (chưa có View)");
    }

    /** Nơi "thổi" file XML thành View. KHÔNG findViewById ở đây bằng getView(). */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView – tạo cây View");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /** View đã sẵn sàng → ánh xạ và gán sự kiện tại đây. */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated – ánh xạ View");
        TextView tv = view.findViewById(R.id.tvChao);
        tv.setText(getString(R.string.home_hello, "Nguyễn Việt Quang"));

        // NC2: ViewModel phạm vi Activity → số tin đã đọc dùng chung giữa các tab.
        NewsViewModel vm = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        TextView tvCount = view.findViewById(R.id.tvReadCount);
        vm.getReadCount().observe(getViewLifecycleOwner(), count -> {
            int c = count == null ? 0 : count;
            tvCount.setText(getString(R.string.home_read_count, c));
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart – Fragment hiện ra");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume – nhận thao tác");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause – bị che");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop – không còn hiển thị");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView – View bị hủy (Fragment có thể vẫn còn sống)");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy – Fragment bị hủy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach – gỡ khỏi Activity");
    }
}
