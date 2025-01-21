package info.reinput.member.infra.client;

import info.reinput.member.application.dto.FolderCreateReq;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "workspace-service")
public interface WorkspaceClient {
    @PostMapping("/folder/batch-create/v1")
    void createFolder(
            @RequestBody List<FolderCreateReq> folderCreateReqs,
            @RequestHeader("X-User-Id") Long memberId
    );
}
