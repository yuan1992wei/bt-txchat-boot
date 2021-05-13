package com.batain.txchart.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.batain.common.Entity.Result;
import com.batain.txchart.service.ITxChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 获取会话信息
 * @author batain
 */
@Controller
public class TxCharController {

    @Autowired
    private ITxChatService txChatService;


    /**
     * 初始化sdk
     * @param sdkId
     * @param corpid
     * @param secrectkey
     * @return
     */
    @RequestMapping("/txchat/initSdk")
    @ResponseBody
    public Result initSdk(String sdkId,String corpid,String secrectkey){
        return txChatService.initSdk(sdkId,corpid,secrectkey);
    }

    /**
     * 撤销skd
     * @param sdkId
     * @return
     */
    @RequestMapping("/txchat/destroySdk")
    @ResponseBody
    public Result destroySdk(String sdkId){
        return txChatService.destroySdk(sdkId);
    }

    /**
     * 获取会话信息（不解密）
     * @param sdkId
     * @return
     */
    @RequestMapping("/txchat/getChatData")
    @ResponseBody
    public Result getChatData(String sdkId,int seq,int limit,String proxy,String passwd,int timeout){
        return txChatService.getChatData(sdkId, seq, limit, proxy, passwd, timeout);
    }

    /**
     * 获取会话信息（解密）
     * @param sdkId
     * @return
     */
    @RequestMapping("/txchat/getDecryptChatData")
    @ResponseBody
    public Result getChatData(String sdkId,int seq,int limit,String proxy,String passwd,int timeout,String pk){
        Result data = txChatService.getChatData(sdkId, seq, limit, proxy, passwd, timeout);
        if(data.getCode() == Result.Type.SUCCESS.value()){
            JSONObject chatData = JSONUtil.parseObj(data.getData());
            JSONArray jsonArray = chatData.getJSONArray("chatdata");
            for (int i = 0; i < jsonArray.size() ; i++) {
                JSONObject chatOne = jsonArray.getJSONObject(i);
                System.out.println("chatOne:"+chatOne.toString());
                Result dcRes = txChatService.decryptData(sdkId,pk,chatOne.getStr("encrypt_random_key"),chatOne.getStr("encrypt_chat_msg"));
                if(dcRes.getCode() == Result.Type.SUCCESS.value()){
                    chatOne.putOpt("body",JSONUtil.parseObj(dcRes.getData()));
                }
            }
            return Result.success("解密数据",chatData.toString());
        }
        return data;
    }

    /**
     * 解密数据
     * @param sdkId
     * @return
     */
    @RequestMapping("/txchat/decryptDataByPk")
    @ResponseBody
    public Result decryptData(String sdkId,String pk, String encryptRandomKey, String encryptChatMsg){
        return txChatService.decryptData(sdkId,pk,encryptRandomKey,encryptChatMsg);
    }

    /**
     * 解密数据
     * @param sdkId
     * @return
     */
    @RequestMapping("/txchat/decryptData")
    @ResponseBody
    public Result decryptData(String sdkId, String encryptKey, String encryptChatMsg){
        return txChatService.decryptData(sdkId,encryptKey,encryptChatMsg);
    }


    /**
     * 获取媒体数据
     * @param sdkId
     * @return
     */
    @RequestMapping("/txchat/getMediaData")
    @ResponseBody
    public Result getMediaData(String sdkId, String indexbuf, String sdkfileid, String proxy, String passwd, int timeout){
        return txChatService.getMediaData(sdkId, indexbuf, sdkfileid, proxy, passwd, timeout);
    }

}
