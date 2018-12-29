package lk.project.marketing.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {

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
    private List<ProductDto> productDtoList;
}
