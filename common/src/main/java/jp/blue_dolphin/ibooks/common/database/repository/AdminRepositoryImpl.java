package jp.blue_dolphin.ibooks.common.database.repository;

import jp.blue_dolphin.ibooks.common.database.dao.AdminDao;
import jp.blue_dolphin.ibooks.common.database.entity.Admin;
import jp.blue_dolphin.ibooks.common.dto.SearchResult;
import jp.blue_dolphin.ibooks.common.model.AdminModel;
import lombok.AllArgsConstructor;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 管理者リポジトリ
 */
@AllArgsConstructor
@Repository
public class AdminRepositoryImpl implements AdminRepository {
    /** 管理者DAO */
    private AdminDao adminDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AdminModel> selectById(Long adminId) {
        if (Objects.isNull(adminId)) {
            return Optional.empty();
        }
        Admin entity = adminDao.selectById(adminId);
        return Optional.ofNullable(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult<AdminModel> selectAll(String orderBy, SelectOptions options) {
        List<Admin> entities = adminDao.selectAll(orderBy, options);
        long count = options.getCount();
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<AdminModel> models = new ArrayList<>();
        for (Admin entity : entities) {
            models.add(convertModel(entity));
        }
        return new SearchResult<>(models, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AdminModel> selectByLoginId(String loginId) {
        if (Objects.isNull(loginId)) {
            return Optional.empty();
        }
        List<Admin> entities = adminDao.selectByLoginId(loginId);
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(convertModel(entities.getFirst()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteById(Long adminId, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        return adminDao.deleteById(adminId, now, createdId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminModel store(AdminModel model, String createdId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Admin> result;
        if (Objects.isNull(model.getAdminId())) {
            AdminModel setModel =
                    model.toBuilder().createdAt(now).updatedAt(now).loginFailureCount(0)
                            .createdId(createdId).ver(1).build();
            result = adminDao.insert(convertEntity(setModel));
        } else {
            AdminModel setModel = model.toBuilder().updatedAt(now).createdId(createdId).build();
            result = adminDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminModel updateLastLoginDate(AdminModel model) {
        LocalDateTime now = LocalDateTime.now();
        AdminModel setModel =
                model.toBuilder().loginFailureCount(0).lastLoginDate(now).updatedAt(now).build();
        Result<Admin> result = adminDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminModel increaseLoginFailureCount(AdminModel model) {
        LocalDateTime now = LocalDateTime.now();
        AdminModel setModel = model.toBuilder().loginFailureCount(model.getLoginFailureCount() + 1)
                .loginFailureDate(now).updatedAt(now).build();
        Result<Admin> result = adminDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminModel resetLoginFailureCount(AdminModel model) {
        LocalDateTime now = LocalDateTime.now();
        AdminModel setModel = model.toBuilder().loginFailureCount(0).updatedAt(now).build();
        Result<Admin> result = adminDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * 引数の管理者エンティティを管理者モデルに変換する
     *
     * @param entity 管理者エンティティ
     * @return 管理者モデル
     */
    private AdminModel convertModel(Admin entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return AdminModel.builder()
                .adminId(entity.adminId)
                .loginId(entity.loginId)
                .password(entity.password)
                .name(entity.name)
                .privilegeId(entity.privilegeId)
                .accountStatus(entity.accountStatus)
                .lastLoginDate(entity.lastLoginDate)
                .loginFailureCount(entity.loginFailureCount)
                .loginFailureDate(entity.loginFailureDate)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .createdId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 引数の管理者モデルを管理者エンティティに変換する
     *
     * @param model 管理者モデル
     * @return 管理者エンティティ
     */
    private Admin convertEntity(AdminModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        return Admin.builder()
                .adminId(model.getAdminId())
                .loginId(model.getLoginId())
                .password(model.getPassword())
                .name(model.getName())
                .privilegeId(model.getPrivilegeId())
                .accountStatus(model.getAccountStatus())
                .lastLoginDate(model.getLastLoginDate())
                .loginFailureCount(model.getLoginFailureCount())
                .loginFailureDate(model.getLoginFailureDate())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .createdId(model.getCreatedId())
                .ver(model.getVer())
                .build();
    }
}
