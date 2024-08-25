package io.github.hefrankeleyn.sc.api.model;

import com.google.common.base.MoreObjects;

/**
 * @Date 2024/8/25
 * @Author lifei
 */
public class User {
    private Integer id;
    private Integer age;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(User.class)
                .add("id", id)
                .add("age", age)
                .add("name", name)
                .toString();
    }
}
