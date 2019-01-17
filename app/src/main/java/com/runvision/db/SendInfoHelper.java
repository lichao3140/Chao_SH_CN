package com.runvision.db;

import com.runvision.bean.SendInfo;
import com.runvision.g702_sn.MainActivity;

/**
 * 发送给PC数据库操作
 */
public class SendInfoHelper {
    private static String TAG = "SendInfoHelper";

    /**
     * 添加发送记录
     * @param CardName
     * @param CardSex
     * @param CardNation
     * @param CardBorn
     * @param CardCountry
     * @param CardNo
     * @param CardApartment
     * @param CardPeriod
     * @param CardImage
     * @param SnapImage
     * @param CompareScore
     * @param CompareTime
     * @param CompareResult
     */
    public static void addSendInfo(String CardName, String CardSex, String CardNation,
                                   String CardBorn, String CardCountry, String CardNo,
                                   String CardApartment, String CardPeriod, String CardImage,
                                   String SnapImage, float CompareScore, long CompareTime,
                                   String CompareResult) {
        SendInfo sendInfo = new SendInfo();
        sendInfo.setCardName(CardName);
        sendInfo.setCardSex(CardSex);
        sendInfo.setCardNation(CardNation);
        sendInfo.setCardBorn(CardBorn);
        sendInfo.setCardCountry(CardCountry);
        sendInfo.setCardNo(CardNo);
        sendInfo.setCardApartment(CardApartment);
        sendInfo.setCardPeriod(CardPeriod);
        sendInfo.setCardImage(CardImage);
        sendInfo.setSnapImage(SnapImage);
        sendInfo.setCompareScore(CompareScore);
        sendInfo.setCompareTime(CompareTime);
        sendInfo.setCompareResult(CompareResult);
        MainActivity.sendInfoDao.insert(sendInfo);
    }
}
