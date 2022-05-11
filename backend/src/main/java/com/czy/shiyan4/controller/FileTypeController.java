package com.czy.shiyan4.controller;

import com.czy.shiyan4.entity.Filetype;
import com.czy.shiyan4.entity.resp.Result;
import com.czy.shiyan4.service.FileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fileType")
public class FileTypeController {

    @Autowired
    private FileTypeService fileTypeService;

    /**
     * 获取文件类型列表
     * @return
     */
    @GetMapping("/getList")
    public Result getTypeList(){
        List<Filetype> list = fileTypeService.getTypeList();
        return Result.ok().data("typeList",list);
    }


}
