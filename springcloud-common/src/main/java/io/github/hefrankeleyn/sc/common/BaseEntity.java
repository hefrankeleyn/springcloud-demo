package io.github.hefrankeleyn.sc.common;

/**
 * @Date 2024/8/25
 * @Author lifei
 */
public abstract class BaseEntity {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
