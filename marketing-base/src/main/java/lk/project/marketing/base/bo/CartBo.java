package lk.project.marketing.base.bo;

import lombok.Data;

import java.util.List;

@Data
public class CartBo {

    /**
     * 购物车所属商家id
     */
    private String shopId;

    /**
     * 购物车所属商铺名称
     */
    private String shopName;

    /**
     * 购物车下订单项列表
     */
    private List<ProductBo> productBoList;
}
