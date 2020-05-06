package ru.javaops.masterjava.persist.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public abstract class CityDao implements AbstractDao {

    @SqlUpdate("TRUNCATE cities CASCADE")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO cities (id_name, city_name) VALUES (:cityIdName, :cityName) ON CONFLICT DO NOTHING")
    public abstract void insert(@Bind("cityIdName") String cityIdName, @Bind("cityName") String cityName);

    @SqlBatch("INSERT INTO cities (id_name, city_name) VALUES (:cityIdName, :cityName) ON CONFLICT DO NOTHING")
    public abstract void insertBatch(@Bind("cityIdName") List<String> cityIdNames, @Bind("cityName") List<String> cityNames);

}
