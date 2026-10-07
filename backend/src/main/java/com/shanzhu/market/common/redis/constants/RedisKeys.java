package com.shanzhu.market.common.redis.constants;


import java.util.concurrent.TimeUnit;

public enum RedisKeys {
    //用户注册
    LOGIN_USER("LOGIN_USER",1440L,TimeUnit.MINUTES),
    //登录密码错误次数
    LOGIN_ERRO_PWDNUM("LOGIN_ERRO_PWDNUM",1L,TimeUnit.DAYS),
    //商品分类信息的缓存
    GOODS_CATEGORY("GOODS_CATEGORY",24L,TimeUnit.HOURS),
    //账户冻结6小时
    DISABLEUSER("DISABLEUSER",6L,TimeUnit.HOURS);

    private String prefix; //前缀
    private Long timeout; //失效时间
    private TimeUnit timeUnit; //时间单位

    private final String  SEP=":";//key分隔符

    RedisKeys(String prefix, Long timeout, TimeUnit timeUnit) {
        this.prefix = prefix;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    RedisKeys(String prefix, Long timeout) {
        this(prefix, timeout, TimeUnit.MINUTES);
    }
    RedisKeys(String prefix){
        this(prefix,30L,TimeUnit.MINUTES);
    }

    public String join(String ...suffix){

        StringBuilder sb = new StringBuilder(80);
        sb.append(this.prefix);
        for (String s : suffix) {
            sb.append(getSEP()).append(s);
        }
        return sb.toString();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getSEP() {
        return SEP;
    }
}
