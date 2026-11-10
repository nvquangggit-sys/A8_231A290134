# A8_231A290134 — Giao diện modular với Fragment

Bài thực hành INT4211 – Lập trình trên các thiết bị di động, Lab A8.

## Chức năng

- 1 Activity duy nhất (`MainActivity`) + 3 tab Fragment: Trang chủ, Tin tức, Cá nhân.
- Khung chứa dùng `FragmentContainerView`, thanh tab dùng `BottomNavigationView`.
- Danh sách tin mở màn hình chi tiết bằng `replace()` + `addToBackStack("chi_tiet")`.
- Truyền dữ liệu vào Fragment bằng mẫu `newInstance` + `Bundle arguments`.
- Trả kết quả ngược bằng Fragment Result API (`news_request`).
- **NC1:** bố cục master–detail cho tablet bằng `res/layout-sw600dp/activity_main.xml`; khung phải có dòng gợi ý khi trống và Back chỉ đóng chi tiết, giữ nguyên danh sách.
- **NC2:** giữ trạng thái đã đọc giữa các tab bằng `ViewModel` phạm vi Activity.

## Cấu hình

| Trường | Giá trị |
| --- | --- |
| Package | `vn.edu.vhu.ltdd.a8fragment` |
| Language | Java |
| Minimum SDK | API 24 |
| compileSdk / targetSdk | 36 / 36 |
| AVD kiểm thử | Pixel 6 API 34, Pixel Tablet API 34 |

## Build

```bash
./gradlew assembleDebug            # Windows: gradlew.bat assembleDebug
# hoặc mở project bằng Android Studio và bấm Run
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

Yêu cầu: JDK đi kèm Android Studio (đã kiểm thử với JDK 25 của Android Studio JBR), Android SDK 36.

## Cấu trúc

```
app/src/main/java/vn/edu/vhu/ltdd/a8fragment/
├── MainActivity.java          # Activity duy nhất, điều hướng tab
├── HomeFragment.java          # Tab Trang chủ + log vòng đời
├── NewsListFragment.java      # Danh sách tin, nhận Fragment Result
├── NewsDetailFragment.java    # Chi tiết, newInstance + arguments
├── ProfileFragment.java       # Tab Cá nhân
└── NewsViewModel.java         # NC2: ViewModel phạm vi Activity
app/src/main/res/layout/            # Bố cục điện thoại (1 khung)
app/src/main/res/layout-sw600dp/    # NC1: master–detail cho tablet
docs/                               # 15 ảnh chụp + 3 tệp Logcat
```

## Kịch bản kiểm thử

1. Mở app → tab Trang chủ hiện sẵn; Logcat ghi đủ `onAttach → onResume`.
2. Chuyển tab Tin tức rồi quay lại Trang chủ → Fragment cũ bị hủy tới `onDetach`.
3. Bấm một tin → mở đúng tiêu đề/nội dung.
4. Bấm Back → quay lại danh sách, không thoát app.
5. Bấm “Đánh dấu đã đọc và quay lại” → danh sách hiện “Đã đọc: …”.
6. Xoay màn hình khi ở chi tiết → nội dung giữ nguyên, không nạp chồng.
7. Ở tab Trang chủ bấm Back → thoát app.

## Minh chứng

Ảnh chụp và Logcat nằm trong `docs/`:

| Thư mục | Nội dung |
| --- | --- |
| `docs/screenshots/` | 9 ảnh Pixel 6 (trang chủ, tin tức, chi tiết, back, đã đọc, đếm tin, cá nhân, xoay ngang, thoát app) + 5 ảnh Pixel Tablet (trang chủ, danh sách, chi tiết, Back, đã đọc) + ảnh Logcat |
| `docs/logs/` | `log_launch.txt`, `log_tabs.txt` (thứ tự callback khi đổi tab), `log_rotate.txt` (3 lần xoay → 3 lần `onCreateView`) |

Video demo 42 giây được nộp kèm bài (không đưa vào repo để tránh phình dung lượng).

## Báo cáo

Báo cáo Word đầy đủ (bảng vòng đời, 7 kịch bản kiểm thử, 3 câu phân tích, NC1 + NC2, 5 câu ôn tập)
được nộp kèm bài dưới tên `BaoCao_LabA8_231A290134.docx`.
