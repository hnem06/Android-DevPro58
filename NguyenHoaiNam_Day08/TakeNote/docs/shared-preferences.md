# SharedPreferences trong Android

## 1. SharedPreferences là gì?

`SharedPreferences` là API lưu trữ dữ liệu dạng key-value có sẵn trong Android. Dữ liệu được lưu thành file XML riêng của ứng dụng, phù hợp cho các thông tin nhỏ và đơn giản.

Nên dùng `SharedPreferences` cho:

- Trạng thái đăng nhập đơn giản.
- Tùy chọn người dùng, ví dụ theme, ngôn ngữ, cờ bật/tắt thông báo.
- Dữ liệu form nhỏ cần giữ lại sau khi đóng app.
- Token hoặc flag cấu hình nhỏ. Với dữ liệu nhạy cảm, nên cân nhắc `EncryptedSharedPreferences`.

Không nên dùng `SharedPreferences` cho:

- Danh sách dữ liệu lớn.
- Dữ liệu quan hệ phức tạp.
- Cache lớn.
- Dữ liệu cần truy vấn, sắp xếp, lọc thường xuyên. Trường hợp này nên dùng Room/SQLite/DataStore.

## 2. Tạo đối tượng SharedPreferences

Có 2 cách lấy `SharedPreferences` phổ biến.

### Theo tên file riêng

```java
SharedPreferences prefs = getSharedPreferences("user_info_pref", Context.MODE_PRIVATE);
```

Cách này tạo/lấy file preferences có tên `user_info_pref`. Nên dùng khi Activity, Fragment hoặc class khác nhau cùng cần truy cập cùng một file.

### Theo preferences mặc định của Activity

```java
SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
```

Cách này gắn với Activity hiện tại. Nếu muốn chia sẻ dữ liệu giữa nhiều màn hình, cách theo tên file riêng rõ ràng hơn.

`Context.MODE_PRIVATE` nghĩa là chỉ ứng dụng hiện tại đọc/ghi được file preferences.

## 3. Lưu dữ liệu

Muốn ghi dữ liệu, gọi `edit()` để lấy `SharedPreferences.Editor`, sau đó put dữ liệu theo key.

```java
SharedPreferences prefs = getSharedPreferences("user_info_pref", Context.MODE_PRIVATE);

prefs.edit()
        .putString("name", "Nguyễn Văn A")
        .putString("age", "20")
        .putString("address", "Hà Nội")
        .apply();
```

`SharedPreferences` hỗ trợ các kiểu cơ bản:

- `putString(String key, String value)`
- `putInt(String key, int value)`
- `putLong(String key, long value)`
- `putFloat(String key, float value)`
- `putBoolean(String key, boolean value)`
- `putStringSet(String key, Set<String> values)`

## 4. apply() và commit()

Sau khi put dữ liệu, phải gọi `apply()` hoặc `commit()`.

### apply()

```java
editor.apply();
```

`apply()` cập nhật dữ liệu trong memory ngay lập tức, sau đó ghi xuống file XML ở background. Hàm này không trả về kết quả thành công/thất bại.

Nên dùng `apply()` khi:

- Đang thao tác trên UI thread.
- Không cần biết ngay lập tức việc ghi file có thành công hay không.
- Lưu các tùy chọn nhỏ, trạng thái màn hình, thông tin form đơn giản.

Ví dụ:

```java
SharedPreferences.Editor editor = sharedPreferences.edit();
editor.putString("name", "Nguyễn Văn A");
editor.putString("age", "20");
editor.apply();
```

Trong demo `EditSharedPreferencesActivity`, nút `Sửa bằng apply()` dùng cách này:

```java
private void updateWithApply() {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(UserPreferences.KEY_NAME, getInputName());
    editor.putString(UserPreferences.KEY_AGE, getInputAge());
    editor.putString(UserPreferences.KEY_ADDRESS, getInputAddress());
    editor.apply();
}
```

### commit()

```java
boolean success = editor.commit();
```

`commit()` ghi dữ liệu xuống file theo cách đồng bộ. Nghĩa là method sẽ chờ cho đến khi việc ghi hoàn tất, sau đó trả về `true` nếu thành công hoặc `false` nếu thất bại.

Nên dùng `commit()` khi:

- Cần biết kết quả ghi ngay lập tức.
- Đang chạy trong background thread hoặc tác vụ không nhạy cảm với blocking.
- Cần quyết định bước tiếp theo dựa trên việc ghi thành công/thất bại.

