package com.czy.shiyan4.controller;

import com.czy.shiyan4.entity.Filemessage;
import com.czy.shiyan4.entity.Space;
import com.czy.shiyan4.entity.User;
import com.czy.shiyan4.entity.resp.FileMessageDto;
import com.czy.shiyan4.entity.resp.Result;
import com.czy.shiyan4.service.FileMessageService;
import com.czy.shiyan4.service.FileTypeService;
import com.czy.shiyan4.service.SpaceService;
import com.czy.shiyan4.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileMessageService fileMessageService;

    @Autowired
    private FileTypeService fileTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private SpaceService spaceService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file,String token){
        System.out.println(token);
        if(file == null){
            return Result.error().message("文件为空");
        }
        fileMessageService.upload(file,token);
        return Result.ok();
    }


    /**
     * 文件下载
     */
    @GetMapping("/download/{id}")
    public void download(@PathVariable("id")Integer id, HttpServletResponse response) {
        fileMessageService.downLoad(id,response);
    }

    /**
     * 文件删除
     */
    @GetMapping("/delete")
    public void delete(@RequestParam(value = "id") Integer id) {
        fileMessageService.deleteFile(id);
    }

    /**
     * 获取文件信息集合
     * @param sortItem 根据下载次数排序方式 0-升序 1-降序
     * @return
     */
    @GetMapping("/getFileList")
    public Result getFileList(@RequestParam(value = "sortItem",required = false,defaultValue = "1") Integer sortItem,
                               int currentPage,
                               int pageSize){
        PageHelper.startPage(currentPage, pageSize);
        List<Filemessage> list = fileMessageService.getList(sortItem);
        PageInfo<Filemessage> info=new PageInfo<>(list);
        List<FileMessageDto> fileMessageDtos = buildRespParms(list);
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",info.getTotal());
        map.put("list",fileMessageDtos);
        map.put("currentPage",info.getPageNum());
        map.put("totalPage",info.getPages());
        return Result.ok().data(map);
    }

    private List<FileMessageDto> buildRespParms(List<Filemessage> filemessageList){
        List<FileMessageDto> result = new ArrayList<>();
        //查询作者信息和剩余空间大小
        for (Filemessage filemessage : filemessageList) {
            //根据用户id获取用户信息
            User user = userService.getAutorNameById(filemessage.getUid());
            //根据用户id获取剩余空间
            Space space = spaceService.getOneByUid(user.getId());
            //封装结果
            FileMessageDto res = buildRespParm(user,filemessage,space);
            result.add(res);
        }
        return result;
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
    /**
     * 修改文件冻结状态
     */
    @PostMapping("/updateState")
    public Result updateState(@RequestParam("state") Integer state,@RequestParam("id")Integer id){
        fileMessageService.updateState(state,id);
        return Result.ok();
    }
}
