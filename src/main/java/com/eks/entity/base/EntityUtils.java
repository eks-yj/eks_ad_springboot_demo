package com.eks.entity.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data//注解在类上;提供类所有非static且非final属性的get和set方法,final属性只提供get方法,此外还提供了equals、canEqual、hashCode、toString 方法
@Accessors(chain = true)//chain=boolean值，默认false。如果设置为true，setter返回的是此对象，方便链式调用方法
@EntityListeners(AuditingEntityListener.class)//将表中数据的创建时间、修改时间交给spring
@MappedSuperclass//JPA基类标识
public class EntityUtils {
    //id, gmt_create, gmt_modified
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;//注意,
    //必备的三个字段,需要给入口加@EnableJpaAuditing及使用new SpringApplicationBuilder(Main.class).web(true).run(args);
    @CreatedDate
    public Date gmtCreate;//创建时间
    @LastModifiedDate
    public Date gmtModified;//修改时间
    public Integer isDeleted;//1 表示删除，0 表示未删除
}
