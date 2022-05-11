package com.czy.shiyan4.entity.resp;

import com.czy.shiyan4.entity.Filemessage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileMessageDto extends Filemessage {

    private String author;
    private Long spareSpace;
}
