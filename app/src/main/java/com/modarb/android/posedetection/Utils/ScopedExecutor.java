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

package com.modarb.android.posedetection.Utils;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;


public class ScopedExecutor implements Executor {

    private final Executor executor;
    private final AtomicBoolean shutdown = new AtomicBoolean();

    public ScopedExecutor(@NonNull Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        if (shutdown.get()) {
            return;
        }
        executor.execute(
                () -> {

                    if (shutdown.get()) {
                        return;
                    }
                    command.run();
                });
    }


    public void shutdown() {
        shutdown.set(true);
    }
}
