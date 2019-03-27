package com.wxstc.dl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Result implements Serializable {
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Result{" +
                "timestamp=" + timestamp +
                ", result=" + result +
                '}';
    }

    public List<CountResult> getResult() {
        return result;
    }

    public void setResult(List<CountResult> result) {
        this.result = result;
    }

    private List<CountResult> result;
}
