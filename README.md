# A8_231A290134 — Giao diện modular với Fragment

Bài thực hành INT4211 – Lập trình trên các thiết bị di động, Lab A8.

## Chức năng

- 1 Activity duy nhất (`MainActivity`) + 3 tab Fragment: Trang chủ, Tin tức, Cá nhân.
- Khung chứa dùng `FragmentContainerView`, thanh tab dùng `BottomNavigationView`.
- Danh sách tin mở màn hình chi tiết bằng `replace()` + `addToBackStack("chi_tiet")`.
- Truyền dữ liệu vào Fragment bằng mẫu `newInstance` + `Bundle arguments`.
- Trả kết quả ngược bằng Fragment Result API (`news_request`).
- **NC1:** bố cục master–detail cho tablet bằng `res/layout-sw600dp/activity_main.xml`.
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
gradle assembleDebug
# hoặc mở project bằng Android Studio và bấm Run
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

## Kịch bản kiểm thử

1. Mở app → tab Trang chủ hiện sẵn; Logcat ghi đủ `onAttach → onResume`.
2. Chuyển tab Tin tức rồi quay lại Trang chủ → Fragment cũ bị hủy tới `onDetach`.
3. Bấm một tin → mở đúng tiêu đề/nội dung.
4. Bấm Back → quay lại danh sách, không thoát app.
5. Bấm “Đánh dấu đã đọc và quay lại” → danh sách hiện “Đã đọc: …”.
6. Xoay màn hình khi ở chi tiết → nội dung giữ nguyên, không nạp chồng.
7. Ở tab Trang chủ bấm Back → thoát app.

## Minh chứng

Ảnh chụp và Logcat nằm trong `docs/`. Video demo: `A8_231A290134_demo.mp4` (45 giây).
