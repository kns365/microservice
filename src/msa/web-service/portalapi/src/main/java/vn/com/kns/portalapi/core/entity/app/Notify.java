package vn.com.kns.portalapi.core.entity.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_app_notify")
@EntityListeners(AuditingEntityListener.class)
public class Notify extends BaseEntity {

     /* Notification
   Table quản lý các loại notify có trong hệ thống như: notify sinh nhật, notify đăng ký,...
   Table quản lý user A sẽ subcrise loại notify nào
   Table quản lý chi tiết từng event noti của mỗi user, đã đọc hay chưa,...
    */

//    @ManyToOne
//    @JoinColumn(name = "notify_template_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private NotifyTemplate notifyTemplate;
//    @Column(name = "notify_template_id")
//    private Long notifyTemplateId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private User user;
//    @Column(name = "user_id")
//    private Long userId;

    private String template;

    private String username;

    private String data;

    private Boolean state;
}
