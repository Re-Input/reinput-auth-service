package info.reinput.member.infra.client;

import info.reinput.member.application.dto.FolderCreateReq;
import info.reinput.member.application.port.out.WorkspacePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkspaceFeignAdapter implements WorkspacePort {

    private final WorkspaceClient workspaceClient;

    @Override
    public void createFoldersInTopics(final List<FolderCreateReq> folderCreateReqs, final Long memberId) {
        log.info("createFoldersInTopics start");

        workspaceClient.createFolder(folderCreateReqs, memberId);

    }

}
