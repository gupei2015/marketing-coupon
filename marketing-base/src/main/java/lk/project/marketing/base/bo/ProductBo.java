package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductBo implements Serializable {

    /**
     * 商品id
     */
    private String skuId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品名称
     */
    private String goodsName;

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
    private BigDecimal price;

    /**
     * 优惠后价格
     */
    private BigDecimal costPrice;

    /**
     * 购买数量
     */
    private Long quantity;

    /**
     * 库存数量
     */
    private Long stockCount;

    /**
     * 商品/服务分类
     */
    private String skuCategory;

    /**
     * 商品型号
     */
    private String productModelNo;

    /**
     * 商品规格
     */
    private String specification;

    /**
     * 商品图片地址
     */
    private String imageUrl;
}
