/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package skku.alticastvux.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import skku.alticastvux.model.VideoInfo;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        VideoInfo videoInfo = (VideoInfo) item;
        if (videoInfo != null) {
            viewHolder.getTitle().setText(videoInfo.getTitle());
            viewHolder.getSubtitle().setText(videoInfo.getTime());
            ViewGroup v = (ViewGroup) viewHolder.getBody().getParent();
            viewHolder.getBody().setMaxLines(20);
            viewHolder.getBody().setTextSize(20);
            viewHolder.getBody().setEllipsize(null);
            viewHolder.getBody().setText(videoInfo.getDescription());
        }
    }
}
