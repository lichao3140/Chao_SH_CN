package com.runvision.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 发送给PC信息
 */

@Entity
public class SendInfo {

    @Id(autoincrement = true)
    private Long id;

    /**
     * 身份证-姓名
     */
    @Property(nameInDb = "CardName")
    private String CardName;

    /**
     * 身份证-性别
     */
    @Property(nameInDb = "CardSex")
    private String CardSex;

    /**
     * 身份证-民族
     */
    @Property(nameInDb = "CardNation")
    private String CardNation;

    /**
     * 身份证-出生日期
     */
    @Property(nameInDb = "CardBorn")
    private String CardBorn;

    /**
     * 身份证-出生地址
     */
    @Property(nameInDb = "CardCountry")
    private String CardCountry;

    /**
     * 身份证-身份证号码
     */
    @Property(nameInDb = "CardNo")
    private String CardNo;

    /**
     * 身份证-签发机关
     */
    @Property(nameInDb = "CardApartment")
    private String CardApartment;

    /**
     * 身份证-有效期限
     */
    @Property(nameInDb = "CardPeriod")
    private String CardPeriod;

    /**
     * 身份证-身份证照片路径
     */
    @Property(nameInDb = "CardImage")
    private String CardImage;

    /**
     * 抓拍照片路径
     */
    @Property(nameInDb = "SnapImage")
    private String SnapImage;

    /**
     * 对比分数
     */
    @Property(nameInDb = "CompareScore")
    private float CompareScore;

    /**
     * 对比时间
     */
    @Property(nameInDb = "CompareTime")
    private long CompareTime;

    /**
     * 对比结果
     */
    @Property(nameInDb = "CompareResult")
    private String CompareResult;

    @Generated(hash = 1278649955)
    public SendInfo(Long id, String CardName, String CardSex, String CardNation,
            String CardBorn, String CardCountry, String CardNo,
            String CardApartment, String CardPeriod, String CardImage,
            String SnapImage, float CompareScore, long CompareTime,
            String CompareResult) {
        this.id = id;
        this.CardName = CardName;
        this.CardSex = CardSex;
        this.CardNation = CardNation;
        this.CardBorn = CardBorn;
        this.CardCountry = CardCountry;
        this.CardNo = CardNo;
        this.CardApartment = CardApartment;
        this.CardPeriod = CardPeriod;
        this.CardImage = CardImage;
        this.SnapImage = SnapImage;
        this.CompareScore = CompareScore;
        this.CompareTime = CompareTime;
        this.CompareResult = CompareResult;
    }

    @Generated(hash = 1964384633)
    public SendInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardName() {
        return this.CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public String getCardSex() {
        return this.CardSex;
    }

    public void setCardSex(String CardSex) {
        this.CardSex = CardSex;
    }

    public String getCardNation() {
        return this.CardNation;
    }

    public void setCardNation(String CardNation) {
        this.CardNation = CardNation;
    }

    public String getCardBorn() {
        return this.CardBorn;
    }

    public void setCardBorn(String CardBorn) {
        this.CardBorn = CardBorn;
    }

    public String getCardCountry() {
        return this.CardCountry;
    }

    public void setCardCountry(String CardCountry) {
        this.CardCountry = CardCountry;
    }

    public String getCardNo() {
        return this.CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public String getCardApartment() {
        return this.CardApartment;
    }

    public void setCardApartment(String CardApartment) {
        this.CardApartment = CardApartment;
    }

    public String getCardPeriod() {
        return this.CardPeriod;
    }

    public void setCardPeriod(String CardPeriod) {
        this.CardPeriod = CardPeriod;
    }

    public String getCardImage() {
        return this.CardImage;
    }

    public void setCardImage(String CardImage) {
        this.CardImage = CardImage;
    }

    public String getSnapImage() {
        return this.SnapImage;
    }

    public void setSnapImage(String SnapImage) {
        this.SnapImage = SnapImage;
    }

    public float getCompareScore() {
        return this.CompareScore;
    }

    public void setCompareScore(float CompareScore) {
        this.CompareScore = CompareScore;
    }

    public long getCompareTime() {
        return this.CompareTime;
    }

    public void setCompareTime(long CompareTime) {
        this.CompareTime = CompareTime;
    }

    public String getCompareResult() {
        return this.CompareResult;
    }

    public void setCompareResult(String CompareResult) {
        this.CompareResult = CompareResult;
    }

}
