package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity {

    @Column("full_name")
    private @NonNull String fullName;

    private @NonNull String email;

    private @NonNull UserFlag flag;

    @Column("city_id_name")
    private @NonNull String cityIdName;

    public User(Integer id, String fullName, String email, UserFlag flag, String cityIdName) {
        this(fullName, email, flag, cityIdName);
        this.id=id;
    }
}