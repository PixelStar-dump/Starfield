package com.star.field;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.widget.LayoutPreference;
import com.google.android.material.card.MaterialCardView;
import com.android.internal.logging.nano.MetricsProto;

public class StarfieldDashboard extends SettingsPreferenceFragment implements View.OnClickListener {

    private LayoutPreference mPreference;
    private MaterialCardView lsclock;
    private LinearLayout icons, fonts;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.starfield_dashboard);

        mPreference = findPreference("starfield_header");
        lsclock = mPreference.findViewById(R.id.wallpaper);
        icons = mPreference.findViewById(R.id.iconpack);
        fonts = mPreference.findViewById(R.id.fonts);

        lsclock.setOnClickListener(this);
        icons.setOnClickListener(this);
        fonts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == lsclock) {
            openGoogleWallpaperApp();
        } else if (v == icons) {
            startActivity("IconPackActivity");
        } else if (v == fonts) {
            startActivity("FontsPickerActivity");
        }
    }

    private void startActivity(String activity) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.Settings$" + activity);
        getContext().startActivity(intent);
    }

	private void openGoogleWallpaperApp() {
		Intent intent = new Intent();
		// Trying known activity names
		String[] possibleActivities = {
			"com.google.android.apps.wallpaper.picker.CategoryPickerActivity",
			"com.google.android.apps.wallpaper.category.CategorySelectorActivity"
		};

		boolean found = false;
		for (String activity : possibleActivities) {
			intent.setClassName("com.google.android.apps.wallpaper", activity);
			if (intent.resolveActivity(getContext().getPackageManager()) != null) {
				getContext().startActivity(intent);
				found = true;
				break;
			}
		}

		if (!found) {
			// Handle the case where the Google Wallpaper app is not installed or no activity is found
			Toast.makeText(getContext(), "Google Wallpaper app is not installed", Toast.LENGTH_SHORT).show();
		}
	}


    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.STARFIELD;
    }

    @Override
    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        RecyclerView rcv = super.onCreateRecyclerView(inflater, container, icicle);
        GridLayoutManager layoutG = new GridLayoutManager(getActivity(), 2);
        layoutG.setSpanSizeLookup(new SpanSizeLookupG());
        rcv.setLayoutManager(layoutG);
        return rcv;
    }

    class SpanSizeLookupG extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            if (position == 0 || position == 1) {
                return 2;
            } else {
                return 1;
            }
        }
    }
}
