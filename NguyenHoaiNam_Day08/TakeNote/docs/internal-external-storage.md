# Internal Storage và External Storage trong Android

## 1. Mục tiêu demo

Phần này demo cách lưu một file JSON ngắn bằng storage API của Android. Activity nằm trong package:

```text
com.rsui.rs_datapersistence.pack2
```

File Activity:

```text
app/src/main/java/com/rsui/rs_datapersistence/pack2/InternalExternalStorageDemoActivity.java
```

Demo có các phần:

- Lưu file JSON vào internal storage bằng `openFileOutput()`.
- Đọc file JSON từ internal storage bằng `openFileInput()`.
- Hiển thị đường dẫn thư mục internal files bằng `getFilesDir()`.
- Ghi file tạm vào internal cache bằng `getCacheDir()`.
- Demo app-specific external storage bằng `getExternalFilesDir(null)`.
- Hiển thị app-specific external cache bằng `getExternalCacheDir()`.

## 2. Internal Storage là gì?

Internal storage là vùng lưu trữ riêng của ứng dụng. File trong vùng này chỉ app hiện tại truy cập được trong điều kiện thông thường.

Nên dùng internal storage khi:

- Dữ liệu thuộc riêng về app.
- Không muốn app khác đọc trực tiếp.
- Dữ liệu cần tồn tại sau khi đóng/mở lại app.
- File nhỏ hoặc vừa, ví dụ JSON config, dữ liệu profile, trạng thái app.

Khi app bị uninstall, dữ liệu trong internal storage cũng bị xóa.

## 3. External Storage là gì?

External storage là vùng lưu trữ nằm ngoài internal private directory. Trong Android hiện đại, nên ưu tiên app-specific external storage:

```java
File externalDir = getExternalFilesDir(null);
```

App-specific external storage:

- Không cần quyền `READ_EXTERNAL_STORAGE` hoặc `WRITE_EXTERNAL_STORAGE` cho file riêng của app.
- File vẫn bị xóa khi uninstall app.
- Phù hợp cho file lớn hơn, file export tạm, ảnh/video/tài liệu thuộc app.
- Có thể không luôn sẵn sàng, vì vậy `getExternalFilesDir(null)` có thể trả về `null`.

Không nên nhầm app-specific external storage với public shared storage như Downloads, Pictures hoặc Documents. Nếu muốn ghi vào shared collections trên Android mới, nên dùng MediaStore hoặc Storage Access Framework.

## 4. Khai báo Activity launcher trong Manifest

Trong `AndroidManifest.xml`, Activity của `pack2` được đặt làm launcher:

```xml
<activity
    android:name=".pack2.InternalExternalStorageDemoActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />

        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

Khi một Activity có intent filter `MAIN` và `LAUNCHER`, Android sẽ mở Activity đó khi người dùng bấm icon app.

## 5. Tạo JSON mẫu

Demo dùng `JSONObject` để tạo JSON thay vì nối chuỗi thủ công.

```java
private String createSampleJson(String storageType) throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", 1);
    jsonObject.put("name", "Nguyễn Văn A");
    jsonObject.put("role", "Android learner");
    jsonObject.put("storageType", storageType);
    return jsonObject.toString(4);
}
```

`toString(4)` giúp format JSON dễ đọc với indent 4 spaces.

## 6. Lưu JSON vào internal storage bằng openFileOutput()

`openFileOutput()` mở một file trong internal files directory của app.

```java
try (FileOutputStream outputStream = openFileOutput("profile_internal.json", MODE_PRIVATE)) {
    outputStream.write(createSampleJson("internal").getBytes(StandardCharsets.UTF_8));
}
```

Ý nghĩa:

- `"profile_internal.json"` là tên file được lưu trong thư mục internal files.
- `MODE_PRIVATE` ghi file ở chế độ riêng tư của app. Nếu file đã tồn tại, nội dung cũ bị thay thế.
- `StandardCharsets.UTF_8` giúp ghi chuỗi JSON có dấu tiếng Việt đúng encoding.
- `try-with-resources` tự đóng stream sau khi ghi xong.

Khi nào dùng:

- Lưu JSON cấu hình nhỏ.
- Lưu dữ liệu app riêng tư.
- Lưu dữ liệu không cần chia sẻ trực tiếp cho app khác.

## 7. Đọc JSON từ internal storage bằng openFileInput()

`openFileInput()` mở file đã lưu trong internal files directory.

```java
try (FileInputStream inputStream = openFileInput("profile_internal.json")) {
    String json = readText(inputStream);
    textView.setText(json);
}
```

Hàm đọc text:

```java
private String readText(FileInputStream inputStream) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
    }
    return outputStream.toString(StandardCharsets.UTF_8.name());
}
```

Lưu ý: nếu file chưa tồn tại, `openFileInput()` sẽ ném `FileNotFoundException`, là một loại `IOException`.

## 8. Lấy đường dẫn internal files bằng getFilesDir()

`getFilesDir()` trả về thư mục internal files của app.

```java
File internalDir = getFilesDir();
File internalFile = new File(getFilesDir(), "profile_internal.json");
```

Nên dùng `getFilesDir()` khi:

- Cần tạo file con hoặc thư mục con trong internal storage.
- Cần hiển thị/log đường dẫn khi debug.
- Cần dùng API làm việc với `File` thay vì `openFileOutput()`.

Ví dụ ghi bằng `FileOutputStream`:

```java
File file = new File(getFilesDir(), "profile_internal.json");
try (FileOutputStream outputStream = new FileOutputStream(file)) {
    outputStream.write(json.getBytes(StandardCharsets.UTF_8));
}
```

Với file nằm trực tiếp trong internal files directory, `openFileOutput()` thường ngắn gọn hơn.

## 9. Dùng getCacheDir() cho file tạm

`getCacheDir()` trả về thư mục cache riêng của app trong internal storage.

```java
File cacheFile = new File(getCacheDir(), "profile_cache.json");
try (FileOutputStream outputStream = new FileOutputStream(cacheFile)) {
    outputStream.write(createSampleJson("cache").getBytes(StandardCharsets.UTF_8));
}
```

Nên dùng cache khi:

- File có thể tạo lại được.
- Dữ liệu chỉ dùng tạm.
- Mất file không làm hỏng luồng chính của app.

Không nên dùng cache cho:

- Token.
- Dữ liệu quan trọng.
- Dữ liệu người dùng kỳ vọng luôn còn.

Android có thể xóa cache khi thiết bị thiếu dung lượng.

## 10. Dùng getExternalFilesDir() cho app-specific external storage

`getExternalFilesDir(null)` trả về thư mục external riêng của app.

```java
File externalDir = getExternalFilesDir(null);
if (externalDir == null) {
    // External storage chưa sẵn sàng
    return;
}

