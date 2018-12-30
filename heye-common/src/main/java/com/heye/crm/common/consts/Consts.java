package com.heye.crm.common.consts;

/**
 * @author : lishuming
 */
public class Consts {
    public static final String UNSUPPORTED = "NOT_SUPPORTED";
    public static final String CUSTOMER_ALREADY_EXISTED = "CUSTOMER_ALREADY_EXISTED";
    public static final String SUSSESS = "SUSSESS";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String SERVER_FAILED = "SERVER_FAILED";
    public static final String NO_SUCH_OBJECT = "NO_SUCH_OBJECT";
    public static final String ALREADY_EXISTED_IN_DB = "ALREADY_EXISTED_IN_DB";
    public static final String NOT_EXISTED_IN_DB = "NOT_EXISTED_IN_DB";
    public static final String SELECT_FAILED = "SELECT_FAILED";
    public static final String DELETE_FAILED = "DELETE_FAILED";
    public static final String UPDATE_FAILED = "UPDATE_FAILED";
    public static final String CAN_NOT_DELETE = "CAN_NOT_DELETE";
    public static final String INSERT_FAILED = "INSERT_FAILED";
    public static final String ACCOUNT_UNAVALIABLE = "ACCOUNT_UNAVALIABLE";
    public static final String NO_SUCH_USER = "NO_SUCH_USER";
    public static final String ORIGIN_PASSWD_WRONG = "ORIGIN_PASSWD_WRONG";
    public static final String NO_SUCH_ADMIN_USER = "NO_SUCH_ADMIN_USER";
    public static final String NO_SUCH_CUSTOM = "NO_SUCH_CUSTOM";
    public static final String INVALID_PARAM = "INVALID_PARAM";
    public static final String INVALID_SESSION = "INVALID_SESSION";
    public static final String INVALID_RIGHT = "INVALID_RIGHT";
    public static final String SEND_SMS_FAILED = "SEND_SMS_FAILED";
    public static final String VERIFY_CODE_INVALID = "VERIFY_CODE_INVALID";
    public static final String VALIDATE_VERIFY_CODE_FAILED = "VALIDATE_VERIFY_CODE_FAILED";
    public static final String VALIDATE_SMS_CODE_FAILED = "VALIDATE_SMS_CODE_FAILED";
    public static final String TELELPHONE_NUMBER_ALREADT_EXISTED = "TELELPHONE_NUMBER_ALREADT_EXISTED";

    public static final String HY_ROLE_HAS_ALREADY_USER_REGISTED = "HY_ROLE_HAS_ALREADY_USER_REGISTED";
    public static final String HY_ROLE_IS_PARENT_OF_OTHERS = "HY_ROLE_IS_PARENT_OF_OTHERS";

    // 1 hour
    public static final Integer ADMIN_SESSION_LIFE_TIME = 3600;

    public static final int HY_CUSTOM_STATE_OK = 1;
    public static final int HY_CUSTOM_STATE_DELETED = -1;

    public static final String ADMIN_REDIS_SESSION_PREFIX_KEY = "adm_ss_";
    public static final String CTM_REDIS_SESSION_PREFIX_KEY = "ctm_ss_";
    public static final String CTM_REDIS_SESSION_VALIDATE_CODE_PREFIX_KEY = "ctm_vali_code_";

    public static final String CTM_CARE_TASK_QUEUE_NAME = "hy_care_task_q";
    public static final String CTM_CARE_TASK_TMP_QUEUE_NAME = "hy_care_task_tmp_q";
    public static final Integer CTM_CARE_QUEUE_BATCH_SIZE = 100;

    //public static final String CTM_CARE_QUEUE_PREFIX_KEY = "hy_care_q";
    //public static final String CTM_CARE_QUEUE_BAK = "hy_care_tmp";

    // hy_advise
    public static final int HY_ADVISE_STATE_INIT = 1;
    public static final int HY_ADVISE_STATE_DOING = 2;
    public static final int HY_ADVISE_STATE_DONE = 3;

    public static final int HY_ADVISE_TYPE_PRODUCT = 1;
    public static final int HY_ADVISE_TYPE_SALES = 2;

    // admin account state
    public static final int HY_ADMIN_ACCOUNT_STATE_OK = 1;
    public static final int HY_ADMIN_ACCOUNT_STATE_SUSPEND = 2;
    public static final int HY_ADMIN_ACCOUNT_STATE_DESTROYED = -1;

    // range 类型
    public static final int HY_ADMIN_RANGE_TYPE_RANGE = 1;
    public static final int HY_ADMIN_RANGE_TYPE_TOTAL = -1;

    // role
    public static final int HY_ROLE_STATE_OK = 1;
    public static final int HY_ROLE_STATE_DELETED = -1;

    // right
    public static final int HY_RIGHT_STATE_OK = 1;
    public static final int HY_RIGHT_STATE_DELETED = -1;

    // location state
    public static final int HY_LOCATIOIN_STATE_OK = 1;
    public static final int HY_LOCATIOIN_STATE_DELETED = -1;

    // production state
    public static final int HY_PRODUCTION_STATE_OK = 1;
    public static final int HY_PRODUCTION_STATE_DELETED = -1;

    public static final int HY_PRODUCTION_DISPLAY_OK = 1;

    // store state
    public static final int HY_STORE_STATE_OK = 1;
    public static final int HY_STORE_STATE_DELETED = -1;

    // revisit question state
    public static final int HY_REVISIT_QUESTION_STATE_OK = 1;
    public static final int HY_REVISIT_QUESTION_STATE_DELETED = -1;

    // revisit question type
    public static final int HY_REVISIT_QUESTION_TYPE_SINGLE = 1;
    public static final int HY_REVISIT_QUESTION_TYPE_MULTIPLE = 2;
    public static final int HY_REVISIT_QUESTION_TYPE_ASK_AND_ANSWER = 3;

    // revisit naire state
    public static final int HY_REVISIT_NAIRE_STATE_OK = 1;
    public static final int HY_REVISIT_NAIRE_STATE_DELETED = -1;

    // revisit question naire state
    public static final int HY_REVISIT_NAIRE_QUESTION_STATE_OK = 1;
    public static final int HY_REVISIT_NAIRE_QUESTION_STATE_DELETED = -1;

    // 发送状态
    public static final Integer HY_CARE_SEND_STATE_OK = 1;
    public static final Integer HY_CARE_SEND_STATE_INIT = 0;
    public static final Integer HY_CARE_SEND_STATE_FAILED = -1;

    // 短信模板将要状态
    public static final Integer HY_CARE_VERIFY_STATE_OK = 1;
    public static final Integer HY_CARE_VERIFY_STATE_INIT = 0;
    public static final Integer HY_CARE_VERIFY_STATE_FAILED = -1;

    // 开关状态
    public static final Integer HY_CARE_SWITCH_STATE_ON = 1;
    public static final Integer HY_CARE_SWITCH_STATE_OFF = -1;

    // 性别
    public static final Integer HY_CARE_SEX_ALL = 0;
    public static final Integer HY_CARE_SEX_MEN = 1;
    public static final Integer HY_CARE_SEX_WOMEN = 2;

    // aggLevel
    public static final int HY_ANALYZE_AGG_LEVEL_DAY = 0;
    public static final int HY_ANALYZE_AGG_LEVEL_MONTH = 1;
    public static final int HY_ANALYZE_AGG_LEVEL_YEAR = 2;

    // verification code prefix
    public static final String HY_VERIFY_CODE_PREFIX = "hy_sms_msg_code_";

    public static final String DEFAULT_PASSWORD = "123456";
}
