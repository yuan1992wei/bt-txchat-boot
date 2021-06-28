package com.batain.common.Entity;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;

public class Result extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data";

    /**
     * 状态类型
     */
    public enum Type
    {
        /** 成功 */
        SUCCESS(0L),
        /** 警告 */
        WARN(301L),
        /** 错误 */
        ERROR(500L);
        private final long value;

        Type(long value)
        {
            this.value = value;
        }

        public long value()
        {
            return this.value;
        }
    }

    /** 状态类型 */
    private Type type;

    /** 状态码 */
    private long code;

    /** 返回内容 */
    private String msg;

    /** 数据对象 */
    private Object data;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public Result()
    {
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     */
    public Result(Type type, String msg)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     * @param data 数据对象
     */
    public Result(Type type, String msg, Object data)
    {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (ObjectUtil.isNotEmpty(data))
        {
            super.put(DATA_TAG, data);
        }
    }


    /**
     * 初始化一个新创建的 Result 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public Result(long code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (ObjectUtil.isNotEmpty(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Result success()
    {
        return Result.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static Result success(Object data)
    {
        return Result.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static Result success(String msg)
    {
        return Result.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static Result success(String msg, Object data)
    {
        return new Result(Type.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Result warn(String msg)
    {
        return Result.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Result warn(String msg, Object data)
    {
        return new Result(Type.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static Result error()
    {
        return Result.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Result error(String msg)
    {
        return Result.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static Result error(String msg, Object data)
    {
        return new Result(Type.ERROR, msg, data);
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public long getCode()
    {
        return (long) this.get("code");
    }

    public void setCode(long code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return (String) this.get("msg");
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return this.get("data");
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