Không nên lạm dụng `commit()` trên UI thread vì thao tác ghi file có thể làm UI bị khựng lại nếu thiết bị chậm hoặc file system đang bận.

Ví dụ:

```java
SharedPreferences.Editor editor = sharedPreferences.edit();
editor.putString("name", "Nguyễn Văn A");
boolean success = editor.commit();

if (success) {
    // Ghi thành công
} else {
    // Ghi thất bại
}
```

Trong demo `EditSharedPreferencesActivity`, nút `Sửa bằng commit()` dùng cách này:

```java
private void updateWithCommit() {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(UserPreferences.KEY_NAME, getInputName());
    editor.putString(UserPreferences.KEY_AGE, getInputAge());
    editor.putString(UserPreferences.KEY_ADDRESS, getInputAddress());
    boolean success = editor.commit();
}
```

### Bảng so sánh nhanh

| Tiêu chí | apply() | commit() |
| --- | --- | --- |
| Cách ghi | Bất đồng bộ xuống file | Đồng bộ xuống file |
| Cập nhật memory | Ngay lập tức | Ngay lập tức |
| Giá trị trả về | Không có | `boolean` |
| Có thể block UI thread | Ít hơn | Có |
| Nên dùng khi | Lưu dữ liệu UI thông thường | Cần biết ghi thành công/thất bại |

## 5. Đọc dữ liệu

Dùng key tương ứng và truyền giá trị mặc định. Giá trị mặc định được trả về khi key chưa tồn tại.

```java
SharedPreferences prefs = getSharedPreferences("user_info_pref", Context.MODE_PRIVATE);

String name = prefs.getString("name", "");
String age = prefs.getString("age", "");
String address = prefs.getString("address", "");
```

Ví dụ hiển thị lên `TextView`:

```java
textViewName.setText("Tên: " + name);
textViewAge.setText("Tuổi: " + age);
textViewAddress.setText("Địa chỉ: " + address);
```

## 6. Xóa dữ liệu

Xóa một key:

```java
prefs.edit()
        .remove("name")
        .apply();
```

Xóa tất cả key trong file preferences đó:

```java
prefs.edit()
        .clear()
        .apply();
```

## 7. Ví dụ Activity hoàn chỉnh

Activity demo trong project này nằm tại package:

```text
com.rsui.rs_datapersistence.pack1
```

File chính:

```text
app/src/main/java/com/rsui/rs_datapersistence/pack1/SharedPreferencesDemoActivity.java
```

Luôn khai báo key bằng constant để tránh gõ sai chuỗi:

```java
private static final String PREF_NAME = "user_info_pref";
private static final String KEY_NAME = "name";
private static final String KEY_AGE = "age";
private static final String KEY_ADDRESS = "address";
```

Khởi tạo preferences trong `onCreate()`:

```java
sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
```

Lưu thông tin khi bấm nút:

```java
private void saveUserInfo() {
    String name = nameEditText.getText().toString().trim();
    String age = ageEditText.getText().toString().trim();
    String address = addressEditText.getText().toString().trim();

    sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_AGE, age)
            .putString(KEY_ADDRESS, address)
            .apply();
}
```

Load thông tin từ `SharedPreferences`:

```java
private void loadUserInfo() {
    String name = sharedPreferences.getString(KEY_NAME, "");
    String age = sharedPreferences.getString(KEY_AGE, "");
    String address = sharedPreferences.getString(KEY_ADDRESS, "");

    savedNameTextView.setText("Tên: " + name);
    savedAgeTextView.setText("Tuổi: " + age);
    savedAddressTextView.setText("Địa chỉ: " + address);
}
```

## 8. Luồng hoạt động của demo

1. App mở `SharedPreferencesDemoActivity`.
2. Activity gọi `loadUserInfo()` trong `onCreate()`.
3. Nếu đã có dữ liệu, app hiển thị tên, tuổi, địa chỉ đã lưu.
4. Người dùng nhập thông tin vào 3 `EditText`.
5. Khi bấm nút lưu, app ghi dữ liệu vào `SharedPreferences`.
6. Activity gọi lại `loadUserInfo()` để cập nhật 3 `TextView`.
7. Khi đóng và mở lại app, dữ liệu vẫn còn vì đã được lưu trên bộ nhớ riêng của ứng dụng.

