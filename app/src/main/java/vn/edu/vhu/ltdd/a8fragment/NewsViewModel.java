package vn.edu.vhu.ltdd.a8fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * NC2: ViewModel phạm vi Activity.
 * Các Fragment lấy cùng một instance bằng:
 *   new ViewModelProvider(requireActivity()).get(NewsViewModel.class)
 * nhờ đó trạng thái "đã đọc" sống sót qua lần đổi tab và xoay màn hình
 * (ViewModel được giữ lại khi Activity bị tạo lại do cấu hình).
 */
public class NewsViewModel extends ViewModel {

    private final MutableLiveData<String> lastRead = new MutableLiveData<>();
    private final MutableLiveData<Integer> readCount = new MutableLiveData<>(0);

    public LiveData<String> getLastRead() {
        return lastRead;
    }

    public LiveData<Integer> getReadCount() {
        return readCount;
    }

    public void markRead(String tieuDe) {
        lastRead.setValue(tieuDe);
        Integer hienTai = readCount.getValue();
        readCount.setValue(hienTai == null ? 1 : hienTai + 1);
    }
}
