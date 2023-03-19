package com.kfm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ApiModel
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class User /*extends BaseModel*/ implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,length = 10)
    @NotEmpty(message = "用户名不能为空",groups = {RegGroup.class})
    @Pattern(regexp = "^(\\w{1,10})$", message = "用户名的长度是1-10位!",groups = {RegGroup.class})
    @ApiModelProperty(value = "姓名")
    private String name;
    @Column(length = 32)
    @NotEmpty(message = "密码不能为空",groups = {RegGroup.class})
    @Length(min = 6,max = 32,message = "密码为6-12位",groups = {RegGroup.class})
    @ApiModelProperty(value = "密码")
//    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;//密文 MD5
    @Column(unique = true,length = 13)
    @NotEmpty(message = "电话不能为空",groups = {RegGroup.class})
    @Pattern(regexp = "^(\\d{11,13})$", message = "电话的长度是11-13位!",groups = {RegGroup.class})
    private String mob;
    @Column(unique = true,length = 30)
    @NotEmpty(message = "邮箱不能为空",groups = {RegGroup.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$",
            message = "您的邮箱格式不正确!",groups = {RegGroup.class})
    private String mail;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Transient
    private transient Set<Promiss> promisses;
    @Transient
    private String token;
    @Transient
    private Integer[] roleIds;

    public User(String name, String password, String mob, String mail) {
        this.name = name;
        this.password = password;
        this.mob = mob;
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", mob='" + mob + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    public Set<Promiss> getPromisses() {
        Set<Promiss> promissSet=new HashSet<>();
        if(roles==null) return null;
        for (Role role : roles) {
            for (Promiss promiss : role.getPromisses()) {
                promissSet.add(promiss);
            }
        }
        return promissSet;
    }
    public String getRoleName() {
        String roleName="";
        if(roles==null) return "";
        for (Role role : roles) {
            roleName+=(role.getName()+" ");
        }
        return roleName;
    }


    //    private String creater;
//    private String updater;
//    private Date createTime;
//    private Date updateTime;
}
