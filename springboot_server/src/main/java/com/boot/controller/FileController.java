package com.boot.controller;

import com.boot.entity.*;
import com.boot.service.FileService;
import com.boot.service.UploaderService;
import com.boot.service.UserService;
import com.boot.utils.EntityToConvertUtils;
import com.boot.utils.UploadFileUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;
    @Autowired
    UploaderService uploaderService;

    //    查询主目录下的文件
    @GetMapping("root/list/{userId}/{pageNum}")
    public CommonResult getRootList(@PathVariable String userId, @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);

        List<UploadFiles> rootList = new ArrayList<>();
        rootList = fileService.getRootFolderList(userId);
        List<UploadFileInfo> rootInfoList = EntityToConvertUtils.filesListToConvert(rootList);
        PageInfo pageInfo = new PageInfo<>(rootList);
        pageInfo.setList(rootInfoList);
        if (rootList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    //    查询收藏夹文件
    @GetMapping("collect/list/{userId}/{pageNum}")
    public CommonResult getCollectList(@PathVariable String userId, @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);

        List<UploadFiles> rootList = new ArrayList<>();
        rootList = fileService.getCollectList(userId);
        List<UploadFileInfo> rootInfoList = EntityToConvertUtils.filesListToConvert(rootList);
        PageInfo pageInfo = new PageInfo<>(rootList);
        pageInfo.setList(rootInfoList);
        if (rootList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    //    查询保险箱文件
    @GetMapping("safe/list/{userId}/{pageNum}")
    public CommonResult getSafeList(@PathVariable String userId, @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);

        List<UploadFiles> rootList = new ArrayList<>();
        rootList = fileService.getSafeList(userId);
        List<UploadFileInfo> rootInfoList = EntityToConvertUtils.filesListToConvert(rootList);
        PageInfo pageInfo = new PageInfo<>(rootList);
        pageInfo.setList(rootInfoList);
        if (rootList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    //    查询回收站文件
    @GetMapping("recycle/list/{userId}/{pageNum}")
    public CommonResult getRecycleList(@PathVariable String userId, @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);

        List<UploadFiles> rootList = new ArrayList<>();
        rootList = fileService.getRecycleList(userId);
        List<UploadFileInfo> rootInfoList = EntityToConvertUtils.filesListToConvert(rootList);
        PageInfo pageInfo = new PageInfo<>(rootList);
        pageInfo.setList(rootInfoList);
        if (rootList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    //    查询文件夹内容
    @GetMapping("folder/list/{userId}/{folderId}/{pageNum}")
    public CommonResult getFolderList(@PathVariable String userId, @PathVariable String folderId, @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<UploadFiles> folderList = new ArrayList<>();
        folderList = fileService.getFolderListByID(userId, folderId);
        List<UploadFileInfo> fileInfos = EntityToConvertUtils.filesListToConvert(folderList);
        PageInfo pageInfo = new PageInfo<>(folderList);
        pageInfo.setList(fileInfos);
        if (folderList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");

    }

    //    新建文件夹
    @PostMapping("/folder/add")
    public CommonResult addNewFolder(@RequestBody UploadFiles files) {
        UploadFiles pFolder = fileService.getAFile(files.getFolder());
        files.setUploadDate(new Date());
        files.setModifiedDate(files.getUploadDate());
        files.setId(UploadFileUtils.getFileName());
        files.setMd5(DigestUtils.md5DigestAsHex(files.getId().getBytes()));
        String newPath = pFolder.getPath() + File.separator + files.getName();
        files.setPath(newPath);
        if (fileService.addNewFolder(files)) {
            return new CommonResult(CommonResult.SUCCESS, "创建成功");
        }
        return new CommonResult(CommonResult.FAIL, "创建失败");
    }

    //文件模糊查询
    @GetMapping("/like/name/{userId}/{likeName}/{pageNum}/{type}")
    public CommonResult getListLikeName(@PathVariable String userId, @PathVariable String likeName, @PathVariable Integer pageNum,@PathVariable Integer type) {
        log.info("type:{}",type);
        PageHelper.startPage(pageNum, 5);
        List<UploadFiles> likeList = fileService.getFileListByLikeName(userId, likeName,type);
        List<UploadFileInfo> infoList = EntityToConvertUtils.filesListToConvert(likeList);
        PageInfo pageInfo = new PageInfo<>(likeList);
        pageInfo.setList(infoList);
        if (likeList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "查询失败");
    }

    //重命名
    @PutMapping("/rename")
    public CommonResult fileRename(@RequestBody UploadFiles files) {
        if (fileService.rename(files)) {
            return new CommonResult(CommonResult.SUCCESS, "修改成功");

        }
        return new CommonResult(CommonResult.FAIL, "修改失败");
    }
//移入收藏夹
    @GetMapping("/collect/move/{fileId}")
    public CommonResult moveInCollect(@PathVariable String fileId) {
        if (fileService.moveInCollect(fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "移入成功");
        }
        return new CommonResult(CommonResult.FAIL, "移入失败");
    }

    @GetMapping("/collect/remove/{fileId}")
    public CommonResult removeInCollect(@PathVariable String fileId) {
        if (fileService.removeCollect(fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "移出成功");
        }
        return new CommonResult(CommonResult.FAIL, "移出失败");
    }
//移入回收站
    @GetMapping("/recycle/move/{fileId}")
    public CommonResult moveInRecycle(@PathVariable String fileId) {
        if (fileService.moveInRecycle(fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "移入成功");
        }
        return new CommonResult(CommonResult.FAIL, "移入失败");
    }

    @GetMapping("/recycle/remove/{fileId}")
    public CommonResult removeInRecycle(@PathVariable String fileId) {
        if (fileService.removeRecycle(fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "移出成功");
        }
        return new CommonResult(CommonResult.FAIL, "移出失败");
    }
//移入保险箱
    @GetMapping("/safe/move/{fileId}")
    public CommonResult moveInSafe(@PathVariable String fileId) {
        if (fileService.moveInSafe(fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "移入成功");
        }
        return new CommonResult(CommonResult.FAIL, "移入失败");
    }

    @GetMapping("/safe/remove/{fileId}")
    public CommonResult removeInSafe(@PathVariable String fileId) {
        if (fileService.removeSafe(fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "恢复成功");
        }
        return new CommonResult(CommonResult.FAIL, "恢复失败");
    }
//删除文件
    @DeleteMapping("/delete/{fileId}")
    public CommonResult deleteFile(@PathVariable String fileId) {
        UploadFiles files = fileService.getAFile(fileId);

        if (fileService.deleteAFileById(files.getDepot(),fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "删除成功");
        }
        return new CommonResult(CommonResult.FAIL, "删除失败,请取消该文件的分享");
    }
//分享文件
    @PostMapping("/share")
    public CommonResult shareFile(@RequestBody ShareParameter parameter) {
        log.info("para:" + parameter.toString());
        ShareGroup shareGroup = fileService.shareFile(parameter);
        if (shareGroup != null) {
            return new CommonResult(CommonResult.SUCCESS, shareGroup.getSharePath(), "分享成功");
        }
        return new CommonResult(CommonResult.FAIL, "分享失败");
    }

    //    添加分享文件
    @PostMapping("/share/add")
    public CommonResult addShareFile(@RequestBody AddShareUserInfo addShareUserInfo) {
        log.info("add" + addShareUserInfo.toString());
        ShareGroup group = fileService.selectShareGroup(addShareUserInfo.getId());
        if (addShareUserInfo.getIsEncrypt()) {
            if (!addShareUserInfo.getExtractionCode().equals(addShareUserInfo.getExtractionCode())) {
                return new CommonResult(CommonResult.FAIL, "提取码错误");
            }
        }
        if (fileService.addShareFile(addShareUserInfo)) {
            return new CommonResult(CommonResult.SUCCESS, "添加成功");
        }
        return new CommonResult(CommonResult.FAIL, "添加失败");
    }

    //获得分享文件信息
    @GetMapping("/share/group/info/{userID}/{pageNum}")
    public CommonResult getGroupInfo(@PathVariable String userID, @PathVariable Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<ShareGroup> shareGroupList = fileService.getAllShareGroup(userID);
        List<ShareInfo> shareInfoList = new ArrayList<>();
        for (ShareGroup shareGroup : shareGroupList) {
            ShareInfo shareInfo = new ShareInfo();
            shareInfo.toConvert(shareGroup);
            shareInfoList.add(shareInfo);
        }
        PageInfo pageInfo = new PageInfo<>(shareGroupList);
        pageInfo.setList(shareInfoList);
        if (shareGroupList != null) {
            return new CommonResult(CommonResult.SUCCESS, pageInfo, "显示成功");
        }
        return new CommonResult(CommonResult.FAIL, "显示失败");
    }

    //    取消分享
    @DeleteMapping("/share/group/{groupId}")
    public CommonResult delete(@PathVariable String groupId) {
        if (fileService.deleteShareGroup(groupId)) {
            return new CommonResult(CommonResult.SUCCESS, "取消成功");
        }
        return new CommonResult(CommonResult.FAIL, "取消失败");
    }
//获得分享组信息
    @GetMapping("/share/group/{groupId}")
    public CommonResult getAShareGroup(@PathVariable String groupId) {
        ShareGroup shareGroup = fileService.selectShareGroup(groupId);
        if (shareGroup != null) {
            return new CommonResult(CommonResult.SUCCESS, shareGroup, "查询成功");
        }
        return new CommonResult(CommonResult.FAIL, "已取消分享");
    }

    //    查询所有文件夹
    @GetMapping("/all/folder/{depotId}")
    public CommonResult getAllFolder(@PathVariable String depotId) {
        List<UploadFiles> folders = new ArrayList<>();
        folders = fileService.getAllFolder(depotId);
        List<UploadFileInfo> folderInfos = new ArrayList<>();
        folderInfos = EntityToConvertUtils.filesListToConvert(folders);
        return new CommonResult(CommonResult.SUCCESS, folderInfos, "查询成功");
    }
//移动文件夹
    @GetMapping("/move/{folderId}/{fileId}")
    public CommonResult moveInFolder(@PathVariable String folderId, @PathVariable String fileId) {
        if (fileService.moveFile(folderId, fileId)) {
            return new CommonResult(CommonResult.SUCCESS, "移动成功");

        }
        return new CommonResult(CommonResult.FAIL, "移动失败");
    }
//压缩
    @PostMapping("/compression")
    public CommonResult compressionFile(@RequestBody CheckParams checkParams) {
        if (fileService.compressionFile(checkParams.getIds(), checkParams.getUserId())) {
            return new CommonResult(CommonResult.SUCCESS, "压缩成功");
        }
        return new CommonResult(CommonResult.FAIL, "压缩失败");
    }
//获得压缩文件信息
    @GetMapping("/zip/info/{fileId}")
    public CommonResult getZipInfo(@PathVariable String fileId) {
        UploadFiles uploadFiles = fileService.getAFile(fileId);
        if (!uploadFiles.getSuffix().equals("zip")) {
            return new CommonResult(CommonResult.FAIL, "仅支持ZIP格式");
        }
        String path = Paths.get(uploadFiles.getPath()).toAbsolutePath().toString();
        List<String> fileNameLit = UploadFileUtils.readZipFileName(path);
        if (fileNameLit.size() > 0) {
            return new CommonResult(CommonResult.SUCCESS, fileNameLit, "解压成功");
        }
        return new CommonResult(CommonResult.FAIL, "解压失败");
    }
//批量回收
    @PostMapping("/batch/recycle")
    public CommonResult batchByRecycle(@RequestBody CheckParams checkParams) {
        for (int i = 0; i < checkParams.getIds().length; i++) {
            fileService.moveInRecycle(checkParams.getIds()[i]);
        }
        return new CommonResult(CommonResult.SUCCESS, "回收成功");
    }
//批量删除
    @PostMapping("/batch/delete")
    public CommonResult batchDelete(@RequestBody CheckParams checkParams) {
        for (int i = 0; i < checkParams.getIds().length; i++) {
            UploadFiles files = fileService.getAFile(checkParams.getIds()[i]);
            if (!fileService.deleteAFileById(files.getDepot(),files.getId())){
                return new CommonResult(CommonResult.FAIL, "删除失败,存在分享的文件");
            }


        }
        return new CommonResult(CommonResult.SUCCESS, "删除成功");
    }
    //获得文件类型
    @GetMapping("/filetype")
    public List<FileType> getFileTypes(){
        log.info("type:"+fileService.getFileTypes());
        return fileService.getFileTypes();
    }
}
