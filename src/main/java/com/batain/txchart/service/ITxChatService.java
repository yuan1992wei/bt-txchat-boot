package com.batain.txchart.service;

import com.batain.common.Entity.Result;

/**
 * 疼过sdk获取信息
 * @author batain
 */
public interface ITxChatService {

    /**
     * 初始化sdk
     * @param sdkId  sdkid
     * @param corpid
     * @param secrectkey
     * @return
     */
    public Result initSdk(String sdkId, String corpid, String secrectkey);

    /**
     * 释放sdk
     * @param sdkId sdkid
     * @return
     */
    public Result destroySdk(String sdkId);

    /**
     * 拉取会话数据
     * @param sdkId sdkId
     * @param seq 会话seq
     * @param limit 会话条数
     * @param proxy 代理名
     * @param passwd 代理密码
     * @param timeout 超时时间
     * @return
     */
    public Result getChatData(String sdkId,int seq,int limit,String proxy,String passwd,int timeout);

    /**
     * 解密会话存档内容
     * //sdk不会要求用户传入rsa私钥，保证用户会话存档数据只有自己能够解密。
     * 此处需要用户先用rsa私钥解密encrypt_random_key后，作为encryptKey参数传入sdk来解密encryptChatMsg获取会话存档明文。
     * @param sdkId
     * @param pkv 私有
     * @param encryptRandomKey msg中
     * @param encryptChatMsg
     * @return
     */
    public Result decryptData(String sdkId, String pkv,String encryptRandomKey,String encryptChatMsg);



    /**
     * 解密会话存档内容
     * //sdk不会要求用户传入rsa私钥，保证用户会话存档数据只有自己能够解密。
     * 此处需要用户先用rsa私钥解密encrypt_random_key后，作为encryptKey参数传入sdk来解密encryptChatMsg获取会话存档明文。
     * @param sdkId
     * @param encryptKey
     * @param encryptChatMsg
     * @return
     */
    public Result decryptData(String sdkId,String encryptKey,String encryptChatMsg);

    /**
     * 拉取文件信息
     * //媒体文件每次拉取的最大size为512k，因此超过512k的文件需要分片拉取。若该文件未拉取完整，sdk的IsMediaDataFinish接口会返回0，同时通过GetOutIndexBuf接口返回下次拉取需要传入GetMediaData的indexbuf。
     * //indexbuf一般格式如右侧所示，”Range:bytes=524288-1048575“，表示这次拉取的是从524288到1048575的分片。单个文件首次拉取填写的indexbuf为空字符串，拉取后续分片时直接填入上次返回的indexbuf即可。
     * @param sdkId
     * @param indexbuf
     * @param sdkfileid
     * @param proxy
     * @param passwd
     * @param timeout
     * @return
     */
    public Result getMediaData(String sdkId, String indexbuf, String sdkfileid, String proxy, String passwd, int timeout);
}
