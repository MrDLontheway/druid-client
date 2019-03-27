package com.wxstc.dl;

import java.io.Serializable;

public class CountResult implements Serializable {
    private long count;
    private String page;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "CountResult{" +
                "count=" + count +
                ", page='" + page + '\'' +
                '}';
    }
}
