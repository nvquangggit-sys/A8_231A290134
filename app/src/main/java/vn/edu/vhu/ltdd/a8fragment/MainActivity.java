package vn.edu.vhu.ltdd.a8fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A8_231A290134";

    /** true khi đang chạy trên màn hình lớn (layout-sw600dp có detailContainer). */
    private boolean twoPane;

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
        Log.d(TAG, "onCreate – twoPane = " + twoPane);

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
