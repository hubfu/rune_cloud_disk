package com.boot.service.impl;




import com.boot.entity.*;
import com.boot.mapper.*;
import com.boot.service.FileService;
import com.boot.service.UploaderService;
import com.boot.utils.EntityToConvertUtils;
import com.boot.utils.UploadFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service("fileServiceImpl")
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    UploadFilesMapper filesMapper;
    @Autowired
    ShareGroupMapper shareGroupMapper;
    @Autowired
    ShareFileMapper shareFileMapper;
    @Autowired
    DepotMapper depotMapper;
    @Autowired
    FileTypeMapper fileTypeMapper;
    @Autowired
    UploaderService uploaderService;
    @Value("${file.uploadpath.merge}")
    private String mergeFolder;
    @Value("${file.share.basepath}")
    private String shareBasePath;
    @Override
    public UploadFiles getAFile(String id) {
        return filesMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UploadFiles> getFileListByLikeName(String userId,String likeName,Integer type) {

        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andDepotEqualTo(userId).andNameLike("%"+likeName+"%").andIsSafeEqualTo(Boolean.FALSE).andIsRecycleEqualTo(Boolean.FALSE).andTypeEqualTo(type);
        return filesMapper.selectByExample(example);
    }
    @Override
    /**
     * 添加一个新文件夹
     */
    public boolean addNewFolder(UploadFiles file) {
        file.setType(1);
        if (filesMapper.insertSelective(file)>0){
            return true;
        }
        return false;

    }
//  添加一个新文件
    @Override
    public boolean addNewFile(UploadFiles file) {

        file.setType(UploadFileUtils.getUploadFileType(file.getSuffix()));

        if (filesMapper.insertSelective(file)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean moveFile(String folderId, String fileId) {
        UploadFiles files=filesMapper.selectByPrimaryKey(fileId);
        files.setFolder(folderId);
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<UploadFiles> getRootFolderList(String userId) {
        List<UploadFiles> fileList=new ArrayList<>();
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andDepotEqualTo(userId).andFolderEqualTo(userId).andIsRecycleEqualTo(Boolean.FALSE).andIsSafeEqualTo(Boolean.FALSE);

        fileList=filesMapper.selectByExample(example);
        return fileList;
    }

    @Override
    public List<UploadFiles> getFolderListByID(String userId,String folderId) {
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andFolderEqualTo(folderId).andDepotEqualTo(userId).andIsRecycleEqualTo(Boolean.FALSE);
        List<UploadFiles> folders = new ArrayList<>();
        folders=filesMapper.selectByExample(example);
        return folders;
    }

    @Override
    public List<UploadFiles> getCollectList(String userId) {
        List<UploadFiles> files=new ArrayList<>();
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andDepotEqualTo(userId).andIsCollectEqualTo(Boolean.TRUE);
        files=filesMapper.selectByExample(example);
        return files;
    }

    @Override
    public List<UploadFiles> getRecycleList(String userId) {
        List<UploadFiles> files=new ArrayList<>();
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andDepotEqualTo(userId).andIsRecycleEqualTo(Boolean.TRUE);
        files=filesMapper.selectByExample(example);
        return files;
    }

    @Override
    public List<UploadFiles> getSafeList(String userId) {
        List<UploadFiles> files=new ArrayList<>();
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andDepotEqualTo(userId).andIsSafeEqualTo(Boolean.TRUE);
        files=filesMapper.selectByExample(example);
        return files;
    }

    @Override
    public boolean deleteAFileById(String uid,String id) {
        UploadFiles deleteFile=filesMapper.selectByPrimaryKey(id);
        if(isShareFileExist(id)){
            return false;
        }
        log.info("删除文件："+deleteFile.toString());
        if (deleteFile.getType()==1){
            deleteAFolderById(uid,id);
            filesMapper.deleteByPrimaryKey(deleteFile.getId());
        }else {

           log.info("条件"+(!isShareFileExist(deleteFile.getId()))+(uploaderService.getFilesByMD5(deleteFile.getMd5()).size()==1));
            if ((!isShareFileExist(deleteFile.getId()))&&(uploaderService.getFilesByMD5(deleteFile.getMd5()).size()==1)){
                try {
                    Files.delete(Paths.get(deleteFile.getPath()));
                    log.info("删除");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cutCapacity(uid,deleteFile.getSize());
            filesMapper.deleteByPrimaryKey(deleteFile.getId());
        }
       return true;
    }

    @Override
    public boolean deleteAFolderById(String uid, String id) {
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andFolderEqualTo(id);
        List<UploadFiles> filesList=filesMapper.selectByExample(example);
        for (UploadFiles uploadFiles:filesList){
            if (uploadFiles.getType()!=1){

                try {
                    if (!isShareFileExist(uploadFiles.getId())&&(uploaderService.getFilesByMD5(uploadFiles.getMd5()).size()==1)){
                        Files.delete(Paths.get(uploadFiles.getPath()));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                cutCapacity(uid, uploadFiles.getSize());
                filesMapper.deleteByPrimaryKey(uploadFiles.getId());
            }else {
                deleteAFolderById(uid,uploadFiles.getId());
            }
        }
        return true;
    }

    @Override
    public boolean rename(UploadFiles file) {
        UploadFiles files=filesMapper.selectByPrimaryKey(file.getId());
        files.setName(file.getName());
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean moveInCollect(String id) {
        UploadFiles files=filesMapper.selectByPrimaryKey(id);
        files.setIsCollect(Boolean.TRUE);
        files.setIsSafe(Boolean.FALSE);
        files.setIsRecycle(Boolean.FALSE);
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCollect(String id) {
        UploadFiles files=filesMapper.selectByPrimaryKey(id);
        files.setIsCollect(Boolean.FALSE);
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean moveInRecycle(String id) {
        UploadFiles files=filesMapper.selectByPrimaryKey(id);
        files.setIsRecycle(Boolean.TRUE);
        files.setIsSafe(Boolean.FALSE);
        files.setIsCollect(Boolean.FALSE);
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRecycle(String id) {
        UploadFiles files=filesMapper.selectByPrimaryKey(id);
        files.setIsRecycle(Boolean.FALSE);
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean moveInSafe(String id) {
        UploadFiles files=filesMapper.selectByPrimaryKey(id);
        files.setIsSafe(Boolean.TRUE);
        files.setIsCollect(Boolean.FALSE);
        files.setIsRecycle(Boolean.FALSE);
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean removeSafe(String id) {
        UploadFiles files=filesMapper.selectByPrimaryKey(id);
        files.setIsSafe(Boolean.FALSE);
        if (filesMapper.updateByPrimaryKey(files)>0){
            return true;
        }
        return false;
    }
//压缩
    @Override
    public boolean compressionFile(String []ids,String userId) {
        log.info(Arrays.toString(ids));
        log.info("userid:",userId);
        List<String> paths = new ArrayList<>();
        for (int i=0;i<ids.length;i++ ){
            UploadFiles uploadFiles=new UploadFiles();
            uploadFiles=filesMapper.selectByPrimaryKey(ids[i]);
            if(uploadFiles.getType()==1){
                List<UploadFiles> subUploadFileList = new ArrayList<>();
                subUploadFileList=getFolderListByID(userId,uploadFiles.getId());
                for (UploadFiles subUploadFile:subUploadFileList){
                    paths.add(Paths.get(subUploadFile.getPath()).toAbsolutePath().toString());
                }
            }
            String path=Paths.get(uploadFiles.getPath()).toAbsolutePath().toString();
            log.info("压缩路径:{}",path);
            paths.add(path);

        }
        int num=0;
        String filename=UploadFileUtils.getFileName()+".zip";
        log.info("filenam:"+filename);
        String zipPath=mergeFolder+File.separator+userId+File.separator+filename;
        zipPath=Paths.get(zipPath).toAbsolutePath().toString();
        log.info("zipPath:"+zipPath);
        try {
          num =  UploadFileUtils.compress(paths,zipPath,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadFiles uploadFiles=new UploadFiles();
        uploadFiles.setId(UploadFileUtils.getFileName());
        uploadFiles.setType(1);
        uploadFiles.setUploadDate(new Date());
        uploadFiles.setFolder(userId);
        uploadFiles.setDepot(userId);
        uploadFiles.setPath(zipPath);
        uploadFiles.setName(filename);
        uploadFiles.setModifiedDate(uploadFiles.getUploadDate());
        uploadFiles.setSize(new File(zipPath).length());
        uploadFiles.setSuffix(UploadFileUtils.getSuffix(filename));
        try {
           FileInputStream fileInputStream=new FileInputStream(zipPath);
            uploadFiles.setMd5(DigestUtils.md5Hex(fileInputStream));
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addNewFile(uploadFiles);
        uploaderService.addUsedCapacity(userId, uploadFiles.getSize());
        if (num>0){
            return  true;
        }
        return false;
    }

    @Override
    public List<UploadFiles> getAllFolder(String depotId) {
        UploadFilesExample example=new UploadFilesExample();
        example.createCriteria().andDepotEqualTo(depotId).andTypeEqualTo(1);
        return filesMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public ShareGroup shareFile(ShareParameter parameter) {
        UploadFiles files=filesMapper.selectByPrimaryKey(parameter.getFileId());
        String groupId= UploadFileUtils.getFileName();
        ShareGroup shareGroup=new ShareGroup();
        shareGroup.setId(groupId);
        shareGroup.setUserId(parameter.getUserId());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,parameter.getShareTime());
        shareGroup.setEndTime(calendar.getTime());
        shareGroup.setIsEncrypt(parameter.getIsEncryptCode());
        shareGroup.setExtractionCode(parameter.getExtractionCode());
        String sharePath=shareBasePath+groupId;
        shareGroup.setSharePath(sharePath);
        ShareFile shareFile=new ShareFile();
        shareFile.setGroudId(groupId);
        shareFile.setFileName(files.getName());
        shareFile.setFilePath(files.getPath());
        shareFile.setFileId(files.getId());
        shareGroupMapper.insertSelective(shareGroup);
        shareFileMapper.insertSelective(shareFile);
        return shareGroup;
    }

    @Override
    public List<ShareGroup> getAllShareGroup(String userId) {
        ShareGroupExample example=new ShareGroupExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return shareGroupMapper.selectByExample(example);
    }

    @Override
    public List<ShareFile> getAllShareFile(String groupId) {
        ShareFileExample example=new ShareFileExample();
        example.createCriteria().andGroudIdEqualTo(groupId);
        return shareFileMapper.selectByExample(example);
    }

    @Override
    public boolean isShareFileExist(String fileId) {
        ShareFileExample example=new ShareFileExample();
        example.createCriteria().andFileIdEqualTo(fileId);
        if (shareFileMapper.countByExample(example)>0){
            return true;
        }
        return false;
    }

    @Override
    public ShareGroup selectShareGroup(String id) {
        return shareGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean addShareFile(AddShareUserInfo addShareUserInfo) {
        boolean isSuccess=false;
        ShareFileExample example=new ShareFileExample();
        example.createCriteria().andGroudIdEqualTo(addShareUserInfo.getId());
    List<ShareFile> shareFiles=shareFileMapper.selectByExample(example);

    for (ShareFile file:shareFiles){
        UploadFiles files=filesMapper.selectByPrimaryKey(file.getFileId());
        files.setId(UploadFileUtils.getFileName());
         files.setDepot(addShareUserInfo.getUserId());
         files.setUploadDate(new Date());
         files.setModifiedDate(files.getUploadDate());
         files.setIsRecycle(Boolean.FALSE);
         files.setIsCollect(Boolean.FALSE);
         files.setIsSafe(Boolean.FALSE);
         files.setFolder(addShareUserInfo.getUserId());
         if (filesMapper.insertSelective(files)>0){
             isSuccess=true;
         }else{
             isSuccess=false;
         }


    }
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean deleteShareGroup(String groupId) {
        ShareFileExample example=new ShareFileExample();
        example.createCriteria().andGroudIdEqualTo(groupId);
        shareFileMapper.deleteByExample(example);
        if (shareGroupMapper.deleteByPrimaryKey(groupId)>0){
            return true;
        }
        return false;
    }
//减少系统容量
    @Override
    public boolean cutCapacity(String userId, long size) {
        Depot depot=depotMapper.selectByPrimaryKey(userId);
        if(!depot.getUsedCapacity().equals(0L)) {
            depot.setUsedCapacity(depot.getUsedCapacity() - size);
            if (depotMapper.updateByPrimaryKeySelective(depot) > 1) {
                return true;
            }

        }
        return false;
    }
//获得过期的分享文件
    @Override
    public List<ShareGroup> getExpiredShare() {
        ShareGroupExample example=new ShareGroupExample();
        example.createCriteria().andEndTimeLessThan(new Date());
        return shareGroupMapper.selectByExample(example);
    }

    @Override
    public List<FileType> getFileTypes() {
        FileTypeExample example=new FileTypeExample();
        example.createCriteria().andNameNotEqualTo("");
        return fileTypeMapper.selectByExample(example);
    }

}
