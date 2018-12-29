package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDto implements Serializable {

    /**
     * 商品id
     */
    private String skuId;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商家id
     */
    private String shopId;

    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 商品价格
     */
    private BigDecimal originPrice;

    /**
     * 优惠后价格
     */
    private BigDecimal actualPrice;

    /**
     * 购买数量
     */
    private Long purchaseQuantity;

    /**
     * 库存数量
     */
    private Long stockQuantity;

    /**
     * 商品/服务分类
     */
    private String skuCategoryName;

    /**
     * 商品型号
     */
    private String productModelNo;

    /**
     * 商品规格
     */
    private String productSpec;

}
