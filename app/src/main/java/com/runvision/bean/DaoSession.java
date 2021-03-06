package com.runvision.bean;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.runvision.bean.RecognitionRecord;
import com.runvision.bean.SocketRecord;
import com.runvision.bean.SendInfo;

import com.runvision.bean.RecognitionRecordDao;
import com.runvision.bean.SocketRecordDao;
import com.runvision.bean.SendInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig recognitionRecordDaoConfig;
    private final DaoConfig socketRecordDaoConfig;
    private final DaoConfig sendInfoDaoConfig;

    private final RecognitionRecordDao recognitionRecordDao;
    private final SocketRecordDao socketRecordDao;
    private final SendInfoDao sendInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        recognitionRecordDaoConfig = daoConfigMap.get(RecognitionRecordDao.class).clone();
        recognitionRecordDaoConfig.initIdentityScope(type);

        socketRecordDaoConfig = daoConfigMap.get(SocketRecordDao.class).clone();
        socketRecordDaoConfig.initIdentityScope(type);

        sendInfoDaoConfig = daoConfigMap.get(SendInfoDao.class).clone();
        sendInfoDaoConfig.initIdentityScope(type);

        recognitionRecordDao = new RecognitionRecordDao(recognitionRecordDaoConfig, this);
        socketRecordDao = new SocketRecordDao(socketRecordDaoConfig, this);
        sendInfoDao = new SendInfoDao(sendInfoDaoConfig, this);

        registerDao(RecognitionRecord.class, recognitionRecordDao);
        registerDao(SocketRecord.class, socketRecordDao);
        registerDao(SendInfo.class, sendInfoDao);
    }
    
    public void clear() {
        recognitionRecordDaoConfig.clearIdentityScope();
        socketRecordDaoConfig.clearIdentityScope();
        sendInfoDaoConfig.clearIdentityScope();
    }

    public RecognitionRecordDao getRecognitionRecordDao() {
        return recognitionRecordDao;
    }

    public SocketRecordDao getSocketRecordDao() {
        return socketRecordDao;
    }

    public SendInfoDao getSendInfoDao() {
        return sendInfoDao;
    }

}
