package com.example.homework;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String FULL_DESCRIPTION = "Aston Hotel, Alice Springs NT 0870, Australia is a modern hotel, elegant 5 star hotel overlooking the sea, perfect for a romantic, charming vacation. Aaaaaa bbbbb ccccc ddddd eeeee fffff ggggg hhhhh jjjjj kkkkk lllll mmmmm nnnnn ooooo ppppp qqqqq rrrrr sssss ttttt uuuuu vvvvv wwwww";

    private boolean isExpanded = false;
    private TextView tvDescription;
    private ViewGroup scrollContent; // parent layout trong ScrollView
    private String truncatedText = "";
    private int purpleColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        purpleColor = ContextCompat.getColor(this, R.color.primary_purple);

        tvDescription = findViewById(R.id.tvDescriptionContent);

        // Lấy LinearLayout chứa tvDescription
        scrollContent = (ViewGroup) tvDescription.getParent();

        // set MovementMethod cho LinkMovementMethod
        // Mục đích: Cho phép TextView nhận biết và xử lý sự kiện click trên
        // ClickableSpan
        // Kiểm tra vị trí ngón tay chạm vào ký tự nào
        // nếu ký tự đó nằm trong một ClickableSpan → gọi onClick() của span đó
        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());

        // Bỏ highlight color của link
        // Nhấn "Read More" → không có vùng tô màu, trông sạch và đẹp hơn
        // tvDescription.setHighlightColor(Color.TRANSPARENT);

        // Đợi layout xong để lấy text bị cắt ở 3 dòng
        tvDescription.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvDescription.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if (tvDescription.getLayout() != null && tvDescription.getLineCount() >= 3) {
                    // Lấy chỉ số ký tự cuối cùng của dòng thứ 3 (index 2)
                    int lineEnd = tvDescription.getLayout().getLineEnd(2);
                    // Lấy chuỗi text từ đầu đến vị trí lineEnd
                    String raw = FULL_DESCRIPTION.substring(0, Math.min(lineEnd, FULL_DESCRIPTION.length()));
                    // Bỏ bớt ký tự cuối để chừa chỗ cho "... Read More"
                    // (16 ký tự bao gồm cả dấu cách, 3 dấu chấm, "Read More")
                    int charsToRemove = 16;
                    // Check nếu độ dài chuỗi lớn hơn số ký tự cần xóa
                    if (raw.length() > charsToRemove) {
                        // Cắt chuỗi
                        truncatedText = raw.substring(0, raw.length() - charsToRemove).trim();
                    } else {
                        // Lấy toàn bộ chuỗi
                        truncatedText = raw.trim();
                    }
                } else {
                    truncatedText = FULL_DESCRIPTION.substring(0, 100).trim();
                }

                // Sử dụng UI thu gọn cho lần ddauafuu tiên
                showCollapsed();
            }
        });

    }

    // Hàm xử lý trạng thái thu gọn
    private void showCollapsed() {
        // Tạo StringBuilder với nội dung cắt và thêm "... "
        SpannableStringBuilder builder = new SpannableStringBuilder(truncatedText + "... ");

        int start = builder.length();
        builder.append("Read More");
        int end = builder.length();

        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                toggleDescription();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(purpleColor);
                ds.setUnderlineText(false);
                ds.setFakeBoldText(true);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvDescription.setMaxLines(Integer.MAX_VALUE);
        tvDescription.setText(builder);
        isExpanded = false;
    }

    private void showExpanded() {
        SpannableStringBuilder builder = new SpannableStringBuilder(FULL_DESCRIPTION + "  ");

        int start = builder.length();
        builder.append("Show Less");
        int end = builder.length();

        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                toggleDescription();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(purpleColor);
                ds.setUnderlineText(false);
                ds.setFakeBoldText(true);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvDescription.setMaxLines(Integer.MAX_VALUE);
        tvDescription.setText(builder);
        isExpanded = true;
    }

    private void toggleDescription() {
        // Animate layout thay đổi bằng TransitionManager
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(300);
        TransitionManager.beginDelayedTransition(scrollContent, transition);

        if (isExpanded) {
            showCollapsed();
        } else {
            showExpanded();
        }
    }
}