## 9. EncryptedSharedPreferences là gì?

`EncryptedSharedPreferences` là một implementation của `SharedPreferences` có mã hóa key và value trước khi ghi xuống disk. Nó phù hợp để lưu các thông tin nhạy cảm như access token, refresh token, mật khẩu tạm thời, secret nhỏ.

Khác với `SharedPreferences` thường:

- `SharedPreferences` thường lưu file XML ở dạng dễ đọc nếu thiết bị bị root, backup bị lộ, hoặc attacker truy cập được storage của app.
- `EncryptedSharedPreferences` mã hóa key/value bằng khóa được quản lý qua Android Keystore.
- Code đọc/ghi gần giống `SharedPreferences`, nhưng cần khởi tạo `MasterKey` và thêm dependency `androidx.security:security-crypto`.

Lưu ý: `androidx.security:security-crypto:1.1.0` hiện vẫn dùng được cho demo `EncryptedSharedPreferences`, nhưng AndroidX đã deprecated API này để khuyến khích các ứng dụng production mới cân nhắc dùng Android Keystore trực tiếp.

## 10. Tích hợp EncryptedSharedPreferences từng bước

### Bước 1: Thêm dependency

Trong `gradle/libs.versions.toml`:

```toml
[versions]
securityCrypto = "1.1.0"

[libraries]
security-crypto = { group = "androidx.security", name = "security-crypto", version.ref = "securityCrypto" }
```

Trong `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation(libs.security.crypto)
}
```

Sau khi thêm dependency, sync Gradle hoặc build project.

### Bước 2: Tạo MasterKey

`MasterKey` là khóa chính dùng để bảo vệ các khóa mã hóa dữ liệu. Thư viện AndroidX Security sẽ làm việc với Android Keystore để tạo/lấy khóa này.

```java
MasterKey masterKey = new MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build();
```

`AES256_GCM` là scheme mã hóa đối xứng có xác thực, giúp bảo vệ cả tính bí mật và tính toàn vẹn của dữ liệu.

### Bước 3: Tạo EncryptedSharedPreferences

```java
SharedPreferences securePreferences = EncryptedSharedPreferences.create(
        context,
        "secure_user_pref",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
);
```

Ý nghĩa các tham số:

- `context`: nên dùng application context để tránh giữ reference Activity.
- `"secure_user_pref"`: tên file preferences được lưu trong storage riêng của app.
- `masterKey`: khóa chính để bảo vệ dữ liệu.
- `AES256_SIV`: scheme mã hóa key preferences.
- `AES256_GCM`: scheme mã hóa value preferences.

### Bước 4: Bọc logic vào helper

Trong project này, helper nằm ở:

```text
app/src/main/java/com/rsui/rs_datapersistence/pack1/SecurePreferencesHelper.java
```

Ví dụ:

```java
public class SecurePreferencesHelper {
    private static final String SECURE_PREF_NAME = "secure_user_pref";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_PASSWORD = "password";

    private final SharedPreferences securePreferences;

    public SecurePreferencesHelper(Context context) throws GeneralSecurityException, IOException {
        Context appContext = context.getApplicationContext();
        MasterKey masterKey = new MasterKey.Builder(appContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        securePreferences = EncryptedSharedPreferences.create(
                appContext,
                SECURE_PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }
}
```

Lý do nên bọc vào helper:

- Activity không phải lặp lại code khởi tạo security.
- Key được quản lý tập trung, tránh gõ sai chuỗi.
- Dễ thay đổi tên file, key, hoặc cách mã hóa sau này.
- Dễ viết test hoặc thay đổi implementation.

### Bước 5: Lưu dữ liệu bảo mật

```java
public void saveSecureInfo(String accessToken, String password) {
    securePreferences.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_PASSWORD, password)
            .apply();
}
```

Code ghi vẫn giống `SharedPreferences`, nhưng key và value sẽ được mã hóa trước khi ghi xuống file.

### Bước 6: Đọc dữ liệu bảo mật

```java
public String getAccessToken() {
    return securePreferences.getString(KEY_ACCESS_TOKEN, "");
}

public String getPassword() {
    return securePreferences.getString(KEY_PASSWORD, "");
}
```

Khi đọc, `EncryptedSharedPreferences` tự giải mã dữ liệu và trả về giá trị gốc.

### Bước 7: Xóa dữ liệu bảo mật

