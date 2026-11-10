package vn.edu.vhu.ltdd.a8fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ProfileFragment extends Fragment {

    private static final String KEY_TEN = "key_ten";
    private static final String KEY_MSSV = "key_mssv";

    public static ProfileFragment newInstance(String ten, String mssv) {
        ProfileFragment f = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TEN, ten);
        args.putString(KEY_MSSV, mssv);
        f.setArguments(args);
        return f;
    }

    /** Cách gọn hơn: truyền layout vào constructor của Fragment, khỏi viết onCreateView. */
    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = requireArguments();
        ((TextView) view.findViewById(R.id.tvTen)).setText(args.getString(KEY_TEN));
        ((TextView) view.findViewById(R.id.tvMssv)).setText(
                getString(R.string.profile_mssv, args.getString(KEY_MSSV)));

        // NC2: tab Cá nhân cũng đọc chung ViewModel với tab Tin tức.
        NewsViewModel vm = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        TextView tvLastRead = view.findViewById(R.id.tvLastRead);
        vm.getLastRead().observe(getViewLifecycleOwner(), tieuDe -> {
            if (tieuDe == null || tieuDe.isEmpty()) {
                tvLastRead.setText(R.string.list_none_read);
            } else {
                tvLastRead.setText(getString(R.string.profile_last_read, tieuDe));
            }
        });
    }
}
