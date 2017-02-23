package works.rational.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Common {
  @Id
  @Column(length = 30)
  String id;
  @Column(length = 800)
  String description;
  @Column(nullable = false, updatable = false)
  String createdBy;
  @Column(nullable = false)
  String updatedBy;
  @Column(nullable = false, updatable = false)
  Timestamp createdAt;
  @Column(nullable = false)
  Timestamp updatedAt;

  @PrePersist
  public void prePersist() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    String now = DateFormatUtils.format(timestamp, "yyyyMMddHH");
    String random = RandomStringUtils.randomAlphanumeric(6);
    setId(now + random);

    this.setCreatedAt(timestamp);
    this.setUpdatedAt(timestamp);
  }

  @PreUpdate
  public void preUpdate() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    this.setUpdatedAt(timestamp);
  }

  public void setCreatedUpdatedBy(String username) {
    if (this.getCreatedBy() == null) {
      this.setCreatedBy(username);
    }
    this.setUpdatedBy(username);
  }

  public static class Util {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd (E) HH時", Locale.JAPAN);

    public static Timestamp strToTimestamp(String timestamp) {
      if (StringUtils.isBlank(timestamp)) {
        return null;
      }
      LocalDateTime dateTime = LocalDateTime.parse(timestamp, DATE_TIME_FORMATTER);
      return Timestamp.valueOf(dateTime);
    }

    public static String timestampToStr(Timestamp timestamp) {
      Timestamp ts = (timestamp == null) ? new Timestamp(System.currentTimeMillis()) : timestamp;
      return ts.toLocalDateTime().format(DATE_TIME_FORMATTER);
    }
  }

}
