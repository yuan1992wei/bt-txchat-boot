package com.batain.txchart.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.batain.common.Entity.Result;
import com.batain.txchart.domain.MediaData;
import com.batain.txchart.service.ITxChatService;
import com.tencent.wework.Finance;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author batain
 */
@Service
public class TxChatServiceImpl implements ITxChatService {

    private Map<String,Long> sdkMap = new HashMap<>();

    @Override
    public Result initSdk(String sdkId, String corpid, String secrectkey) {
        long ret;
        Long sdk = Finance.NewSdk();
        ret = Finance.Init(sdk, corpid, secrectkey);
        if(ret != 0){
            Finance.DestroySdk(sdk);
            sdkMap.remove(sdkId);
            return new Result(ret,"init sdk err",null);
        }
        if(StrUtil.isNotBlank(sdkId)){
            sdkMap.put(sdkId,sdk);
            return Result.success("init sdk success",sdkId);
        }else{
            sdkMap.put(String.valueOf(sdk),sdk);
            return Result.success("init sdk success",String.valueOf(sdk));
        }
    }

    @Override
    public Result destroySdk(String sdkId) {
        Long sdk = sdkMap.get(sdkId);
        if(ObjectUtil.isNull(sdk)){
            return Result.error("init sdk err");
        }
        Finance.DestroySdk(sdk);
        sdkMap.remove(sdkId);
        return Result.success("destroySdk success");
    }

    @Override
    public Result getChatData(String sdkId, int seq, int limit, String proxy, String passwd, int timeout){
        Long sdk = sdkMap.get(sdkId);
        if(ObjectUtil.isNull(sdk)){
            return Result.error("init sdk err");
        }
        //每次使用GetChatData拉取存档前需要调用NewSlice获取一个slice，在使用完slice中数据后，还需要调用FreeSlice释放。
        long slice = Finance.NewSlice();
        long ret = Finance.GetChatData(sdk, seq, limit, proxy, passwd, timeout, slice);
        if (ret != 0) {
            System.out.println("getchatdata ret " + ret);
            Finance.FreeSlice(slice);
            return new Result(ret,"getchatdata err",null);
        }
        String result = Finance.GetContentFromSlice(slice);
        System.out.println("getchatdata :" + result);
        Finance.FreeSlice(slice);
        return Result.success("getchatdata success",result);
    }

    @Override
    public Result decryptData(String sdkId, String pkv,String encryptRandomKey, String encryptChatMsg) {
        Long sdk = sdkMap.get(sdkId);
        if(ObjectUtil.isNull(sdk)){
            return Result.error("init sdk err");
        }
        //每次使用DecryptData解密会话存档前需要调用NewSlice获取一个slice，在使用完slice中数据后，还需要调用FreeSlice释放。

        String encryptKey = decrypt(encryptRandomKey,pkv);
        return decryptData(sdkId,encryptKey,encryptChatMsg);
    }

    @Override
    public Result decryptData(String sdkId, String encryptKey, String encryptChatMsg) {
        Long sdk = sdkMap.get(sdkId);
        if(ObjectUtil.isNull(sdk)){
            return Result.error("init sdk err");
        }
        long msg = Finance.NewSlice();
        long ret = Finance.DecryptData(sdk, encryptKey, encryptChatMsg, msg);
        if (ret != 0) {
            System.out.println("decryptData ret " + ret);
            Finance.FreeSlice(msg);
            return new Result(ret,"decryptData err",null);
        }
        String result = Finance.GetContentFromSlice(msg);
        System.out.println("decryptData ret:" + ret + " msg:" + result);
        Finance.FreeSlice(msg);
        return Result.success("decryptData success",result);
    }


    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    private String decrypt(String str, String privateKey){
        System.out.println("decrypt--str:"+str);
        System.out.println("decrypt--privateKey:"+privateKey);
        //64位解码加密后的字符串
        String outStr = null;
        try {
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }

    @Override
    public MediaData getMediaData(String sdkId, String indexbuf, String sdkfileid, String proxy, String passwd, int timeout){
        Long sdk = sdkMap.get(sdkId);
        if(ObjectUtil.isNull(sdk)){
            throw new RuntimeException("init sdk err");
        }
        MediaData result = new MediaData();
        //每次使用GetMediaData拉取存档前需要调用NewMediaData获取一个media_data，在使用完media_data中数据后，还需要调用FreeMediaData释放。
        long media_data = Finance.NewMediaData();
        long ret = Finance.GetMediaData(sdk, indexbuf, sdkfileid, proxy, passwd, timeout, media_data);
        if(ret!=0){
            Finance.FreeMediaData(media_data);
            throw new RuntimeException("getmediadata ret:" + ret,null);
        }
        System.out.printf("getmediadata outindex len:%d, data_len:%d, is_finis:%d\n",Finance.GetIndexLen(media_data),Finance.GetDataLen(media_data), Finance.IsMediaDataFinish(media_data));
        try {
            result.setData(Finance.GetData(media_data));
            //大于512k的文件会分片拉取，此处需要使用追加写，避免后面的分片覆盖之前的数据。
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Finance.IsMediaDataFinish(media_data) == 1)
        {
            //已经拉取完成最后一个分片
            Finance.FreeMediaData(media_data);
            result.setEnd(true);
        }
        else
        {
            //获取下次拉取需要使用的indexbuf
            indexbuf = Finance.GetOutIndexBuf(media_data);
            Finance.FreeMediaData(media_data);
            result.setEnd(false);
            result.setIndexbuf(indexbuf);
        }

        return result;
    }
}
