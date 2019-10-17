package com.htmitech.listener;

import htmitech.com.componentlibrary.entity.Dics;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface ISearchResult {
    public void searchResult(Dics dics);

    public class DefaultSearchResult implements ISearchResult {

        @Override
        public void searchResult(Dics dics) {

        }
    }
}
