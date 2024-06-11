package vn.com.kns.portalapi.core.entity.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.com.kns.portalapi.core.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_ptl_app_notifytemplate")
@EntityListeners(AuditingEntityListener.class)
public class NotifyTemplate extends BaseEntity {
    /* Notification
   Table quản lý các loại notify có trong hệ thống như: notify sinh nhật, notify đăng ký,...
   Table quản lý user A sẽ subcrise loại notify nào
   Table quản lý chi tiết từng event noti của mỗi user, đã đọc hay chưa,...
    */

    @Column(nullable = false, unique = true)
    private String name;
    //User A đã Tạo Đơn hàng 123
    //User B đã Chỉnh sửa Đơn hàng 123

//    @OneToMany(mappedBy = "notifyTemplate", cascade = CascadeType.ALL)
//    private List<NotifySubscribe> notifySubscribes;

}
