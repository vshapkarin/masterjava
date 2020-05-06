package ru.javaops.masterjava.persist.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public abstract class ProjectDao implements AbstractDao {

    @SqlUpdate("TRUNCATE project_groups, project_participants")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO project_groups (group_name, project) VALUES (:groupName, CAST(:project AS project_name))")
    public abstract void insertProject(@Bind("groupName") String groupName, @Bind("project") String project);

    @SqlUpdate("INSERT INTO project_participants (user_id, project_group_id) " +
            "VALUES (:userId, (SELECT id FROM project_groups WHERE group_name = :groupName))")
    public abstract void insertParticipant(@Bind("userId") Integer userId, @Bind("groupName") String groupName);

    @SqlQuery("SELECT COUNT(*) FROM project_groups")
    public abstract int getGroupsNumber();

    @SqlQuery("SELECT COUNT(*) FROM project_participants")
    public abstract int getParticipantsNumber();

}
