package info.reinput.member.application.dto;

import info.reinput.global.domain.Color;
import lombok.Builder;

@Builder
public record FolderCreateReq(
        String folderName,
        Color folderColor
) {
    public static FolderCreateReq fromDto(String folderName){
        return FolderCreateReq.builder()
                .folderName(folderName)
                .folderColor(Color.BLUE)
                .build();
    }
}
