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
package com.android.settings.deviceinfo.firmwareversion;
import android.content.Context;
import android.os.SystemProperties;
import android.support.annotation.VisibleForTesting;
import com.android.settings.R;
public class DevilianVersionDialogController {
    @VisibleForTesting
import com.android.settings.R;
public class DevilianVersionDialogController {
    @VisibleForTesting
    private static final int DEVILIAN_VERSION_VALUE_ID = R.id.devilian_version;
    private static final String DEVILIAN_VERSION = "devilian_version";
    private static final String DEVILIAN_PROP = "ro.devilian.version";
    private final FirmwareVersionDialogFragment mDialog;
    private final Context mContext;
    public DevilianVersionDialogController(FirmwareVersionDialogFragment dialog) {
        mDialog = dialog;
        mContext = dialog.getContext();
    }
     public void initialize() {
         mDialog.setText(DEVILIAN_VERSION_VALUE_ID, SystemProperties.get(DEVILIAN_PROP,
                mContext.getResources().getString(R.string.device_info_default)));
    }
}
