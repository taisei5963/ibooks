package jp.blue_dolphin.ibooks.common.database.entity;

import jp.blue_dolphin.ibooks.common.constant.SiteType;
import jp.blue_dolphin.ibooks.common.constant.UploadStatus;
import jp.blue_dolphin.ibooks.common.constant.UploadType;
import lombok.Builder;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

import java.time.LocalDateTime;

/**
 * アップロード履歴エンティティ
 */
@Builder(toBuilder = true)
@Entity(immutable = true)
@Table(name = "upload_hist")
public class UploadHist {
    /** アップロード履歴ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_hist_id")
    public final Long uploadHistId;

    /** アップロード種別 */
    @Column(name = "upload_type")
    public final UploadType uploadType;

    /** 処理ステータス */
    @Column(name = "upload_status")
    public final UploadStatus uploadStatus;

    /** ファイルパス */
    @Column(name = "file_path")
    public final String filePath;

    /** ファイルサイズ */
    @Column(name = "file_size")
    public final Integer fileSize;

    /** 対象件数 */
    @Column(name = "row_count")
    public final Integer rowCount;

    /** 取込件数 */
    @Column(name = "import_count")
    public final Integer importCount;

    /** サイト種別 */
    @Column(name = "site_type")
    public final SiteType siteType;

    /** 作成日時 */
    @Column(name = "created_at")
    public final LocalDateTime createdAt;

    /** 更新日時 */
    @Column(name = "updated_at")
    public final LocalDateTime updatedAt;

    /** 削除日時 */
    @Column(name = "deleted_at")
    public final LocalDateTime deletedAt;

    /** 作成者ID */
    @Column(name = "created_id")
    public final String createdId;

    /** バージョン */
    @Version
    @Column(name = "ver")
    public final Integer ver;
}
