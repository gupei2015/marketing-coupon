package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberDto implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 用户经验值
     */
    private Long expValue;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 联系电话
     */
    private String phoneNumber;
}
