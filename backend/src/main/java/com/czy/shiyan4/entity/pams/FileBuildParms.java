package com.czy.shiyan4.entity.pams;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class FileBuildParms {

    private String originalFilename;
    private String fileType;
    private Timestamp cur_time;
    private int uid;

}
