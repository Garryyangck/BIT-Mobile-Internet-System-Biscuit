package com.garry.biscuit.common.consts;

/**
 * 通用常量
 */
public class CommonConst {

    /**
     * 日志跟踪号，MDC 的 key 值
     */
    public static final String LOG_ID = "LOG_ID";

    /**
     * LOG_ID的长度，用于 logback的配置中
     */
    public static final Integer LOG_ID_LENGTH = 18;

    /**
     * 雪花算法的工作 Id
     */
    public static final Integer WORKER_ID = 1;

    /**
     * 雪花算法的机器 Id
     */
    public static final Integer DATACENTER_ID = 1;

    /**
     * JWT 的有效小时数
     */
    public static final Integer JWT_EXPIRE_HOUR = 24;

    /**
     * 导航页默认大小
     */
    public static final Integer DEFAULT_NAVIGATE_PAGES = 8;

    /**
     * 系统的 id
     */
    public static final Long SystemId = 0L;

}
