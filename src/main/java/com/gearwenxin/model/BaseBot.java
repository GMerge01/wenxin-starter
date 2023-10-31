package com.gearwenxin.model;

/**
 * @author Ge Mingjia

 */
public interface BaseBot {

    /**
     * 单独设置accessToken
     *
     * @param accessToken accessToken
     */
    void setCustomAccessToken(String accessToken);

    /**
     * 获取accessToken
     */
    String getCustomAccessToken();

    /**
     * 获取模型的URL
     *
     * @return URL
     */
    String getURL();

    /**
     * 获取模型的Tag
     *
     * @return URL
     */
    String getTag();

}
