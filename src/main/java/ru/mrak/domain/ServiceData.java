package ru.mrak.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "service_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "key_data")
    private String keyData;

    @Column(name = "value")
    private String value;

    public ServiceData() {
    }

    public ServiceData(String keyData, String value) {
        this.keyData = keyData;
        this.value = value;
    }

    public String getKeyData() {
        return keyData;
    }

    public void setKeyData(String keyData) {
        this.keyData = keyData;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceData that = (ServiceData) o;
        return keyData.equals(that.keyData);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceData{" +
            "keyData='" + keyData + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
