package com.boot.service.impl;

import com.boot.entity.*;
import com.boot.mapper.*;
import com.boot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
@Slf4j
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private static final Long depotSize =5*1024*1024*1024L;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ConsumeMapper consumeMapper;
    @Autowired
    DepotMapper depotMapper;
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    UploadFilesMapper uploadFilesMapper;
    @Value("${file.uploadpath.merge}")
    private String mergeFolder;
    @Override
    public User selectAUser(String  userID) {
        return userMapper.selectByPrimaryKey(userID);
    }

    @Override
    @Transactional
    public boolean insertAUser(User user) {
        Depot newDepot = new Depot();
        newDepot.setId(user.getId());
        newDepot.setUsedCapacity(0L);
        newDepot.setBuyCapacity(0L);
        newDepot.setMaxCapacity(depotSize);
        UploadFiles rootFolder=new UploadFiles();
        rootFolder.setId(user.getId());
        rootFolder.setName(user.getId());
        rootFolder.setDepot(user.getId());
        rootFolder.setMd5(DigestUtils.md5DigestAsHex(user.getId().getBytes()));
        rootFolder.setUploadDate(new Date());
        rootFolder.setModifiedDate(rootFolder.getUploadDate());
        rootFolder.setFolder("/");
        rootFolder.setType(1);
        rootFolder.setPath(mergeFolder+ File.separator+user.getId());
        if(insertUserDepot(newDepot)){
            user.setDepot(user.getId());
            if (userMapper.insertSelective(user) > 0&&uploadFilesMapper.insertSelective(rootFolder)>0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean updateAUserInfo(User user) {

        log.info("user:"+user.toString());
        if (userMapper.updateByPrimaryKeySelective(user)>0) {
            return true;
        }

        return false;
    }

    @Override
    public List<Consume> selectAllConsumeInfo(String  userID) {
        ConsumeExample example=new ConsumeExample();
        example.createCriteria().andUserIdEqualTo(userID);
        List<Consume> consumeList=consumeMapper.selectByExample(example);
        return consumeList;
    }

    @Override
    @Transactional
    public Depot selectDepotInfo(String userID) {
       String  depotID=userMapper.selectByPrimaryKey(userID).getDepot();

        return depotMapper.selectByPrimaryKey(depotID);
    }

    @Override
    public boolean insertAFeedback(Feedback feedback) {
        if (feedbackMapper.insertSelective(feedback)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAFeedback(Integer id) {
        if (feedbackMapper.deleteByPrimaryKey(id)>0){
            return true;
        }
        return false;
    }


    @Override
    public List<Feedback> selectUserAllFeedbackResult(String  userID) {
//        log.info("userID:"+userID);
        FeedbackExample example=new FeedbackExample();
        example.createCriteria().andUserIdEqualTo(userID);
//        log.info("结果"+feedbackMapper.selectByExample(example));
        return feedbackMapper.selectByExample(example);
    }

    @Override
    public boolean insertUserConsumeInfo(Consume consume) {
        if (consumeMapper.insert(consume)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Consume> selectUserAllConsumeInfo(String  userID) {
        ConsumeExample example=new ConsumeExample();
        example.createCriteria().andUserIdEqualTo(userID);

        return consumeMapper.selectByExample(example);
    }



    @Override
    public boolean insertUserDepot(Depot depot) {
        if (depotMapper.insert(depot)>0){
            return true;
        }
       return false;
    }

    @Override
    public boolean updateUserDepot(Depot depot) {
        if ( depotMapper.updateByPrimaryKeySelective(depot)>0){
            return true;
        }
       return  false;
    }

    @Override
    public boolean deleteAConsume(Integer id) {
        if (consumeMapper.deleteByPrimaryKey(id)>0){
            return true;
        }
        return false;
    }


}
