/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.notification;

import android.app.AutomaticZenRule;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.preference.Preference;
import android.widget.Switch;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.applications.LayoutPreference;
import com.android.settings.widget.SwitchBar;
import com.android.settingslib.core.lifecycle.Lifecycle;

public class ZenAutomaticRuleSwitchPreferenceController extends
        AbstractZenModeAutomaticRulePreferenceController implements
        SwitchBar.OnSwitchChangeListener {

    private static final String KEY = "zen_automatic_rule_switch";
    private AutomaticZenRule mRule;
    private String mId;
    private Toast mEnabledToast;
    private int mToastTextResource;

    public ZenAutomaticRuleSwitchPreferenceController(Context context, Fragment parent,
            int toastTextResource, Lifecycle lifecycle) {
        super(context, KEY, parent, lifecycle);
        mToastTextResource = toastTextResource;
    }

    @Override
    public String getPreferenceKey() {
        return KEY;
    }

    @Override
    public boolean isAvailable() {
        return mRule != null && mId != null;
    }

    public void onResume(AutomaticZenRule rule, String id) {
        mRule = rule;
        mId = id;
    }

    public void updateState(Preference preference) {
        LayoutPreference pref = (LayoutPreference) preference;
        SwitchBar bar = pref.findViewById(R.id.switch_bar);
        if (mRule != null) {
            bar.setChecked(mRule.isEnabled());
        }
        if (bar != null) {
            bar.show();
            try {
                bar.addOnSwitchChangeListener(this);
            } catch (IllegalStateException e) {
                // an exception is thrown if you try to add the listener twice
            }
        }
        bar.show();
    }

    @Override
    public void onSwitchChanged(Switch switchView, boolean isChecked) {
        final boolean enabled = isChecked;
        if (enabled == mRule.isEnabled()) return;
        mRule.setEnabled(enabled);
        mBackend.setZenRule(mId, mRule);
        if (enabled) {
            final int toastText = mToastTextResource;
            if (toastText != 0) {
                mEnabledToast = Toast.makeText(mContext, toastText, Toast.LENGTH_SHORT);
                mEnabledToast.show();
            }
        } else {
            if (mEnabledToast != null) {
                mEnabledToast.cancel();
            }
        }
    }
}