```java
public void clearSecureInfo() {
    securePreferences.edit()
            .remove(KEY_ACCESS_TOKEN)
            .remove(KEY_PASSWORD)
            .apply();
}
```

Nên xóa token/password khi logout, khi token hết hạn, hoặc khi người dùng yêu cầu xóa dữ liệu.

### Bước 8: Xử lý exception khi khởi tạo

Khởi tạo `EncryptedSharedPreferences` có thể ném `GeneralSecurityException` hoặc `IOException`. Activity demo xử lý bằng cách disable các nút thao tác nếu khởi tạo thất bại.

```java
try {
    securePreferencesHelper = new SecurePreferencesHelper(this);
    loadSecureInfo();
} catch (GeneralSecurityException | IOException exception) {
    Toast.makeText(this, "Không thể khởi tạo EncryptedSharedPreferences", Toast.LENGTH_LONG).show();
    saveSecureButton.setEnabled(false);
    loadSecureButton.setEnabled(false);
    clearSecureButton.setEnabled(false);
}
```

## 11. Vì sao cần backup_rules.xml và data_extraction_rules.xml?

`EncryptedSharedPreferences` phụ thuộc vào khóa trong Android Keystore. Khóa này thường gắn với thiết bị/app install hiện tại và không được backup/restore như file preferences thông thường.

Nếu file encrypted preferences được backup lên cloud rồi restore sang lần cài đặt khác hoặc thiết bị khác, file XML mã hóa có thể được khôi phục nhưng khóa trong Android Keystore lại không còn tương ứng. Khi đó app có thể không giải mã được dữ liệu, gây lỗi lúc đọc preferences.

Vì vậy nên exclude file encrypted preferences khỏi backup.

### backup_rules.xml

File:

```text
app/src/main/res/xml/backup_rules.xml
```

Được tham chiếu trong manifest bằng:

```xml
android:fullBackupContent="@xml/backup_rules"
```

Trong project này:

```xml
<full-backup-content>
    <exclude domain="sharedpref" path="secure_user_pref.xml" />
</full-backup-content>
```

Ý nghĩa: không đưa file `secure_user_pref.xml` vào Android Auto Backup/full backup.

### data_extraction_rules.xml

File:

```text
app/src/main/res/xml/data_extraction_rules.xml
```

Được tham chiếu trong manifest bằng:

```xml
android:dataExtractionRules="@xml/data_extraction_rules"
```

Từ Android 12/API 31 trở lên, `dataExtractionRules` điều khiển dữ liệu nào được đưa vào cloud backup hoặc device-to-device transfer.

Trong project này:

```xml
<data-extraction-rules>
    <cloud-backup>
        <exclude domain="sharedpref" path="secure_user_pref.xml" />
    </cloud-backup>
</data-extraction-rules>
```

Ý nghĩa: không backup file secure preferences lên cloud trên các thiết bị/API dùng cơ chế data extraction mới.

### Tại sao cần cả hai file?

- `backup_rules.xml` phục vụ cơ chế full backup cũ hơn và các thiết bị/API cũ.
- `data_extraction_rules.xml` phục vụ cơ chế backup/transfer mới từ Android 12 trở lên.
- Khai báo cả hai giúp hạn chế việc file encrypted preferences bị restore sai ngữ cảnh trên nhiều phiên bản Android.

### Có nên exclude SharedPreferences thường không?

Không bắt buộc. Với preferences thường như theme, ngôn ngữ, tùy chọn UI, backup có thể hữu ích. Với file chứa dữ liệu mã hóa bằng Android Keystore, nên exclude.

## 12. Lưu ý thực hành tốt

- Đặt key và tên file preferences bằng `static final String`.
- Không lưu object phức tạp trực tiếp vào `SharedPreferences`.
- Không lưu dữ liệu quá lớn.
- Dùng `apply()` cho thao tác lưu thông thường trên UI.
- Dùng giá trị mặc định khi đọc dữ liệu để tránh lỗi null.
- Với mật khẩu, token quan trọng hoặc dữ liệu nhạy cảm, nên dùng giải pháp mã hóa thay vì `SharedPreferences` thường.
- Không backup file `EncryptedSharedPreferences` vì file mã hóa có thể không giải mã được sau khi restore.
- Không log access token, mật khẩu, refresh token ra Logcat.
- Nên xóa token/password khi logout.