File externalFile = new File(externalDir, "profile_external.json");
try (FileOutputStream outputStream = new FileOutputStream(externalFile)) {
    outputStream.write(createSampleJson("external").getBytes(StandardCharsets.UTF_8));
}
```

Nên kiểm tra `null` vì external storage có thể chưa mount hoặc chưa sẵn sàng.

Nên dùng app-specific external storage khi:

- File lớn hơn internal storage thông thường.
- File thuộc riêng app nhưng không quá nhạy cảm.
- Muốn tách dữ liệu app ra vùng external app-specific.

Không cần xin runtime permission khi app chỉ đọc/ghi thư mục app-specific external của chính nó.

## 11. Dùng getExternalCacheDir()

`getExternalCacheDir()` trả về thư mục cache external riêng của app.

```java
File externalCacheDir = getExternalCacheDir();
```

Dùng external cache khi:

- File tạm có kích thước lớn.
- File có thể tạo lại.
- Không cần bảo mật cao.

Luôn kiểm tra `null` trước khi sử dụng:

```java
File externalCacheDir = getExternalCacheDir();
if (externalCacheDir != null) {
    File file = new File(externalCacheDir, "temp.json");
}
```

## 12. So sánh nhanh

| API | Vị trí | Dữ liệu bị xóa khi uninstall | Cần permission | Nên dùng cho |
| --- | --- | --- | --- | --- |
| `openFileOutput()` | Internal files | Có | Không | File riêng nhỏ/vừa |
| `openFileInput()` | Internal files | Có | Không | Đọc file đã lưu bằng internal storage |
| `getFilesDir()` | Internal files | Có | Không | Lấy đường dẫn, tạo file/thư mục con |
| `getCacheDir()` | Internal cache | Có | Không | File tạm, có thể bị xóa |
| `getExternalFilesDir(null)` | App-specific external files | Có | Không | File riêng của app, có thể lớn hơn |
| `getExternalCacheDir()` | App-specific external cache | Có | Không | File tạm ở external storage |

## 13. Lưu ý khi sử dụng

- Luôn dùng `UTF-8` khi đọc/ghi text có tiếng Việt.
- Luôn đóng stream sau khi dùng. Cách tốt nhất là dùng `try-with-resources`.
- Không lưu mật khẩu hoặc token nhạy cảm bằng file thường. Nên dùng `EncryptedSharedPreferences` hoặc Android Keystore.
- Không ghi file lớn trên UI thread trong app production. Demo này ghi file nhỏ để dễ quan sát.
- Với external storage, luôn kiểm tra `getExternalFilesDir()` hoặc `getExternalCacheDir()` có trả về `null` không.
- Không giả định cache sẽ tồn tại mãi. Android hoặc người dùng có thể xóa cache.
- Không dùng đường dẫn hard-code. Hãy lấy đường dẫn bằng API như `getFilesDir()`, `getCacheDir()`, `getExternalFilesDir()`.
- Với file cần chia sẻ ra ngoài app, cân nhắc `FileProvider`, MediaStore hoặc Storage Access Framework thay vì đưa đường dẫn file private cho app khác.
