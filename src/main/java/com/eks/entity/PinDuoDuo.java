package com.eks.entity;

import com.eks.entity.base.EntityUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data//注解在类上;提供类所有非static且非final属性的get和set方法,final属性只提供get方法,此外还提供了equals、canEqual、hashCode、toString 方法
@Accessors(chain = true)//chain=boolean值，默认false。如果设置为true，setter返回的是此对象，方便链式调用方法
@EqualsAndHashCode(callSuper = true)//@Data默认不会调用父类的equals(Object other) 和 hashCode()方法,因此需要加上此注解
@Entity
@Table(name = "tbl_pin_duo_duo")
@EntityListeners(AuditingEntityListener.class)//将表中数据的创建时间、修改时间交给spring
public class PinDuoDuo extends EntityUtils{
    @Column(name = "goods_id", unique = true, nullable = false)
    private String goodsId;
    private String goodsIntroduction;
    private String goodsOriginalPrice;
    private String goodsDiscountPrice;
    private String goodsEarnMoney;
    private String goodsEarnRatio;
    private String shortUrlString;
    private String miniProgramImageUrl;
}
