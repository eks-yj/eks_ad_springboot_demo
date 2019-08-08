package com.eks.entity;

import com.eks.entity.base.EntityUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Data//注解在类上;提供类所有非static且非final属性的get和set方法,final属性只提供get方法,此外还提供了equals、canEqual、hashCode、toString 方法
@Accessors(chain = true)//chain=boolean值，默认false。如果设置为true，setter返回的是此对象，方便链式调用方法
@EqualsAndHashCode(callSuper = true)//@Data默认不会调用父类的equals(Object other) 和 hashCode()方法,因此需要加上此注解
@Entity
@Table(name = "tbl_tao_bao")
@EntityListeners(AuditingEntityListener.class)//将表中数据的创建时间、修改时间交给spring
public class TaoBao extends EntityUtils{
    //商品id
    private String shangPinId;
    //商品名称
    private String shangPinMingCheng;
    //商品主图
    private String shangPinZhuTu;
    //商品详情页链接地址
    private String shangPinXiangQingYeLianJieDiZhi;
    //店铺名称
    private String dianPuMingCheng;
    //商品价格(单位：元)
    private String shangPinJiaGe;
    //商品月销量
    private String shangPinYueXiaoLiang;
    //收入比率(%)
    private String shouRuBiLu;
    //佣金
    private String yongJin;
    //卖家旺旺
    private String maiJiaWangWang;
    //淘宝客短链接(300天内有效)
    private String taoBaoKeDuanLianJie;
    //淘宝客链接
    private String taoBaoKeLianJie;
    //淘口令(30天内有效)
    private String taoKouLing;
    //优惠券总量
    private String youHuiQuanZongLiang;
    //优惠券剩余量
    private String youHuiQuanShengYuLiang;
    //优惠券面额
    private String youHuiQuanMianE;
    //优惠券开始时间
    private String youHuiQuanKaiShiShiJian;
    //优惠券结束时间
    private String youHuiQuanJieShuShiJian;
    //优惠券链接
    private String youHuiQuanLianJie;
    //优惠券淘口令(30天内有效)
    private String youHuiQuanTaoKouLing;
    //优惠券短链接(300天内有效)
    private String youHuiQuanDuanLianJie;
    //是否为营销计划商品
    private String shiFouWeiYingXiaoJiHuaShangPin;
    //成团人数
    private String chengTuanRenShu;
    //成团价
    private String chengTuanJia;
    //成团佣金
    private String chengTuanYongJin;
    //拼团开始时间
    private String pinTuanKaiShiShiJian;
    //拼团结束时间
    private String pinTuanJieShuShiJian;
}
