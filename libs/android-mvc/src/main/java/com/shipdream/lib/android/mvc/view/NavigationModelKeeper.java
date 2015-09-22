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

package com.shipdream.lib.android.mvc.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.shipdream.lib.android.mvc.NavLocation;
import com.shipdream.lib.android.mvc.controller.NavigationController;

/**
 * Navigation model state keeper implemented by Android parcelable.
 */
public class NavigationModelKeeper implements AndroidStateKeeper<NavigationController.Model> {
    @Override
    public Parcelable saveState(NavigationController.Model state, Class<NavigationController.Model> stateType) {
        return new ModelParcelable(state);
    }

    @Override
    public NavigationController.Model getState(Parcelable parceledState, Class<NavigationController.Model> stateType) {
        return ((ModelParcelable)parceledState).model;
    }

    //==================================================================================================
    //Parcelable to manage navigation model
    public static class ModelParcelable implements Parcelable {
        private NavigationController.Model model;

        public static final Parcelable.Creator<ModelParcelable> CREATOR
                = new Parcelable.Creator<ModelParcelable>() {
            public ModelParcelable createFromParcel(Parcel in) {
                return new ModelParcelable(in);
            }

            public ModelParcelable[] newArray(int size) {
                return new ModelParcelable[size];
            }
        };

        private ModelParcelable(NavigationController.Model model) {
            this.model = model;
        }

        private ModelParcelable(Parcel in) {
            model = new NavigationController.Model();
            int start = in.dataPosition();
            int size = in.readInt();
            int end = start + size;
            if(in.dataPosition() < end) {
                String locId = in.readString();
                NavLocation location = new NavLocation();
                location._setLocationId(locId);
                model.setCurrentLocation(location);
                readLocation(in, location, end);
            }
        }

        private void readLocation(Parcel in, NavLocation curLoc, int end) {
            int pos = in.dataPosition();
            if(pos < end) {
                String str = in.readString();
                NavLocation location = new NavLocation();
                location._setLocationId(str);
                curLoc._setPreviousLocation(location);
                readLocation(in, location, end);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            //Place holder for the size of the parcel
            int start = dest.dataPosition();
            dest.writeInt(0);
            writeLocation(dest, model.getCurrentLocation());
            int end = dest.dataPosition();
            //Rewind back and write the size of the parcel
            int size = end - start;
            dest.setDataPosition(start);
            dest.writeInt(size);
            //Move the cursor back
            dest.setDataPosition(end);
        }

        private void writeLocation(Parcel dest, NavLocation location) {
            if (location != null) {
                dest.writeString(location.getLocationId());
                writeLocation(dest, location.getPreviousLocation());
            }
        }
    }

}
