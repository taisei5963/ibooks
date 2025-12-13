package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.ActionRoleDao;
import jp.blue_dolphin.ibooks.common.database.entity.ActionRole;
import jp.blue_dolphin.ibooks.common.model.ActionRoleModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * アクションロールリポジトリ
 */
@AllArgsConstructor
@Repository
public class ActionRoleRepositoryImpl implements ActionRoleRepository {
    /** アクションロールDAO */
    private ActionRoleDao actionRoleDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionRoleModel> selectById(Long actionRoleId) {
        if (Objects.isNull(actionRoleId)) {
            return Optional.empty();
        }
        ActionRole entity = actionRoleDao.selectById(actionRoleId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionRoleModel> selectInaccessibleByPrivilegeId(Long privilegeId) {
        if (Objects.isNull(privilegeId)) {
            return Collections.emptyList();
        }
        List<ActionRole> entities = actionRoleDao.selectInaccessibleByPrivilegeId(privilegeId);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return Collections.emptyList();
        }
        List<ActionRoleModel> models = new ArrayList<>();
        for (ActionRole entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionRoleModel> selectByPrivilegeId(Long privilegeId) {
        if (Objects.isNull(privilegeId)) {
            return Collections.emptyList();
        }
        List<ActionRole> entities = actionRoleDao.selectByPrivilegeId(privilegeId);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return Collections.emptyList();
        }
        List<ActionRoleModel> models = new ArrayList<>();
        for (ActionRole entity : entities) {
            models.add(convertModel(entity));
        }
        return models;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionRoleModel store(ActionRoleModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<ActionRole> result;
        if (Objects.isNull(model.getActionRoleId())) {
            ActionRoleModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).createdId(createdId).ver(1)
                            .build();
            result = actionRoleDao.insert(convertEntity(setModel));
        } else {
            ActionRoleModel setModel =
                    model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = actionRoleDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * 引数のアクションロールエンティティをアクションロールモデルに変換する
     *
     * @param entity アクションロールエンティティ
     * @return アクションロールモデル
     */
    private ActionRoleModel convertModel(ActionRole entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return ActionRoleModel.builder()
                .actionRoleId(entity.actionRoleId)
                .path(entity.path)
                .privilegeId(entity.privilegeId)
                .availableFlg(entity.availableFlg)
                .note(entity.note)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数のアクションロールモデルをアクションロールエンティティに変換する
     *
     * @param model アクションロールモデル
     * @return アクションロールエンティティ
     */
    private ActionRole convertEntity(ActionRoleModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return ActionRole.builder()
                .actionRoleId(model.getActionRoleId())
                .path(model.getPath())
                .privilegeId(model.getPrivilegeId())
                .availableFlg(model.getAvailableFlg())
                .note(model.getNote())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
