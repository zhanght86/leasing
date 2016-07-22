package com.pqsoft.telephonenumberlist.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 请求收集数据的响应
 *
 * @author 钟林俊
 * @version V1.0 2016-06-27 11:52
 */
public class CollectResponse extends Response{

    @JSONField(name = "data")
    private CollectResponseData data;

    public CollectResponseData getData() {
        return data;
    }

    public void setData(CollectResponseData data) {
        this.data = data;
    }
}
