package com.boot.service;



import com.boot.entity.*;

import java.util.List;

public interface FileService {
//    搜索一个文件
    public UploadFiles getAFile(String id);
//    File姓名模糊查询
    public List<UploadFiles> getFileListByLikeName(String userId,String likeName,Integer type);

//    添加一个新文件夹
    public boolean addNewFolder(UploadFiles file);
//    添加一个新文件
    public boolean addNewFile(UploadFiles file);
//    移动一个文件
    public boolean moveFile(String folderId,String fileId);
//    获得根目录下的文件夹和文件
    public List<UploadFiles> getRootFolderList(String userId);
//    获得某个目录下的文件夹和文件
    public List<UploadFiles> getFolderListByID(String userId, String folderId);
//    收藏夹
    public List<UploadFiles>getCollectList(String userId);
//    回收站
    public List<UploadFiles> getRecycleList(String userId);
//    保险柜
    public List<UploadFiles> getSafeList(String userId);
//    删除文件
    public boolean deleteAFileById(String uid,String id);
//    删除文件夹
    public boolean deleteAFolderById(String uid,String id);
//重命名
    public boolean rename(UploadFiles file);
//    移入收藏
    public boolean moveInCollect(String id);
//    移出收藏
    public boolean removeCollect(String id);
//   移入回收站
    public boolean moveInRecycle(String id);
//    移出回收站
    public boolean removeRecycle(String id);
//    移入保险柜
    public boolean moveInSafe(String id);
//    移出保险柜
    public boolean removeSafe(String id);

//    压缩
    public boolean compressionFile(String [] ids,String userId);

//得到所有的文件夹
    public List<UploadFiles>getAllFolder(String depotId);
//    分享文件
    public ShareGroup shareFile(ShareParameter parameter);
//    查询该用户的所有分享信息
    public List<ShareGroup> getAllShareGroup(String userId);
//    查询分享组的所有分享文件
    public List<ShareFile> getAllShareFile(String groupId);
//一个文件是否是正在分享的文件
    public boolean isShareFileExist(String fileId);
//查找分享组
    public ShareGroup selectShareGroup(String id);
//添加分享的文件
    public boolean addShareFile(AddShareUserInfo addShareUserInfo);
//删除分享的文件
    public boolean deleteShareGroup(String groupId);
//    减少用户的文件仓库容量
    public boolean cutCapacity(String userId,long size);
//    获得已过期的分享文件
    public List<ShareGroup> getExpiredShare();
//    获得文件的类型
    public List<FileType>getFileTypes();

}
