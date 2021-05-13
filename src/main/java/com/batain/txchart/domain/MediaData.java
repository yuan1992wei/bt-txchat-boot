package com.batain.txchart.domain;

/**
 * 获取媒体的数据实体
 */
public class MediaData {

    /**
     * 数据
     */
    private byte[] data;

    /**
     * 是否分片结束
     */
    private boolean isEnd;

    /**
     * 获取下次拉取需要使用的indexbuf
     */
    private String indexbuf;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public String getIndexbuf() {
        return indexbuf;
    }

    public void setIndexbuf(String indexbuf) {
        this.indexbuf = indexbuf;
    }
}
