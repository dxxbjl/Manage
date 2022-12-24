package io.github.yangyouwang.crud.system.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Description: 用户参数DTO<br/>
 * date: 2022/12/24 13:59<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Data
public class UserParamDTO {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号码
     */
    private String phonenumber;
    /**
     * 部门ids
     */
    private List deptIds;
}