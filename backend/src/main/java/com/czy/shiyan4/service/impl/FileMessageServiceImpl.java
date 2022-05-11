package com.czy.shiyan4.service.impl;

import com.czy.shiyan4.entity.Filemessage;
import com.czy.shiyan4.entity.Filetype;
import com.czy.shiyan4.entity.Space;
import com.czy.shiyan4.entity.User;
import com.czy.shiyan4.entity.resp.FileMessageDto;
import com.czy.shiyan4.entity.resp.Result;
import com.czy.shiyan4.mapper.FilemessageMapper;
import com.czy.shiyan4.service.FileMessageService;
import com.czy.shiyan4.service.FileTypeService;
import com.czy.shiyan4.service.SpaceService;
import com.czy.shiyan4.service.UserService;
import com.czy.shiyan4.utils.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class FileMessageServiceImpl implements FileMessageService {

    @Value("${czy.file.path}")
    private String path;

    @Autowired
    private FileTypeService fileTypeService;

    @Autowired
    private FilemessageMapper filemessageMapper;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private UserService userService;

    @Override
    public void save(Filemessage buildParms) {
        filemessageMapper.insert(buildParms);
    }

    @Override
    public Result upload(MultipartFile file, String token) {

        //4.获取用户Id
        DecodedJWT verify = JwtUtils.verify(token);
        String userId = verify.getClaim("userId").asString();
        int uid = Integer.parseInt(userId);
        //5.获取文件大小(单位-M)
        long size = file.getSize();
        //根据用户Id获取空间大小，判断是否能够上传
        Space space = spaceService.getOneByUid(uid);
        if (space != null && space.getSpaceSize().longValue() < size) {
            return Result.error().message("用户空间不足");
        }

        //1.获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //2.获取保存的UUID文件名
        String uuidFileName = String.valueOf(System.currentTimeMillis());
        //2.获取文件类型
        String fileType = originalFilename.substring(originalFilename.indexOf("."));
        //3.获取当前时间
        Timestamp cur_time = new Timestamp(System.currentTimeMillis());
        //6.根据文件类型获取主键，如果没有则插入
        Filetype filetype = fileTypeService.selectByTypeName(fileType);
        int typeId = 0;
        if (filetype == null) {
            typeId = fileTypeService.save(fileType);
        } else {
            typeId = filetype.getId();
        }
        //封装参数
        Filemessage buildParms = buildParms(originalFilename, typeId, cur_time, uid, uuidFileName, size);


        File newFile = new File(path);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        try {
            file.transferTo(new File(newFile, uuidFileName));
            //保存文件信息
            this.save(buildParms);
            //更新文件类型信息
            if (space != null) {
                //对空间进行更新操作
                Long spaceSize = space.getSpaceSize();
                long curSize = spaceSize - size;
                space.setSpaceSize(curSize);
                spaceService.update(space);
            } else {
                //保存空间信息
                spaceService.save(uid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @Override
    public void downLoad(Integer id, HttpServletResponse response) {
        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //根据文件id获取文件对象
            //Filemessage filemessage = filemessageMapper.getOneByFileName(id);
            Filemessage filemessage = filemessageMapper.selectByPrimaryKey(id);
            String fileNanme = filemessage.getStoreName();
            inputStream = new FileInputStream(new File(path, fileNanme));
            filemessage.setCount(filemessage.getCount() + 1);
            //更新下载次数
            filemessageMapper.updateByPrimaryKey(filemessage);
            //获取文件类型
            String type = fileTypeService.getFileTypeById(filemessage.getTid());
            type = type.substring(type.indexOf(".") + 1);
            System.out.println(type);
            response.setContentType("application/" + type);
            // 设置响应头、以附件形式打开文件
            response.setHeader("content-disposition", "attachment; fileName=" + fileNanme + "." + type);
            outputStream = response.getOutputStream();
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteFile(Integer id) {
        filemessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Filemessage> getList(Integer sortItem) {
        List<Filemessage> list = filemessageMapper.getList(sortItem);
        return list;
    }

    private FileMessageDto buildRespParm(User user, Filemessage filemessage, Space space) {
        FileMessageDto fileMessageDto = new FileMessageDto();
        fileMessageDto.setId(filemessage.getId());
        fileMessageDto.setRealName(filemessage.getRealName());
        fileMessageDto.setStoreName(filemessage.getStoreName());
        fileMessageDto.setState(filemessage.getState());
        fileMessageDto.setCount(filemessage.getCount());
        fileMessageDto.setUploadTime(filemessage.getUploadTime());
        fileMessageDto.setAuthor(user.getName());
        fileMessageDto.setSpareSpace(space.getSpaceSize());
        fileMessageDto.setSize(filemessage.getSize());
        fileMessageDto.setTid(filemessage.getTid());
        fileMessageDto.setUid(filemessage.getUid());
        return fileMessageDto;
    }

    @Override
    public void updateState(Integer state,Integer id) {
        filemessageMapper.updateStateById(state,id);
    }

    private Filemessage buildParms(String originalFilename, int typeId, Timestamp cur_time, int uid, String uuidFileName, long size) {
        Filemessage buildParms = new Filemessage();
        buildParms.setRealName(originalFilename);
        buildParms.setStoreName(uuidFileName);
        buildParms.setUid(uid);
        buildParms.setUploadTime(cur_time);
        buildParms.setSize(size);
        buildParms.setTid(typeId);
        buildParms.setCount(0);
        buildParms.setState(1);
        return buildParms;
    }
}
