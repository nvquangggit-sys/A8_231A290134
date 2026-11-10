package vn.edu.vhu.ltdd.a8fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A8_231A290134";

    /** true khi đang chạy trên màn hình lớn (layout-sw600dp có detailContainer). */
    private boolean twoPane;

    /** Chỉ layout-sw600dp mới có: dòng gợi ý khi khung chi tiết còn trống. */
    private View detailPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // NC1: chỉ layout-sw600dp mới có detailContainer.
        twoPane = findViewById(R.id.detailContainer) != null;
        detailPlaceholder = findViewById(R.id.tvDetailPlaceholder);
        Log.d(TAG, "onCreate – twoPane = " + twoPane);

        if (twoPane) {
            // Hiện lại dòng gợi ý mỗi khi khung phải trống (đổi tab, xoay màn hình, đóng chi tiết).
            getSupportFragmentManager().registerFragmentLifecycleCallbacks(
                    new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
                                                          @NonNull View v, @Nullable Bundle saved) {
                            if (f instanceof NewsDetailFragment) {
                                hienGoiY(false);
                            }
                        }

                        @Override
                        public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                            if (f instanceof NewsDetailFragment) {
                                hienGoiY(true);
                            }
                        }
                    }, false);

            // Tablet không dùng back stack cho khung phải, nên tự đóng chi tiết trước khi thoát app.
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    Fragment detail = getSupportFragmentManager().findFragmentById(R.id.detailContainer);
                    if (detail != null) {
                        Log.d(TAG, "Back – đóng khung chi tiết, giữ nguyên danh sách");
                        getSupportFragmentManager().beginTransaction().remove(detail).commit();
                        return;
                    }
                    // Không còn gì để đóng: nhường Back cho hệ thống (đóng Activity).
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            });
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.tab_home) {
                doiTab(new HomeFragment());
            } else if (id == R.id.tab_news) {
                doiTab(new NewsListFragment());
            } else if (id == R.id.tab_profile) {
                doiTab(ProfileFragment.newInstance("Nguyễn Việt Quang", "231A290134"));
            }
            return true;   // true = đánh dấu tab đang được chọn
        });

        // Chỉ nạp Fragment mặc định ở LẦN TẠO ĐẦU TIÊN.
        // Nếu bỏ điều kiện này, khi xoay màn hình sẽ nạp chồng thêm một Fragment nữa.
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.tab_home);
        }
    }

    public boolean isTwoPane() {
        return twoPane;
    }

    /** Bật/tắt dòng gợi ý ở khung chi tiết (tablet). */
    private void hienGoiY(boolean hien) {
        if (detailPlaceholder != null) {
            detailPlaceholder.setVisibility(hien ? View.VISIBLE : View.GONE);
        }
    }

    /** Thay Fragment đang hiển thị trong khung chứa. */
    private void doiTab(Fragment f) {
        Log.d(TAG, "Chuyển tab sang: " + f.getClass().getSimpleName());

        int containerId = twoPane ? R.id.listContainer : R.id.fragmentContainer;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, f)
                // KHÔNG addToBackStack cho các tab: bấm Back sẽ thoát app, không lùi tab.
                .setReorderingAllowed(true)
                .commit();

        // NC1: đổi tab thì xóa luôn màn hình chi tiết bên phải.
        if (twoPane) {
            Fragment detail = getSupportFragmentManager().findFragmentById(R.id.detailContainer);
            if (detail != null) {
                getSupportFragmentManager().beginTransaction().remove(detail).commit();
            }
        }
    }
}
