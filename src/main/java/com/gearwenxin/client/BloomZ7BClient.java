package com.gearwenxin.client;

import com.gearwenxin.client.base.FullClient;
import com.gearwenxin.common.Constant;
import com.gearwenxin.entity.Message;

import java.util.Map;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ge Mingjia

 */
public abstract class BloomZ7BClient extends FullClient {

    protected BloomZ7BClient() {
    }

    private String accessToken = null;
    private static final String TAG = "BloomZ-7B-Client";
    private static Map<String, Deque<Message>> BLOOMZ_MESSAGES_HISTORY_MAP = new ConcurrentHashMap<>();
    private static final String URL = Constant.BLOOMZ_7B_URL;

    protected abstract String getAccessToken();

    @Override
    public String getCustomAccessToken() {
        return accessToken != null ? accessToken : getAccessToken();
    }

    @Override
    public Map<String, Deque<Message>> getMessageHistoryMap() {
        return BLOOMZ_MESSAGES_HISTORY_MAP;
    }

    @Override
    public void initMessageHistoryMap(Map<String, Deque<Message>> map) {
        BLOOMZ_MESSAGES_HISTORY_MAP = map;
    }

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public void setCustomAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
