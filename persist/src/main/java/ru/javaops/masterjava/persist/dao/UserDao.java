package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class UserDao implements AbstractDao {

    public User insert(User user) {
        if (user.isNew()) {
            int id = insertGeneratedId(user);
            user.setId(id);
        } else {
            insertWithId(user);
        }
        return user;
    }

    public List<User> generateIdsAndBatchInsert(List<User> users, int chunkSize) {
        List<User> notUpdatedUsers = new ArrayList<>();
        DBIProvider.getDBI().useTransaction(((handle, transactionStatus) -> {
            generateAndSetIds(users);
            int[] modifiedRows = batchInsert(users, chunkSize);
            IntStream.range(0, modifiedRows.length)
                    .filter(i -> modifiedRows[i] == 0)
                    .mapToObj(users::get)
                    .forEach(notUpdatedUsers::add);
        }));
        return notUpdatedUsers;
    }

    public void generateAndSetIds(List<User> users) {
        users.forEach(user -> {
            if (user.isNew()) {
                user.setId(generateKey());
            }
        });
    }

    @SqlUpdate("INSERT INTO users (full_name, email, flag) VALUES (:fullName, :email, CAST(:flag AS user_flag)) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean User user);

    @SqlQuery("SELECT nextval('user_seq')")
    abstract int generateKey();

    @SqlUpdate("INSERT INTO users (id, full_name, email, flag) VALUES (:id, :fullName, :email, CAST(:flag AS user_flag)) ")
    abstract void insertWithId(@BindBean User user);

    @SqlBatch("INSERT INTO users (id, full_name, email, flag) VALUES (:id, :fullName, :email, CAST(:flag AS user_flag)) ON CONFLICT DO NOTHING")
    abstract int[] batchInsert(@BindBean List<User> users, @BatchChunkSize int chunkSize);

    @SqlQuery("SELECT * FROM users ORDER BY full_name, email LIMIT :it")
    public abstract List<User> getWithLimit(@Bind int limit);

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE users")
    @Override
    public abstract void clean();

}
