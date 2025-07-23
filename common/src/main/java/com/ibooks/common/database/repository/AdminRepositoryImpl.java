package com.ibooks.common.database.repository;

import com.ibooks.common.database.dao.AdminDao;
import com.ibooks.common.database.entity.Admin;
import com.ibooks.common.dto.IdAndCodeAndName;
import com.ibooks.common.dto.SearchResult;
import com.ibooks.common.model.AdminModel;
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
 * 管理者レポジトリ
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
    public SearchResult<AdminModel> selectAll(String orderBy, SelectOptions options) {
        List<Admin> admins = adminDao.selectAll(orderBy, options);
        long count = options.getCount();
        if (Objects.isNull(admins) || admins.isEmpty()) {
            return new SearchResult<>(Collections.emptyList(), count);
        }
        List<AdminModel> models = new ArrayList<>();
        for (Admin admin : admins) {
            models.add(convertModel(admin));
        }
        return new SearchResult<>(models, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AdminModel> selectById(Long adminId) {
        Admin entity = adminDao.selectById(adminId);
        if (Objects.isNull(entity)) {
            return Optional.empty();
        }
        return Optional.of(convertModel(entity));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AdminModel> selectByLoginId(String loginId) {
        List<Admin> admins = adminDao.selectByLoginId(loginId);
        if (Objects.isNull(admins) || admins.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(convertModel(admins.getFirst()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IdAndCodeAndName> selectIdAndCodeAndNames() {
        return adminDao.selectIdAndCodeAndName();
    }

    /**
     * {@inheritDoc}
     */
    public AdminModel store(AdminModel model, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        Result<Admin> result;
        if (Objects.isNull(model.getAdminId())) {
            AdminModel setModel = model.toBuilder().createDate(now).updateDate(now).loginFailureCount(0)
                    .createId(registerId).ver(1).build();
            result = adminDao.insert(convertEntity(setModel));
        } else {
            AdminModel setModel = model.toBuilder().updateDate(now).createId(registerId).build();
            result = adminDao.update(convertEntity(setModel));
        }
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteById(Long adminId, String registerId) {
        LocalDateTime now = LocalDateTime.now();
        return adminDao.deleteById(adminId, now, registerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminModel updateLastLoginDate(AdminModel model) {
        LocalDateTime now = LocalDateTime.now();
        AdminModel setModel = model.toBuilder().loginFailureCount(0).loginFailureDate(now).updateDate(now).build();
        Result<Admin> result = adminDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminModel inCreaseLoginFailureCount(AdminModel model) {
        LocalDateTime now = LocalDateTime.now();
        AdminModel setModel = model.toBuilder().loginFailureCount(model.getLoginFailureCount() + 1)
                .loginFailureDate(now).updateDate(now).build();
        Result<Admin> result = adminDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdminModel resetLoginFailureCount(AdminModel model) {
        LocalDateTime now = LocalDateTime.now();
        AdminModel setModel = model.toBuilder().loginFailureCount(0).updateDate(now).build();
        Result<Admin> result = adminDao.update(convertEntity(setModel));
        return convertModel(result.getEntity());
    }

    /**
     * 管理者エンティティを管理者モデルに変換する
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
                .lastLoginDate(entity.lastLoginDate)
                .accountStatus(entity.accountStatus)
                .loginFailureCount(entity.loginFailureCount)
                .loginFailureDate(entity.loginFailureDate)
                .createDate(entity.createdAt)
                .updateDate(entity.updatedAt)
                .createId(entity.createdId)
                .ver(entity.ver)
                .build();
    }

    /**
     * 管理者モデルを管理者エンティティに変換する
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
                .lastLoginDate(model.getLastLoginDate())
                .accountStatus(model.getAccountStatus())
                .loginFailureCount(model.getLoginFailureCount())
                .loginFailureDate(model.getLoginFailureDate())
                .createdAt(model.getCreateDate())
                .updatedAt(model.getUpdateDate())
                .createdId(model.getCreateId())
                .ver(model.getVer())
                .build();
    }
}
