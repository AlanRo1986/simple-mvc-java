package com.lanxinbase.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alan.luo on 2017/11/1.
 */
public class ConstantAuthority {

    public static final Map<String, Boolean> authority = new HashMap<String, Boolean>() {
        {
            this.put("/api/upload/base64", true);
            this.put("/api/upload", true);
            this.put("/api/getUserInfo", true);
            this.put("/api/getUserOrder", true);
            this.put("/api/getUserFav", true);
            this.put("/api/saveUserFav", true);
            this.put("/api/delUserFav", true);
            this.put("/api/loginOut", true);
            this.put("/api/getUser", true);
            this.put("/api/saveUser", true);
            this.put("/api/doPay", true);
            this.put("/api/getLoadByDealId", true);
            this.put("/api/getDealTypes", true);
            this.put("/api/saveDeal", true);
            this.put("/api/initBoatOrderUser", true);
            this.put("/api/initBuyBoatTicket", true);
            this.put("/api/getBoatOrderUserById", true);
            this.put("/api/getBoatOrderUserByPaySn", true);
            this.put("/api/getUserBoatOrder", true);
            this.put("/api/getUserDealLoad", true);
            this.put("/api/getUserBag", true);
            this.put("/api/delUserShare", true);
            this.put("/api/getUserShare", true);
            this.put("/api/getUserLog", true);
            this.put("/api/getUserBankCard", true);
            this.put("/api/saveUserBankCard", true);
            this.put("/api/putUserBankCard", true);
            this.put("/api/delUserBankCard", true);
            this.put("/api/initUserWithdraw", true);
            this.put("/api/saveUserWithdraw", true);
            this.put("/api/getUserDealLoadById", true);
            this.put("/api/getDealCert", true);

        }
    };

}
