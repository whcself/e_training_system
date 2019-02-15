package com.csu.etrainingsystem.authority;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * 为了方便模糊查询，所以设成String
 */
public class TeacherAuthority {
    public static final String MATERIAL_NULL = "0";
    public static final String MATERIAL_REGISTER = "1"; //物料登记
    public static final String MATERIAL_BUY = String.valueOf(1 << 1);//物料申购
    public static final String MATERIAL_BUY_V = String.valueOf(1 << 2);//申购审核
    public static final String MATERIAL_PURCHASE = String.valueOf(1 << 3);//物料采购
    public static final String REMI_V = String.valueOf(1 << 4);
    public static final String SAVE = String.valueOf(1 << 5);

    public static final String OVERWORK_NULL = "0";
    public static final String OVERWORK_MANAGE = "1";
    public static final String ALL = "%";

    public static boolean HasMaterialRegister(int authNum) {
        return (authNum & (Integer.valueOf(MATERIAL_REGISTER))) == 1;
    }

    public static boolean HasMaterialBuy(int authNum) {
        return (authNum & (Integer.valueOf(MATERIAL_BUY))) == 1;
    }

    public static boolean HasMaterialBuyV(int authNum) {
        return (authNum & (Integer.valueOf(MATERIAL_BUY_V))) == 1;
    }

    public static boolean HasMaterialPurchase(int authNum) {
        return (authNum & (Integer.valueOf(MATERIAL_PURCHASE))) == 1;
    }

    public static boolean HasRemiV(int authNum) {
        return (authNum & (Integer.valueOf(REMI_V))) == 1;
    }

    public static boolean HasSave(int authNum) {
        return (authNum & (Integer.valueOf(SAVE))) == 1;
    }


    public static boolean HasAuth(int authNum,String auth) {
        return (authNum & (Integer.valueOf(auth))) == 1;
    }



}
