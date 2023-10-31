package com.gearwenxin.client.ernie;

import com.gearwenxin.client.ImageClient;
import com.gearwenxin.entity.Message;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Deque;

/**
 * @author Ge Mingjia

 */
@Slf4j
public abstract class ErnieBotVilGClient extends ImageClient {

    protected ErnieBotVilGClient() {
    }

    private String accessToken = null;
    private static final String TAG = "ErnieBotVilGClient";

    // 获取access-token
    protected abstract String getAccessToken();

    // 获取不固定的模型URL
    protected abstract String getCustomURL();

    @Override
    public String getCustomAccessToken() {
        return accessToken != null ? accessToken : getAccessToken();
    }

    @Override
    public void setCustomAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getURL() {
        return getCustomURL();
    }

    @Override
    public String getTag() {
        return TAG;
    }

}
