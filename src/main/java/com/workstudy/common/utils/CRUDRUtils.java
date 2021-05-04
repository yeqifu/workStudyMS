package com.workstudy.common.utils;

/**
 * @author 刘其悦
 */
public class CRUDRUtils {
    /**
     * 修改返回值
     *
     * @param result
     * @return
     */
    public static R updateR(int result) {
        if (result > 0) {
            return R.ok(Constant.UPDATE_SUCCESS);
        } else {
            return R.error(Constant.RESPONSECODE_FAIL, Constant.UPDATE_FAIL);
        }
    }

    /**
     * 修改返回值
     *
     * @param result
     * @return
     */
    public static R updateR(boolean result) {
        if (result == true) {
            return R.ok(Constant.UPDATE_SUCCESS);
        } else {
            return R.error(Constant.RESPONSECODE_FAIL, Constant.UPDATE_FAIL);
        }
    }

    /**
     * 添加返回值
     *
     * @param result
     * @return
     */
    public static R addR(int result) {
        if (result > 0) {
            return R.ok(Constant.ADD_SUCCESS);
        } else {
            return R.error(Constant.RESPONSECODE_FAIL, Constant.ADD_FAIL);
        }
    }

    /**
     * 添加返回值
     *
     * @param result
     * @return
     */
    public static R addR(boolean result) {
        if (result == true) {
            return R.ok(Constant.OPERATION_SUCCESS);
        } else {
            return R.error(Constant.RESPONSECODE_FAIL, Constant.OPERATION_FAIL);
        }
    }

    /**
     * 删除返回值
     *
     * @param result
     * @return
     */
    public static R deleteR(int result) {
        if (result > 0) {
            return R.ok(Constant.DELETE_SUCCESS);
        } else {
            return R.error(Constant.RESPONSECODE_FAIL, Constant.DELETE_FAIL);
        }
    }

    /**
     * 删除返回值
     *
     * @param result
     * @return
     */
    public static R deleteR(boolean result) {
        if (result == true) {
            return R.ok(Constant.DELETE_SUCCESS);
        } else {
            return R.error(Constant.RESPONSECODE_FAIL, Constant.DELETE_FAIL);
        }
    }

}
