package info.reinput.member.application.port.out;

import info.reinput.member.application.dto.FolderCreateReq;

import java.util.List;

public interface WorkspacePort {
    void createFoldersInTopics(final List<FolderCreateReq> folderCreateReqs, final Long memberId);
}
