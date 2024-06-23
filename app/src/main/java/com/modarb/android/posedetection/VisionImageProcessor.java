/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.modarb.android.posedetection;

import android.graphics.Bitmap;

import androidx.camera.core.ImageProxy;

import com.google.mlkit.common.MlKitException;

import java.nio.ByteBuffer;

public interface VisionImageProcessor {

    void processBitmap(Bitmap bitmap, GraphicOverlay graphicOverlay);

    void processByteBuffer(
            ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay)
            throws MlKitException;

    void processImageProxy(ImageProxy image, GraphicOverlay graphicOverlay) throws MlKitException;

    void stop();
}
