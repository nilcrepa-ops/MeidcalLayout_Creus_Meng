package com.creus.medicallayouyt;

import android.os.Bundle;
import android.util.TypedValue;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.creus.medicallayouyt.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

/**
 * Home screen header: gradient background, avatar, notification bell, greeting,
 * Urgent Care call-to-action and the doctor cutout.
 *
 * The screen is edge-to-edge: the header gradient is drawn behind the status bar,
 * while its content is padded with the status-bar inset at runtime.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // The design is light, so force dark system-bar icons.
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);

        applyWindowInsets();
        bindClicks();
    }

    private void applyWindowInsets() {
        final int horizontal = dp(20);
        final int headerTop = dp(18);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());

            // Header content sits below the status bar; its gradient still paints behind it.
            binding.headerContent.setPadding(horizontal, bars.top + headerTop, horizontal, 0);

            return insets;
        });
    }

    private void bindClicks() {
        binding.avatar.setOnClickListener(v -> snack(R.string.cd_profile));
        binding.bell.setOnClickListener(v -> snack(R.string.cd_notifications));
        binding.urgentCare.setOnClickListener(v -> snack(R.string.toast_urgent_care));
    }

    private void snack(int messageRes) {
        Snackbar.make(binding.getRoot(), getString(messageRes), Snackbar.LENGTH_SHORT).show();
    }

    private int dp(int value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics()));
    }
}
