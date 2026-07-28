package com.techcof.ITive.admin.service;

import com.techcof.ITive.admin.dto.AdminDto;
import com.techcof.ITive.common.database.repository.ActionRoleRepository;
import com.techcof.ITive.common.model.ActionRoleModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * アクションロールサービス
 */
@AllArgsConstructor
@Service
public class ActionRoleService {
    /** アクションロールリポジトリ */
    private ActionRoleRepository actionRoleRepository;
    /** 管理者DTO */
    private AdminDto adminDto;

    /**
     * 指定した権限IDで実行不可なアクションパスリストを取得する
     *
     * @param privilegeId 権限ID
     * @return アクションパスリスト
     */
    public List<String> getInaccessibleActionPaths(Long privilegeId) {
        List<String> actionPaths = new ArrayList<>();
        List<ActionRoleModel> models =
                actionRoleRepository.selectInaccessibleByPrivilegeId(privilegeId);
        models.forEach(a -> actionPaths.add(a.getPath()));
        return actionPaths;
    }

    /**
     * 引数の権限IDでのアクションロールリストを取得する
     *
     * @param privilegeId 権限ID
     * @return アクションロールリスト
     */
    public List<ActionRoleModel> selectByPrivilegeId(Long privilegeId) {
        return actionRoleRepository.selectByPrivilegeId(privilegeId);
    }
}
