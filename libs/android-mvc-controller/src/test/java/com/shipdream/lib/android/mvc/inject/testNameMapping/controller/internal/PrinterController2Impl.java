/*
 * Copyright 2015 Kejun Xia
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

package com.shipdream.lib.android.mvc.inject.testNameMapping.controller.internal;

import com.shipdream.lib.android.mvc.controller.internal.BaseControllerImpl;
import com.shipdream.lib.android.mvc.inject.testNameMapping.controller.PrintModel;
import com.shipdream.lib.android.mvc.inject.testNameMapping.controller.PrinterController2;
import com.shipdream.lib.android.mvc.inject.testNameMapping.manager.InkManager;

import javax.inject.Inject;

public class PrinterController2Impl extends BaseControllerImpl<PrintModel> implements PrinterController2{
    @Inject
    private InkManager inkManager;

    @Override
    public String print() throws Exception {
        if(inkManager.fetchInk()) {
            return getModel().getContent();
        } else {
            throw new Exception("Out of ink");
        }
    }

    @Override
    public InkManager getInkManager() {
        return inkManager;
    }

    @Override
    public Class<PrintModel> getModelClassType() {
        return PrintModel.class;
    }
}
