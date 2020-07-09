package com.zsc.dto;

/**
 * @author zsc
 * @version 1.0
 * @date 2020/6/24 19:29
 */
public class ClientMsg {
    /**
     * json格式消息
     *{
     *     "userId"      :1,
     *     "peerUserId"  :2,
     *     "goupId"      :-1（或非-1），
     *     "msg"         :"msg"
     *}
     * userId      :发送用户
     * peerUserId  :接收用户
     * groupId     :是否是群组消息，-1表示非群组消息
     * msg         :发送的消息
     **/
    private long userId;
    private long peerUserId;
    private long groupId;
    private String msg;

    public ClientMsg(long userId, long peerUserId, long groupId, String msg) {
        this.userId = userId;
        this.peerUserId = peerUserId;
        this.groupId = groupId;
        this.msg = msg;
    }

    public long getUserId() {
        return userId;
    }

    public long getPeerUserId() {
        return peerUserId;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getMsg() {
        return msg;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setPeerUserId(long peerUserId) {
        this.peerUserId = peerUserId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString(){
        return "{"+
                "userId:" + String.valueOf(userId) +
                "peerUserId:" + String.valueOf(peerUserId) +
                "groupId:" + String.valueOf(peerUserId) +
                "msg:" + msg
                +"}";
    }
}
