/*
 * Copyright (C) 2017 BullyBoo
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
package ru.bullyboo.mvp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.bullyboo.mvp.managers.PresenterManager;
import ru.bullyboo.mvp.presenters.FrogPresenter;
import ru.bullyboo.mvp.tools.PresenterCreator;


public abstract class FrogFragment<P extends FrogPresenter> extends Fragment {

    /**
     * Class of presenter
     */
    private Class<P> presenterClass;

    /**
     * Object of presenter
     */
    private P presenter;

    /**
     * Object of Bundle, which maybe has id of presenter
     */
    private Bundle fragmentBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentBundle = savedInstanceState;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(presenter, outState);
    }

    public void setPresenter(Class<P> presenterClass){
        this.presenterClass = presenterClass;
        checkPresenter();
    }

    public P getPresenter(){
        return presenter;
    }

    private void checkPresenter(){
        if(fragmentBundle != null){
            presenter = PresenterManager.getInstance().restorePresenter(fragmentBundle);
            fragmentBundle = null;
        } else {
            presenter = PresenterCreator.newInstance(presenterClass);
        }
    }
}
