package com.heye.crm.server.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : lishuming
 */
@Setter
@Getter
public class HyConsumeReq extends Req {
    private String proBarCode;
    private long ctmId;
}
