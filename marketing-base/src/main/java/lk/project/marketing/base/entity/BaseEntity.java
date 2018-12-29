package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity base class created on 2018/9/13.
 */
@Data
public class BaseEntity<T extends Model> extends Model<T> {
    /**
     * 递增ID(主键字段)
     */
    @TableId("id")
    private Long id;

    /**
     * 锁标识
     */
    @Version
    @TableField("version")
    @JsonIgnore
    protected Long version;

    /**
     * 是否有效(0:未删除的有效记录 1:逻辑删除的无效记录)
     */
    @JsonIgnore
    @TableField("is_delete")
    protected Boolean delete;

    /**
     * 创建人ID
     */
    @TableField("creator_id")
    @JsonIgnore
    private Long creatorId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @TableField("created_at")
    @JsonIgnore
    private Date createdAt;

    /**
     * 修改人ID
     */
    @TableField("modifier_id")
    @JsonIgnore
    private Long modifierId;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @TableField("modified_at")
    @JsonIgnore
    private Date modifiedAt;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
