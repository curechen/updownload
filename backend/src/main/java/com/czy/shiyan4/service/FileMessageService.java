package com.czy.shiyan4.service;

import com.czy.shiyan4.entity.Filemessage;
import com.czy.shiyan4.entity.resp.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileMessageService {
    void save(Filemessage buildParms);

    Result upload(MultipartFile file, String token);

    void downLoad(Integer id, HttpServletResponse response);

    void deleteFile(Integer id);

    List<Filemessage> getList(Integer sortItem);

    void updateState(Integer state,Integer id);
}
